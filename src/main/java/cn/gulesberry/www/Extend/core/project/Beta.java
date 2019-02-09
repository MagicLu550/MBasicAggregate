package cn.gulesberry.www.Extend.core.project;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Beta {
    String where() default "";
    Progress progress() default Progress.FINISH;
}
