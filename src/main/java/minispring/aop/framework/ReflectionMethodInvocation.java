package minispring.aop.framework;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author lihua
 * @since 2021/9/18
 */
@Getter
@RequiredArgsConstructor
public class ReflectionMethodInvocation implements MethodInvocation {

	protected final Object target;

	protected final Method method;

	protected final Object[] args;

	@Override
	public Object proceed() throws Throwable {
		return method.invoke(target, args);
	}

	@Override
	public Object getThis() {
		return this;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return method;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Object[] getArguments() {
		return args;
	}
}
