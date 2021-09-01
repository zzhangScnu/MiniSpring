package minispring.context;

import minispring.beans.BeanException;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     *
     * @throws BeanException 异常
     */
    void refresh() throws BeanException;
}
