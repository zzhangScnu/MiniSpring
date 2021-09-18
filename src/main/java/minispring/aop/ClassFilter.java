package minispring.aop;

/**
 * @author lihua
 * @since 2021/9/18
 */
public interface ClassFilter {

	/**
	 * 是否拦截类
	 *
	 * @param clazz 类对象
	 * @return 是否拦截
	 */
	boolean matches(Class<?> clazz);
}
