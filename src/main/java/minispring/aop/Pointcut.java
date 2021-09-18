package minispring.aop;

/**
 * @author lihua
 * @since 2021/9/18
 */
public interface Pointcut {

	/**
	 * 获取类匹配对象
	 *
	 * @return 类匹配对象
	 */
	ClassFilter getClassFilter();

	/**
	 * 获取方法匹配对象
	 *
	 * @return 方法匹配对象
	 */
	MethodMatcher getMethodMatcher();
}
