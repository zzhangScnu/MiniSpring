package minispring.aop.aspectj;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import minispring.aop.Pointcut;
import minispring.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

import java.util.Objects;

/**
 * @author lihua
 * @since 2021/9/22
 */
@Data
@RequiredArgsConstructor
public class ExpressionPointcutAdvisor implements PointcutAdvisor {

	private final String expression;

	private final Advice advice;

	private ExpressionPointcut pointcut;

	@Override
	public Pointcut getPointcut() {
		if (Objects.isNull(pointcut)) {
			pointcut = new ExpressionPointcut(expression);
		}
		return pointcut;
	}

	@Override
	public Advice getAdvice() {
		return advice;
	}
}
