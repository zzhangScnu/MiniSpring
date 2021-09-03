package minispring.beans.factory.support;

import lombok.Getter;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanPostProcessor;
import minispring.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 本类实现了ConfigurableBeanFactory接口，并继承了DefaultSingletonBeanRegistry类
 * 其中，destroySingletons由接口定义，由父类实现
 * ConfigurableBeanFactory和DefaultSingletonBeanRegistry并没有直接的实现关系，但是通过本类，达到了实现的效果，是一种不错的分层和隔离
 *
 * @author lihua
 * @since 2021/8/25
 */
@Getter
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name, Object... args) {
        Object singletonBean = getSingleton(name);
        if (Objects.nonNull(singletonBean)) {
            return singletonBean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);
    }

    /**
     * 获取bean定义
     *
     * @param name bean的名字
     * @return beanDefinition
     */
    public abstract BeanDefinition getBeanDefinition(String name);

    /**
     * 创建一个新的bean
     *
     * @param name           bean的名字
     * @param beanDefinition bean定义
     * @param args           构造方法的参数
     * @return bean
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }
}
