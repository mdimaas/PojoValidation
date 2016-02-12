package com.delidishes.validate.spring;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ValidateHttpServletWrapper extends HttpServletRequestWrapper {

	private String body;

	/**
	 * Constructs a request object wrapping the given request.
	 *
	 * @param request
	 * @throws IllegalArgumentException if the request is null
	 */
	public ValidateHttpServletWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.body = getRequestBodyAsString(request);
	}


	@Override
	public ServletInputStream getInputStream() throws IOException {
		InputStream stream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
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

	public String getBody() {
		return this.body;
	}

	public String getRequestBodyAsString(HttpServletRequest request) throws IOException {
		StringBuilder requestBody = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
		String line;
		while ((line = reader.readLine()) != null) {
			requestBody.append(line);
		}
		return requestBody.toString();
	}
}
