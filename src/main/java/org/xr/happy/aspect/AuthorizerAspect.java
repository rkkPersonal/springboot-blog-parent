package org.xr.happy.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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
import org.xr.happy.common.annotation.Permission;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Steven
 * <annotation>@Order用来做切面排序的，数值小越先执行 </annotation>
 */
@Configuration
@Aspect
@Order(1)
public class AuthorizerAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizerAspect.class);

    @Pointcut("execution(* org.xr.happy.controller.*.*(..))")
    public void doFilterPermission() {

    }


    @Around("doFilterPermission()")
    public Object doProcessor(ProceedingJoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Signature signature = joinPoint.getSignature();
        logger.info("signature :{},2:{},3:{}", signature, signature.getName(), signature.getDeclaringTypeName(), signature.getModifiers());
        Object[] args = joinPoint.getArgs();
        logger.info("arges:{}", Arrays.asList(args));
        Object target = joinPoint.getTarget();
        logger.info("targer:{}", target);
        Object aThis = joinPoint.getThis();
        logger.info("aThis :{}", aThis);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();

        if (method != null) {

            boolean annotationPresent = method.isAnnotationPresent(Permission.class);

            if (annotationPresent == true) {

                Permission annotation = method.getAnnotation(Permission.class);

                String role = annotation.role();

                logger.info("role:{}", role);
            }

        }


        Object proceed = joinPoint.proceed();

        return proceed;
    }
}
