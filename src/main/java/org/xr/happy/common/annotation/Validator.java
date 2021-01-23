package org.xr.happy.common.annotation;

import java.lang.annotation.*;

/**
 * @author Steven
 */
@Target(value = ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

}
