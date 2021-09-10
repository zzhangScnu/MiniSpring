package minispring.context.event;

import minispring.context.ApplicationEvent;
import minispring.context.ApplicationListener;
import minispring.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lihua
 * @since 2021/9/8
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster {

	private final Set<ApplicationListener<ApplicationEvent>> listeners = new LinkedHashSet<>();

	@Override
	public void addApplicationListener(ApplicationListener<ApplicationEvent> applicationListener) {
		listeners.add(applicationListener);
	}

	@Override
	public void removeApplicationListener(ApplicationListener<ApplicationEvent> applicationListener) {
		listeners.remove(applicationListener);
	}

	protected Collection<ApplicationListener<ApplicationEvent>> getApplicationListeners(ApplicationEvent event) {
		return listeners.stream()
				.filter(listener -> supportsEvent(listener, event))
				.collect(Collectors.toList());
	}

	protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
		Class<?> listenerClass = applicationListener.getClass();
		Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
		// 获取接口上的泛型参数
		ParameterizedType genericInterface = (ParameterizedType) targetClass.getGenericInterfaces()[0];
		Type actualTypeArgument = genericInterface.getActualTypeArguments()[0];
		Class<?> actualClass = (Class<?>) actualTypeArgument;
		// 是否监听器感兴趣的事件，是否是入参事件的类型或父类
		return actualClass.isAssignableFrom(event.getClass());
	}
}
