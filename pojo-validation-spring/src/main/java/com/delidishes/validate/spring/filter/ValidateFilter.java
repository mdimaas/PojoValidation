package com.delidishes.validate.spring.filter;


import com.delidishes.validate.spring.ValidateHttpServletWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ValidateFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(ValidateFilter.class);

    private String encoding;
    private List<String> contentTypes;

    @Override
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = StandardCharsets.UTF_8.toString();
        }
        LOG.info(String.format("Init [%s] with encoding %s", this.getClass().getName(), encoding));

        String contentTypes = config.getInitParameter("contentTypes");
        this.contentTypes = contentTypes == null ? Collections.emptyList() : Arrays.stream(contentTypes.split(",")).map(String::trim).collect(toList());
        LOG.info(String.format("Init [%s] with contentTypes %s", this.getClass().getName(), contentTypes));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (getContentTypes().contains(request.getContentType())) {
            chain.doFilter(new ValidateHttpServletWrapper((HttpServletRequest) request, getEncoding()), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        LOG.info(String.format("Destroy [%s] with encoding %s", this.getClass().getName(), encoding));
    }

    public String getEncoding() {
        return encoding;
    }

    public List<String> getContentTypes() {
        return contentTypes;
    }
}
