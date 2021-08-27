package minispring.util;

import java.net.URL;

/**
 * @author lihua
 * @since 2021/8/27
 */
public class ResourceUtils {

	private ResourceUtils() {
		throw new UnsupportedOperationException("Utility class!");
	}

	/**
	 * URL protocol for a file in the file system: "file".
	 */
	public static final String URL_PROTOCOL_FILE = "file";

	public static boolean isFileUrl(URL url) {
		String protocol = url.getProtocol();
		return URL_PROTOCOL_FILE.equals(protocol);
	}
}
