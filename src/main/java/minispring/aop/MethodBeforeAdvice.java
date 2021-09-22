package minispring.aop;

import java.lang.reflect.Method;

/**
 * @author lihua
 * @since 2021/9/22
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

	/**
	 * 前置操作
	 *
	 * @param method 要执行的方法对象
	 * @param args   参数
	 * @param target 目标对象
	 */
	void before(Method method, Object[] args, Object target);
}
