package minispring.context;

import minispring.beans.BeanException;
import minispring.beans.factory.Aware;

/**
 * @author lihua
 * @since 2021/9/4
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 为实现此接口的类注入上下文
     *
     * @param applicationContext 上下文
     * @throws BeanException 异常
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
