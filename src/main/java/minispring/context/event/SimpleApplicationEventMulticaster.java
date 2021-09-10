package minispring.context.event;

import minispring.context.ApplicationEvent;

/**
 * @author lihua
 * @since 2021/9/8
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

	@Override
	public void multicasterEvent(ApplicationEvent applicationEvent) {
		getApplicationListeners(applicationEvent)
				.forEach(listener -> listener.onApplicationEvent(applicationEvent));
	}
}
