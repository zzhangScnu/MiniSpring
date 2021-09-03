package minispring.beans.factory;

/**
 * @author lihua
 * @since 2021/9/2
 */
public interface DisposableBean {

    /**
     * 销毁bean对象
     *
     * @throws Exception 异常
     */
    void destroy() throws Exception;
}
