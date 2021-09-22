package minispring.aop;

import lombok.RequiredArgsConstructor;
import minispring.aop.framework.AopProxy;
import minispring.aop.framework.CglibAopProxy;
import minispring.aop.framework.JdkDynamicAopProxy;

/**
 * @author lihua
 * @since 2021/9/22
 */
@RequiredArgsConstructor
public class AopFactory {

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
