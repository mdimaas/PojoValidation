package com.delidishes.validate.spring;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ValidateHttpServletWrapper extends HttpServletRequestWrapper implements HttpInputMessage {

	private String encoding;
	private String body;

	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request - current request
	 * @throws IllegalArgumentException if the request is null
	 * @throws IOException if not parse request
	 */
	public ValidateHttpServletWrapper(HttpServletRequest request) throws IOException {
		this(request, StandardCharsets.UTF_8.name());
	}

	public ValidateHttpServletWrapper(HttpServletRequest request, String encoding) throws IOException {
		super(request);
		this.encoding = encoding;
		this.body = getRequestBodyAsString(request);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		InputStream stream = new ByteArrayInputStream(body.getBytes(encoding));
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				throw new UnsupportedOperationException();
			}

			@Override
			public int read() throws IOException {
				return stream.read();
			}
		};
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	public String getStringBody() {
		return this.body;
	}

	@Override
	public InputStream getBody() throws IOException {
		return new ByteArrayInputStream(body.getBytes(encoding));
	}

	public String getRequestBodyAsString(HttpServletRequest request) throws IOException {
		StringBuilder requestBody = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), encoding));
		String line;
		while ((line = reader.readLine()) != null) {
			requestBody.append(line);
		}
		return requestBody.toString();
	}

	@Override
	public HttpHeaders getHeaders() {
		return null;
	}
}
