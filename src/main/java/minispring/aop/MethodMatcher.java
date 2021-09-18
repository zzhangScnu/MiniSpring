package minispring.aop;

import java.lang.reflect.Method;

/**
 * @author lihua
 * @since 2021/9/18
 */
public interface MethodMatcher {

	/**
	 * 是否拦截方法
	 *
	 * @param method 方法对象
	 * @param clazz  类对象
	 * @return 是否拦截
	 */
	boolean matches(Method method, Class<?> clazz);
}
