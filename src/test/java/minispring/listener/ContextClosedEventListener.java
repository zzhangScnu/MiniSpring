package minispring.listener;

import minispring.context.ApplicationListener;
import minispring.context.event.ContextClosedEvent;

/**
 * @author lihua
 * @since 2021/9/9
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		System.out.println("监听到容器关闭的消息！");
	}
}
