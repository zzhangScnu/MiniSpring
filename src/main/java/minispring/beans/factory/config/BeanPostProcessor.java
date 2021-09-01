package minispring.beans.factory.config;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface BeanPostProcessor {

    /**
     * bean初始化前调用
     *
     * @param bean     bean实例
     * @param beanName bean名字
     * @return 执行结果
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * bean初始化后调用
     *
     * @param bean     bean实例
     * @param beanName bean名字
     * @return 执行结果
     */
    Object postProcessAfterInitialization(Object bean, String beanName);
}
