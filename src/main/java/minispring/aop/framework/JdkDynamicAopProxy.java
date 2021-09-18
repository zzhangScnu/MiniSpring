package minispring.aop.framework;

import lombok.RequiredArgsConstructor;
import minispring.aop.AdvisedSupport;
import minispring.util.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lihua
 * @since 2021/9/18
 */
@RequiredArgsConstructor
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

	private final AdvisedSupport advisedSupport;

	/**
	 * 生成代理对象
	 */
	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(
				ClassUtils.getDefaultClassLoader(), advisedSupport.getTargetClass(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object target = advisedSupport.getTargetSource().getTarget();
		if (advisedSupport.getMethodMatcher().matches(method, target.getClass())) {
			ReflectionMethodInvocation methodInvocation = new ReflectionMethodInvocation(target, method, args);
			MethodInterceptor methodInterceptor = advisedSupport.getMethodInterceptor();
			return methodInterceptor.invoke(methodInvocation);
		}
		return method.invoke(target, args);
	}
}
