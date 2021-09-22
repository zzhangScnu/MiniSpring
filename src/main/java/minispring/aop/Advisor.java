package minispring.aop;

import org.aopalliance.aop.Advice;

/**
 * @author lihua
 * @since 2021/9/22
 */
public interface Advisor {

	/**
	 * 获取要执行的操作
	 *
	 * @return advice
	 */
	Advice getAdvice();
}
