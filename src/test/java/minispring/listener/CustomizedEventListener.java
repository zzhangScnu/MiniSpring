package minispring.listener;

import minispring.context.ApplicationListener;

/**
 * @author lihua
 * @since 2021/9/9
 */
public class CustomizedEventListener implements ApplicationListener<CustomizedEvent> {

	@Override
	public void onApplicationEvent(CustomizedEvent event) {
		System.out.println(String.format("监听到自定义的消息： %s， source：%s", event.getMessage(), event.getSource()));
	}
}
