package com.delidishes.validate.spring.filter;


import com.delidishes.validate.spring.ValidateHttpServletWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ValidateFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ValidateFilter.class);

    private String encoding;

    @Override
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = StandardCharsets.UTF_8.toString();
        }
        LOG.info(String.format("Init [%s] with encoding %s", this.getClass().getName(), encoding));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new ValidateHttpServletWrapper((HttpServletRequest) request, encoding), response);
    }

    @Override
    public void destroy() {
        LOG.info(String.format("Destroy [%s] with encoding %s", this.getClass().getName(), encoding));
    }
}
