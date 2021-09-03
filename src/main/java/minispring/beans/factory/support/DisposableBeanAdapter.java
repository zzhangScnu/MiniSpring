package minispring.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import minispring.beans.BeanException;
import minispring.beans.factory.DisposableBean;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author lihua
 * @since 2021/9/3
 */
@RequiredArgsConstructor
public class DisposableBeanAdapter {

    private static final String DISPOSABLE_BEAN_DESTORY_METHOD_NAME = "destroy";

    private final String name;

    private final Object bean;

    private final String destroyMethodName;

    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        if (isXmlConfigDestroy()) {
            Method method = bean.getClass().getMethod(destroyMethodName);
            if (Objects.isNull(method)) {
                throw new BeanException(String.format("Couldn't find a destroy method named '%s' on bean with name '%s'",
                        destroyMethodName, name));
            }
            method.invoke(bean);
        }
    }

    private boolean isXmlConfigDestroy() {
        return StrUtil.isNotBlank(destroyMethodName)
                // 避免xml和接口方式都配置了同一个销毁方法，导致重复执行
                && !(bean instanceof DisposableBean && DISPOSABLE_BEAN_DESTORY_METHOD_NAME.equals(destroyMethodName));
    }
}
