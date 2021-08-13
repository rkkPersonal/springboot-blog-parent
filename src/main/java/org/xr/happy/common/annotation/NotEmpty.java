package org.xr.happy.common.annotation;


import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * @author Steven
 */
@Target(value = {METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {

    String msg() default "";

    boolean required() default false;

    Class<?>[] groups() default {};
}
