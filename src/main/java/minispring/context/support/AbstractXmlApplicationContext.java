package minispring.context.support;

import minispring.beans.factory.support.DefaultListableBeanFactory;
import minispring.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Objects;

/**
 * @author lihua
 * @since 2021/8/31
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (Objects.isNull(configLocations)) {
            return;
        }
        beanDefinitionReader.loadBeanDefinitions(configLocations);
    }

    /**
     * 获取bean定义的xml的位置
     *
     * @return xml路径
     */
    protected abstract String[] getConfigLocations();
}
