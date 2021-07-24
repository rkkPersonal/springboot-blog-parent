package org.xr.happy.common.annotation;


import java.lang.annotation.*;

/**
 * @author Steven
 */
@Target(value = {ElementType.FIELD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {

    String msg() default "";

    boolean required() default false;
}
