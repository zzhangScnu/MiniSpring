package minispring.beans.factory.config;

import cn.hutool.core.util.StrUtil;
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

    public static final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    public static final String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    private Class beanClass;

    private PropertyValues propertyValues;

    /**
     * 必须是无参的方法
     */
    private String initMethodName;

    private String destroyMethodName;

    private String scope;

    private boolean singleton = true;

    private boolean prototype = false;

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

    public void setScope(String scope) {
        // 如果不设置，就保持默认值-singleton
        if (StrUtil.isBlank(scope)) {
            return;
        }
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }
}
