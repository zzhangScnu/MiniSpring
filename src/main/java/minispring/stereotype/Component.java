package minispring.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lihua
 * @since 2021/9/28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

	/**
	 * getValue()的写法是错误的
	 * 注解是一种特殊的接口
	 */
	String value() default "";
}
