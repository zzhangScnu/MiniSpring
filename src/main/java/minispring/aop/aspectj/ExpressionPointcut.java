package minispring.aop.aspectj;

import minispring.aop.ClassFilter;
import minispring.aop.MethodMatcher;
import minispring.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.aspectj.weaver.tools.PointcutPrimitive.EXECUTION;

/**
 * @author lihua
 * @since 2021/9/18
 */
public class ExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

	private static final Set<PointcutPrimitive> POINTCUT_PRIMITIVES = new HashSet<PointcutPrimitive>() {{
		this.add(EXECUTION);
	}};

	private final PointcutExpression pointcutExpression;

	public ExpressionPointcut(String expression) {
		PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
				POINTCUT_PRIMITIVES, this.getClass().getClassLoader());
		this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
	}

	@Override
	public boolean matches(Class<?> clazz) {
		return pointcutExpression.couldMatchJoinPointsInType(clazz);
	}

	@Override
	public boolean matches(Method method, Class<?> clazz) {
		return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
	}

	@Override
	public ClassFilter getClassFilter() {
		return this;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return this;
	}
}
