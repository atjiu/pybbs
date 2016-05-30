package cn.tomoya.utils.ext.route;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ControllerBind {
    String controllerKey();

    String viewPath() default "";
}
