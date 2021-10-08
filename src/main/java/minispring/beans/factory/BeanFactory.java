package minispring.beans.factory;

/**
 * @author lihua
 * @since 2021/8/23
 */
public interface BeanFactory {

    /**
     * 从容器中获取bean
     *
     * @param name bean的名字
     * @param args 构造方法参数
     * @return bean
     */
    Object getBean(String name, Object... args);

    /**
     * 根据bean的类型，从容器中获取bean
     *
     * @param type bean的类型
     * @return bean
     */
    Object getBean(Class<?> type);

    /**
     * 从容器中获取不被代理的bean，每次获取到的都是新的实例
     *
     * @param name bean的名字
     * @param args 构造方法参数
     * @return bean
     */
    Object getBeanPlainly(String name, Object... args);
}
