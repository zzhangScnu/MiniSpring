package minispring.aop;

import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author lihua
 * @since 2021/9/22
 */
@RequiredArgsConstructor
public class MethodBeforeInterceptor implements MethodInterceptor {

	private final MethodBeforeAdvice methodBeforeAdvice;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		methodBeforeAdvice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
		return invocation.proceed();
	}
}
