package minispring.beans.factory.config;

/**
 * @author lihua
 * @since 2021/8/25
 */
public interface SingletonBeanRegistry {

	/**
	 * 获取单例对象
	 *
	 * @param name bean的名字
	 * @return 单例的bean
	 */
	Object getSingleton(String name);
}
