package minispring.aop;

import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author lihua
 * @since 2021/9/18
 */
@Data
public class AdvisedSupport {

	private ClassFilter classFilter;

	private MethodMatcher methodMatcher;

	private TargetSource targetSource;

	private MethodInterceptor methodInterceptor;

	public Class<?>[] getTargetClass() {
		return targetSource.getTarget().getClass().getInterfaces();
	}
}
