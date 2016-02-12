package com.delidishes.validate.spring.filter;


import com.delidishes.validate.spring.ValidateHttpServletWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.delidishes.validate.spring.ValidateSpringUtils.SUPPORT_CONTENT_TYPES;

public class ValidateFilter implements Filter {

	private String encoding;

	@Override
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("requestEncoding");
		if (encoding == null) {
			encoding = StandardCharsets.UTF_8.toString();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (SUPPORT_CONTENT_TYPES.contains(request.getContentType())) {
			request.setCharacterEncoding(encoding);
			chain.doFilter(new ValidateHttpServletWrapper((HttpServletRequest) request), response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}
}
