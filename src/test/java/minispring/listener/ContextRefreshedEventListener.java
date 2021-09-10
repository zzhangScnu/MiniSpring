package minispring.listener;

import minispring.context.ApplicationListener;
import minispring.context.event.ContextRefreshedEvent;

/**
 * @author lihua
 * @since 2021/9/9
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("监听到容器刷新的消息！");
	}
}
