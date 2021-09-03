package minispring.beans.factory.config;

import lombok.Data;
import minispring.beans.PropertyValues;

/**
 * 从xml配置文件中解析而来
 *
 * @author lihua
 * @since 2021/8/23
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Data
public class BeanDefinition {

    private Class beanClass;

    private PropertyValues propertyValues;

    /**
     * 必须是无参的方法
     */
    private String initMethodName;

    private String destroyMethodName;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public boolean isAssignableFrom(Class receivedClass) {
        return receivedClass.isAssignableFrom(beanClass);
    }
}
