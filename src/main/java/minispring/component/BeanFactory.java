package minispring.component;

import minispring.pojo.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lihua
 * @since 2021/8/23
 */
public abstract class BeanFactory {

    protected static final Map<String, BeanDefinition> CACHED_MAP = new ConcurrentHashMap<>();

    /**
     * 从容器中获取bean
     *
     * @param tClass bean的类对象
     * @return bean
     */
    abstract <T> T getBean(Class<T> tClass);
}
