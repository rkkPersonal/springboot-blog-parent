package org.xr.happy.aspect;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
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
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xr.happy.common.annotation.NotEmpty;
import org.xr.happy.common.annotation.Valid;
import org.xr.happy.common.annotation.Validator;
import org.xr.happy.common.dto.Result;
import org.xr.happy.common.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven
 */
@Configuration
@Aspect
@Order(0)
public class ParameterAspect {

    private static final Logger logger = LoggerFactory.getLogger(ParameterAspect.class);

    @Pointcut("execution(* org.xr.happy.controller.login.*.*(..))")
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
        DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], args[i]);
            System.out.println("parameterNams:" + parameterNames[i] + ",args:" + args[i]);
        }
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isAnnotationPresent(NotEmpty.class)) {
                String name = parameter.getName();
                if (map.containsKey(name)){
                    Object o = map.get(name);
                    if (ObjectUtils.isEmpty(o)){
                        NotEmpty annotation = parameter.getAnnotation(NotEmpty.class);
                        return Result.error(annotation.msg());
                    }
                }
            }else if (parameter.isAnnotationPresent(Validator.class)){
                String name = parameter.getName();
                if (map.containsKey(name)){
                    Object o = map.get(name);
                    Result validator = validator(o);
                    if (validator.getCode().intValue() == 0) {
                        continue;
                    }
                    return validator;
                }
            }
        }


        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            Object value = stringObjectEntry.getValue();
            Result validator = validator(value);
            if (validator.getCode().intValue() == 0) {
                continue;
            }
            return validator;
        }

        Object proceed = joinPoint.proceed();

        return proceed;
    }

    @SneakyThrows
    private Result validator(Object obj) {
        String typeName = obj.getClass().getTypeName();
        System.out.println(typeName);
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(Valid.class)) {
                Object o = declaredField.get(obj);
                Result validator = validator(o);
                if (validator.getCode().intValue()!=0){
                    return validator;
                }
            } else if (declaredField.isAnnotationPresent(NotEmpty.class)) {
                NotEmpty notEmpty = declaredField.getAnnotation(NotEmpty.class);
                boolean required = notEmpty.required();
                String msg = notEmpty.msg();
                Object o = declaredField.get(obj);
                if (required) {
                    if (null == o) {
                        return Result.error(msg);
                    }
                }
            }
        }
        return Result.ok();
    }



    public static void main(String[] args) throws IllegalAccessException {

        UserVo userVo = new UserVo();
        userVo.setUserId(111L);

        Field[] declaredFields = userVo.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object o = declaredField.get(userVo);
            System.out.println(o);
        }
    }
}
