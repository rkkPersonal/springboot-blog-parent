package org.xr.happy.common.annotation;

import java.lang.annotation.*;

/**
 * @author Steven
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisCache {

    String key() default "";

    String value() default "";
}
