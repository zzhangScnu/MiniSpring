package minispring.aop.framework.adapter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import minispring.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author lihua
 * @since 2021/9/22
 */
@NoArgsConstructor
@AllArgsConstructor
public class MethodBeforeInterceptor implements MethodInterceptor {

	private MethodBeforeAdvice methodBeforeAdvice;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		methodBeforeAdvice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
		return invocation.proceed();
	}
}
