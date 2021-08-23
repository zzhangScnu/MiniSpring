package minispring.component;

import lombok.SneakyThrows;
import minispring.pojo.BeanDefinition;

import java.util.Objects;

/**
 * @author lihua
 * @since 2021/8/23
 */
public class BeanRegistry extends BeanFactory {

    @SuppressWarnings("unchecked")
    @SneakyThrows
    @Override
    public <T> T getBean(Class<T> tClass) {
        String name = tClass.getSimpleName();
        BeanDefinition beanDefinition = CACHED_MAP.get(name);
        if (Objects.nonNull(beanDefinition)) {
            return (T) beanDefinition.getBean();
        }
        T bean = tClass.newInstance();
        putBean(bean);
        return bean;
    }

    public <T> void putBean(T bean) {
        String name = bean.getClass().getSimpleName();
        BeanDefinition beanDefinition = new BeanDefinition(bean);
        CACHED_MAP.put(name, beanDefinition);
    }
}
