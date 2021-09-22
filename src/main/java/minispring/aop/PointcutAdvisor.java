package minispring.aop;

/**
 * @author lihua
 * @since 2021/9/22
 */
public interface PointcutAdvisor extends Advisor {

	/**
	 * 获取切点
	 *
	 * @return pointcut
	 */
	Pointcut getPointcut();
}
