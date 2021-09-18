package minispring.aop.framework;

/**
 * @author lihua
 * @since 2021/9/18
 */
public interface AopProxy {

	/**
	 * 获取代理对象
	 *
	 * @return 代理对象
	 */
	Object getProxy();
}
