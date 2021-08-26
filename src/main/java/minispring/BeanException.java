package minispring;

/**
 * @author lihua
 * @since 2021/8/26
 */
public class BeanException extends RuntimeException {

	public BeanException(String message) {
		super(message);
	}

	public BeanException(String message, Throwable cause) {
		super(message, cause);
	}
}
