package minispring.core.io.loader;

import minispring.core.io.resource.Resource;

/**
 * @author lihua
 * @since 2021/8/27
 */
public interface ResourceLoader {

	/**
	 * 获取资源
	 *
	 * @param location 资源位置
	 * @return Resource
	 */
	Resource getResource(String location);
}
