package minispring.context;

import java.util.EventListener;

/**
 * 关注某一类事件的监听器
 *
 * @author lihua
 * @since 2021/9/8
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

	/**
	 * 当接收到消息事件时，要执行的操作
	 *
	 * @param event 消息事件
	 */
	void onApplicationEvent(E event);
}
