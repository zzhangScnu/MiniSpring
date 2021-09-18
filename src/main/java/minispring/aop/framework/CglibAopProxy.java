package minispring.aop.framework;

import lombok.RequiredArgsConstructor;
import minispring.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lihua
 * @since 2021/9/18
 */
@RequiredArgsConstructor
public class CglibAopProxy implements AopProxy {

	private final AdvisedSupport advisedSupport;

	@Override
	public Object getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(advisedSupport.getTargetSource().getTarget().getClass());
		enhancer.setInterfaces(advisedSupport.getTargetClass());
		enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
		return enhancer.create();
	}

	@RequiredArgsConstructor
	private static class DynamicAdvisedInterceptor implements MethodInterceptor {

		private final AdvisedSupport advisedSupport;

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			Object target = advisedSupport.getTargetSource().getTarget();
			CglibMethodInvocation methodInvocation = new CglibMethodInvocation(target, method, args, proxy);
			if (advisedSupport.getMethodMatcher().matches(method, target.getClass())) {
				return advisedSupport.getMethodInterceptor().invoke(methodInvocation);
			}
			return methodInvocation.proceed();
		}
	}

	private static class CglibMethodInvocation extends ReflectionMethodInvocation {

		private final MethodProxy methodProxy;

		public CglibMethodInvocation(Object target, Method method, Object[] args, MethodProxy methodProxy) {
			super(target, method, args);
			this.methodProxy = methodProxy;
		}

		@Override
		public Object proceed() throws Throwable {
			return methodProxy.invoke(getTarget(), getArgs());
		}
	}
}
