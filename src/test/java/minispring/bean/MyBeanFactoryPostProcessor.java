package minispring.bean;

import minispring.beans.BeanException;
import minispring.beans.PropertyValue;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanFactoryPostProcessor;
import minispring.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author lihua
 * @since 2021/9/1
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        BeanDefinition beanDefinition = defaultListableBeanFactory.getBeanDefinition("personService");
        PropertyValue gender = new PropertyValue("gender", 1);
        beanDefinition.getPropertyValues().addPropertyValue(gender);
    }
}
