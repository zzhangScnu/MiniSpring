package minispring.aop;

import lombok.Data;
import minispring.util.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author lihua
 * @since 2021/9/18
 */
@Data
public class AdvisedSupport {

	/**
	 * 默认是false，即用JDK方式实例化
	 */
	private boolean proxyTargetClass;

	private ClassFilter classFilter;

	private MethodMatcher methodMatcher;

	private TargetSource targetSource;

	private MethodInterceptor methodInterceptor;

	public Class<?>[] getTargetClass() {
		// 从容器中取出来的对象，是通过JDK或CGLIB实例化的。
		// 所以，如果是前者，跟目标对象实现了同一个接口，直接getClass就可以了。如果是后者，要getSuperclass一下
		Object target = targetSource.getTarget();
		Class<?> originalClass = target.getClass();
		Class<?> clazz = ClassUtils.isCglibProxyClass(originalClass) ? originalClass.getSuperclass() : originalClass;
		return clazz.getInterfaces();
	}
}
