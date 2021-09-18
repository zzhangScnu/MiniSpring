package minispring.aop;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 代理对象
 *
 * @author lihua
 * @since 2021/9/18
 */
@Data
@AllArgsConstructor
public class TargetSource {

	/**
	 * 目标对象
	 */
	private final Object target;
}
