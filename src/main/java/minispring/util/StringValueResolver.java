package minispring.util;

/**
 * @author lihua
 * @since 2021/9/29
 */
public interface StringValueResolver {

	/**
	 * 将注解中的${xxx}转换成配置文件中实际的值
	 *
	 * @param strVal 占位符
	 * @return 实际的值，可能为null
	 */
	String resolveStringValue(String strVal);
}
