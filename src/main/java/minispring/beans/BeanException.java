package minispring.beans;

/**
 * @author lihua
 * @since 2021/8/26
 */
public class BeanException extends RuntimeException {

	public BeanException(String message, Object... args) {
		super(String.format(message, args));
	}

	public BeanException(String message, Throwable cause, Object... args) {
		super(String.format(message, args), cause);
	}
}
