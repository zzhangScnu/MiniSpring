package minispring.beans.factory;

/**
 * @author lihua
 * @since 2021/9/2
 */
public interface InitializingBean {

    /**
     * 在属性设置完成后执行的方法
     *
     * @throws Exception 异常
     */
    void afterPropertiesSet() throws Exception;
}
