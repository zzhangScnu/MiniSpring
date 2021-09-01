package minispring.bean;

import minispring.beans.factory.config.BeanPostProcessor;

/**
 * @author lihua
 * @since 2021/9/1
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (!(bean instanceof PersonService)) {
            return bean;
        }
        ((PersonService) bean).setName("张喵喵");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
