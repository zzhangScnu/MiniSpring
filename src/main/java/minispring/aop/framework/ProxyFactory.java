package minispring.aop.framework;

import lombok.RequiredArgsConstructor;
import minispring.aop.AdvisedSupport;

/**
 * @author lihua
 * @since 2021/9/22
 */
@RequiredArgsConstructor
public class ProxyFactory {

	private final AdvisedSupport advisedSupport;

	public Object getProxy() {
		return createProxy().getProxy();
	}

	public AopProxy createProxy() {
		boolean proxyTargetClass = advisedSupport.isProxyTargetClass();
		return proxyTargetClass ?
				new CglibAopProxy(advisedSupport) : new JdkDynamicAopProxy(advisedSupport);
	}
}
