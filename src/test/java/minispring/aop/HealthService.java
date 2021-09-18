package minispring.aop;

/**
 * @author lihua
 * @since 2021/8/23
 */
public class HealthService implements IHealthService {

	@Override
	public Boolean healthCheck() {
		System.out.println("健康检查中...");
		return true;
	}
}
