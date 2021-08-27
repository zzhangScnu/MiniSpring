package minispring.core.io.loader;

import minispring.core.io.resource.ClassPathResource;
import minispring.core.io.resource.FileSystemResource;
import minispring.core.io.resource.Resource;
import minispring.core.io.resource.UrlResource;
import minispring.util.ResourceUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * @author lihua
 * @since 2021/8/27
 */
public class DefaultResourceLoader implements ResourceLoader {

	private static final String CLASSPATH_URL_PREFIX = "classpath:";

	@Override
	public Resource getResource(String location) {
		if (Objects.isNull(location)) {
			throw new IllegalStateException("path cannot be null!");
		}
		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			// 前缀只是用于区分是哪种文件。真正读取的时候要先去掉
			String realLocation = location.substring(CLASSPATH_URL_PREFIX.length());
			return new ClassPathResource(realLocation);
		}
		try {
            URL url = new URL(location);
            return ResourceUtils.isFileUrl(url) ?
                    new FileSystemResource(location) : new UrlResource(url);
        } catch (MalformedURLException e) {
			return new FileSystemResource(location);
		}
	}
}
