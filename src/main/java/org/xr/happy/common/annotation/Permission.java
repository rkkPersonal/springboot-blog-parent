package org.xr.happy.common.annotation;


import java.lang.annotation.*;

/**
 * @author Steven
 */
@Target(value = {ElementType.METHOD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

    String role() default "";

}
