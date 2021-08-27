package minispring.core.io.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author lihua
 * @since 2021/8/27
 */
public class UrlResource implements Resource {

	private URI uri;

	private final URL url;

	public UrlResource(URI uri) throws MalformedURLException {
		this.uri = uri;
		this.url = uri.toURL();
	}

	public UrlResource(URL url) {
		this.url = url;
		this.uri = null;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URLConnection urlConnection = url.openConnection();
		return urlConnection.getInputStream();
	}
}
