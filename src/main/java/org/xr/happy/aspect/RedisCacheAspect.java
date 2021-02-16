package org.xr.happy.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.xr.happy.common.annotation.RedisCache;
import org.xr.happy.common.dto.Result;

import java.lang.reflect.Method;

/**
 * @author Steven
 */
@Component
@Aspect
public class RedisCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(org.xr.happy.common.annotation.RedisCache)")
    public void cachePointCut() {
    }

    @Around("cachePointCut()")
    public Object doCache(ProceedingJoinPoint joinPoint) {

        Object object = null;
        try {

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());

            RedisCache redisCache = method.getAnnotation(RedisCache.class);
            String key = redisCache.key();
            String value = redisCache.value();

            // 0-2、 前提条件：拿到作为key的依据  - 解析springEL表达式
            // 创建解析器
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(key);
            EvaluationContext context = new StandardEvaluationContext();
            // 添加参数
            Object[] args = joinPoint.getArgs();
            DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
            String[] parameterNames = discover.getParameterNames(method);
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i].toString());
            }
            // 解析
            String redisKey = value + "::" + expression.getValue(context);

            logger.info("redis Key is :{}", redisKey);
            // todo 自定义组件，限流，降级...

            Object userInfo = redisTemplate.opsForValue().get(redisKey);

            if (userInfo!=null){
                logger.info("Get User Info from redis cache..........");
                return Result.success(userInfo);
            }

            // 2、不存在则执行方法
            object = joinPoint.proceed();
            redisTemplate.opsForValue().set(redisKey, object);

        } catch (Throwable throwable) {
            logger.error("redisCache error :", throwable.getMessage());
        }

        return object;
    }

}
