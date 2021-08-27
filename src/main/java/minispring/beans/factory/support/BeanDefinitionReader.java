package minispring.beans.factory.support;

/**
 * @author lihua
 * @since 2021/8/27
 */
public interface BeanDefinitionReader {

    /**
     * 加载bean定义
     *
     * @param location 位置信息
     */
    void loadBeanDefinitions(String location);
}
