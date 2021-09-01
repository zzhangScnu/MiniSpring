package minispring.beans.factory;

import minispring.beans.BeanException;

import java.util.Map;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 根据类型获取名字-bean实例的映射
     *
     * @param type 类型
     * @param <T>  类类型
     * @return 映射关系
     * @throws BeanException 异常
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

    /**
     * 获取注册表中所有bean的名字
     *
     * @return bean名字的集合
     */
    String[] getBeanDefinitionNames();
}
