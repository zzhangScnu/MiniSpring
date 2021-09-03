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

    /**
     * 注册钩子，在容器销毁时执行方法
     */
    void registerShutdownHook();

    /**
     * 在容器销毁时将要执行的方法
     */
    void close();
}
