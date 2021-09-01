package minispring.context.support;

import minispring.beans.BeanException;

/**
 * @author lihua
 * @since 2021/8/31
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) throws BeanException {
        this(new String[]{configLocation});
    }

    /**
     * 从xml配置文件中加载beanDefinition，并刷新上下文
     *
     * @param configLocations xml路径
     * @throws BeanException 异常
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeanException {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
