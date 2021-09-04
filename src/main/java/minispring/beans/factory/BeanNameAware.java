package minispring.beans.factory;

import minispring.beans.BeanException;

/**
 * @author lihua
 * @since 2021/9/4
 */
public interface BeanNameAware extends Aware {

    /**
     * 为实现此接口的类注入bean名字
     *
     * @param beanName bean的名字
     * @throws BeanException 异常
     */
    void setBeanName(String beanName) throws BeanException;
}
