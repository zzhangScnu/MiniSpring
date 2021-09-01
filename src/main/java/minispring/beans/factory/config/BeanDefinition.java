package minispring.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import minispring.beans.PropertyValues;

/**
 * @author lihua
 * @since 2021/8/23
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Data
@AllArgsConstructor
public class BeanDefinition {

    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }

    public boolean isAssignableFrom(Class receivedClass) {
        return receivedClass.isAssignableFrom(beanClass);
    }
}
