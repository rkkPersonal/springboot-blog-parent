package org.xr.happy.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xr.happy.common.annotation.NotEmpty;
import org.xr.happy.common.annotation.Validator;
import org.xr.happy.common.dto.Result;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @author Steven
 */
@Configuration
@Aspect
@Order(0)
public class ValidatorAspect {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorAspect.class);

    @Pointcut("execution(* org.xr.happy.controller.*.*(..))")
    public void doValidator() {

    }


    @Around("doValidator()")
    public Object doProcessor(ProceedingJoinPoint joinPoint) throws Throwable {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Signature signature = joinPoint.getSignature();
        logger.info("signature :{},2:{},3:{}", signature, signature.getName(), signature.getDeclaringTypeName(), signature.getModifiers());
        Object[] args = joinPoint.getArgs();
        logger.info("arges:{}", Arrays.asList(args));

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();

        if (method != null) {

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();

            for (Annotation[] parameterAnnotation : parameterAnnotations) {

                for (Annotation annotation : parameterAnnotation) {
                    if (annotation.annotationType().equals(Validator.class)) {

                        Parameter[] parameters = method.getParameters();
                        for (Parameter parameter : parameters) {
                            // 参数名称 例如 (UserVo user) name 就是user
                            String name = parameter.getName();

                            // type 这个是获取 UserVo原对象的
                            Class<?> type = parameter.getType();

                            // 通过UserVo原对象获取它所有的属性，然后再获取所有的防范中 加了NotEmpty注解的参数
                            Field[] declaredFields = type.getDeclaredFields();

                            for (Field field : declaredFields) {

                                field.setAccessible(true);

                                if (field.isAnnotationPresent(NotEmpty.class)) {

                                    String fileName = field.getName();

                                    String requestParameter = request.getParameter(fileName);
                                    NotEmpty notEmpty = field.getDeclaredAnnotation(NotEmpty.class);

                                    if (notEmpty.required()) {

                                        if (StringUtils.isBlank(requestParameter)) {
                                            String msg = notEmpty.msg();
                                            logger.info(" error msg  is :{}", msg);
                                            return Result.error(msg);

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        logger.info("Url : " + request.getRequestURL().toString());
        logger.info("HttpMethod : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("ClassMethod : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());


        Object proceed = joinPoint.proceed();

        return proceed;
    }


    /**
     * @param result
     */
    @AfterReturning(
            pointcut = "org.xr.happy.aspect.ValidatorAspect.doValidator()",
            returning = "result")
    public void doAccessCheck(Object result) {
        // ...

        logger.info("afterReturning advice:{}", result);
    }


}
