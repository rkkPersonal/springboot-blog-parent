package org.xr.happy.common.annotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @author Steven
 */
@Target(value = ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {

}
