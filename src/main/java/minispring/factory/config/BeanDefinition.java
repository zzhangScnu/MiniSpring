package minispring.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lihua
 * @since 2021/8/23
 */
@SuppressWarnings("rawtypes")
@Data
@AllArgsConstructor
public class BeanDefinition {

    private Class beanClass;
}
