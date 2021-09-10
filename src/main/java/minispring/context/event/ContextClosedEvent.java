package minispring.context.event;

/**
 * @author lihua
 * @since 2021/9/8
 */
public class ContextClosedEvent extends ApplicationContextEvent {

	public ContextClosedEvent(Object source) {
		super(source);
	}
}
