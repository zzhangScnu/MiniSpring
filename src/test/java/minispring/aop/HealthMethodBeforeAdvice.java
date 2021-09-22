package minispring.aop;

import java.lang.reflect.Method;

/**
 * @author lihua
 * @since 2021/9/22
 */
public class HealthMethodBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) {
		System.out.println("目标方法执行前切一刀，执行前置方法~");
	}
}
