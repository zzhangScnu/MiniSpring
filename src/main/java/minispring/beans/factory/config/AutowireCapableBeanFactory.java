package minispring.beans.factory.config;

import minispring.beans.BeanException;
import minispring.beans.factory.BeanFactory;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 获取已注册的beanPostProcessor，执行其中的postProcessBeforeInitialization()方法
     *
     * @param existingBean 已实例化的bean
     * @param beanName     bean名字
     * @return 执行后的bean
     * @throws BeanException 异常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException;

    /**
     * 获取已注册的beanPostProcessor，执行其中的postProcessAfterInitialization()方法
     *
     * @param existingBean 已实例化的bean
     * @param beanName     bean名字
     * @return 执行后的bean
     * @throws BeanException 异常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeanException;
}
