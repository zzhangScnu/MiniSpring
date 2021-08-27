package minispring.core.io.resource;

import minispring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

/**
 * @author lihua
 * @since 2021/8/27
 */
public class ClassPathResource implements Resource {

	private final String path;

	private final ClassLoader classLoader;

	public ClassPathResource(String path) {
		this(path, null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		if (Objects.isNull(path)) {
			throw new IllegalStateException("path cannot be null!");
		}
		this.path = path;
		this.classLoader = Optional.ofNullable(classLoader)
				.orElse(ClassUtils.getDefaultClassLoader());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream inputStream = classLoader.getResourceAsStream(path);
		if (Objects.isNull(inputStream)) {
			throw new IOException(String.format("get input stream of %s failed: ", path));
		}
		return inputStream;
	}
}
