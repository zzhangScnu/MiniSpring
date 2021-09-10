package minispring.listener;

import lombok.Getter;
import minispring.context.ApplicationEvent;

/**
 * @author lihua
 * @since 2021/9/9
 */
@Getter
public class CustomizedEvent extends ApplicationEvent {

	private final String message;

	public CustomizedEvent(Object source, String message) {
		super(source);
		this.message = message;
	}
}
