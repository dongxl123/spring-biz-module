package com.winbaoxian.module.security.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SearchParam {
    COMPARE compare() default COMPARE.eq;

    String name() default "";

    boolean ignore() default false;

    enum COMPARE {
        lt,
        gt,
        le,
        ge,
        ne,
        in,
        eq,
        like
    }
}
