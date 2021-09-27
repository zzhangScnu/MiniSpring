package minispring.aop.aspectj;

import lombok.Data;
import lombok.NoArgsConstructor;
import minispring.aop.Pointcut;
import minispring.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

import java.util.Objects;

/**
 * 整合了拦截表达式、切点和执行操作的类
 * 切点对象由表达式字符串转换而来
 * 无参构造方法，是实例化时使用的
 * 里面的参数，setPropertyValue时赋值
 *
 * @author lihua
 * @since 2021/9/22
 */
@Data
@NoArgsConstructor
public class ExpressionPointcutAdvisor implements PointcutAdvisor {

	private String expression;

	private Advice advice;

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
