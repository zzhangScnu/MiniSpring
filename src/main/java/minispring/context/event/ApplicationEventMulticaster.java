package minispring.context.event;

import minispring.context.ApplicationEvent;
import minispring.context.ApplicationListener;

/**
 * @author lihua
 * @since 2021/9/8
 */
public interface ApplicationEventMulticaster {

	/**
	 * 广播消息事件，给感兴趣的监听器
	 *
	 * @param applicationEvent 消息事件
	 */
	void multicasterEvent(ApplicationEvent applicationEvent);

	/**
	 * 注册监听器
	 *
	 * @param applicationListener 监听器
	 */
	void addApplicationListener(ApplicationListener<ApplicationEvent> applicationListener);

	/**
	 * 移除监听器
	 *
	 * @param applicationListener 监听器
	 */
	void removeApplicationListener(ApplicationListener<ApplicationEvent> applicationListener);
}
