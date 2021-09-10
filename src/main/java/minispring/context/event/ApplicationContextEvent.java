package minispring.context.event;

import minispring.context.ApplicationContext;
import minispring.context.ApplicationEvent;

/**
 * @author lihua
 * @since 2021/9/8
 */
public class ApplicationContextEvent extends ApplicationEvent {

	public ApplicationContextEvent(Object source) {
		super(source);
	}

	public ApplicationContext getApplicationContext() {
		return (ApplicationContext) getSource();
	}
}
