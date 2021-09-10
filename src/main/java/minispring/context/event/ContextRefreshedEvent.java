package minispring.context.event;

/**
 * @author lihua
 * @since 2021/9/8
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

	public ContextRefreshedEvent(Object source) {
		super(source);
	}
}
