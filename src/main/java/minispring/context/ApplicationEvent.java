package minispring.context;

import java.util.EventObject;

/**
 * @author lihua
 * @since 2021/9/8
 */
public class ApplicationEvent extends EventObject {

	public ApplicationEvent(Object source) {
		super(source);
	}
}
