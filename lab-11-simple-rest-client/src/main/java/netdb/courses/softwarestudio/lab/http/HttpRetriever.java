package netdb.courses.softwarestudio.lab.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpRetriever {

	private final HttpClient client;
	private final HttpClientContext context;

	public HttpRetriever() {
		client = HttpClients.createDefault();
		context = HttpClientContext.create();
		context.setCookieStore(new BasicCookieStore());
	}

	/**
	 * Get content of the given URL and parameters as a string.
	 * 
	 * @param url
	 *            the base URL of the content you want to get.
	 * 
	 * @param params
	 *            the parameters of the request wrapped in a map.
	 * 
	 * @return the string of the content
	 * */
	public String getContent(String url, Map<String, String> params) {

		String content = null;

		try {

			// Build up the URL
			URIBuilder builder = new URIBuilder(url);

			// Add parameters to the builder
			// to form a URL like `url?key1=val2&key2=val2...`
			if (params != null) {
				for (Entry<String, String> e : params.entrySet()) {
					builder.addParameter(e.getKey(), e.getValue());
				}
			}

			// Let the client perform the request based on the URL we build
			HttpResponse response = client.execute(
					new HttpGet(builder.build()), context);

			// Get the response
			HttpEntity entity = response.getEntity();

			// Extract character set from the response
			String charSet = ContentType.getOrDefault(entity).getCharset()
					.name();

			// Make a string based on the returned content
			// StringEscapeUtils is used for escaping UNICODE characters
			// For example: '\u8449' to '葉'
			content = StringEscapeUtils.unescapeJava(EntityUtils.toString(
					entity, charSet));

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (URISyntaxException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return content;

	}
}
