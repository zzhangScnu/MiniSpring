package minispring.core.io.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lihua
 * @since 2021/8/27
 */
public interface Resource {

	/**
	 * 获取资源流。
	 * 可以重复调用此接口，获取新的流
	 *
	 * @return 资源的InputStream
	 * @throws IOException IO异常
	 */
	InputStream getInputStream() throws IOException;
}
