package minispring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author lihua
 * @since 2021/9/18
 */
public class HealthServiceInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("开始执行方法");
		long startTime = System.nanoTime();
		try {
			return invocation.proceed();
		} finally {
			long timeSpent = System.nanoTime() - startTime;
			System.out.println(String.format("执行方法完毕，耗时%s纳秒", timeSpent));
		}
	}
}
