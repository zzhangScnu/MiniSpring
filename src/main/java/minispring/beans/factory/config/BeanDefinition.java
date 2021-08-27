package minispring.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import minispring.beans.PropertyValues;

/**
 * @author lihua
 * @since 2021/8/23
 */
@SuppressWarnings("rawtypes")
@Data
@AllArgsConstructor
public class BeanDefinition {

    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }
}
