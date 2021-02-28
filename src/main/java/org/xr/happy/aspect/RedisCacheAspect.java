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
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 该注解添加了限流和防止 缓存失效雪崩
 *
 * @author Steven
 */
@Component
@Aspect
public class RedisCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAspect.class);

    private static final Semaphore semaphore = new Semaphore(100);

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

            if (userInfo != null) {
                logger.info("Get User Info from redis cache..........");
                return userInfo;
            }

            //semaphore.acquire(); 会阻塞一直等待，tryAcquire()则可以设置超时时间，如果超时，返回false则进行默认返回降级处理
            //防止缓存同时失效，启用限流，并且在重试获取三秒之后没有拿到请求则默认返回 网络不稳定等消息
            // Todo 根据具体业务去写
            boolean hitStatus = semaphore.tryAcquire(1000, TimeUnit.SECONDS);
            if (!hitStatus) {
                return Result.error("网络错误，请刷新页面重试！");
            }


            // 2、不存在则执行方法
            object = joinPoint.proceed();
            redisTemplate.opsForValue().set(redisKey, object);

        } catch (Throwable throwable) {
            logger.error("redisCache error :", throwable.getMessage());
        } finally {
            // 业务处理完后释放资源
            semaphore.release();
        }

        return object;
    }

}
