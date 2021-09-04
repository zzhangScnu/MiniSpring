package minispring.context.support;

import lombok.RequiredArgsConstructor;
import minispring.beans.factory.config.BeanPostProcessor;
import minispring.context.ApplicationContext;
import minispring.context.ApplicationContextAware;

/**
 * @author lihua
 * @since 2021/9/4
 */
@RequiredArgsConstructor
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
