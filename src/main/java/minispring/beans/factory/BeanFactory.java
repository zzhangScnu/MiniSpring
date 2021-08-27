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
}
