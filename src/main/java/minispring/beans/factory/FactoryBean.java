package minispring.beans.factory;

/**
 * @author lihua
 * @since 2021/9/6
 */
public interface FactoryBean<T> {

	/**
	 * 本实例是否为单例
	 *
	 * @return 作用域是否为singleton
	 */
	boolean isSingleton();

	/**
	 * 获取bean实例
	 * 代替xml中繁琐的配置，可以在代码中装填对象，赋予额外的逻辑
	 *
	 * @return bean实例
	 */
	T getObject();
}
