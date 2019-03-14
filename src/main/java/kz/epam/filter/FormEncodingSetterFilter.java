package kz.epam.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FormEncodingSetterFilter implements Filter {

    private static final String FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String EXCLUDED_URLS = "excludedUrls";
    private static final String COMMA = ",";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final String ENCODING_INIT_PARAM_NAME = "encoding";

    private List<String> excludedUrls;
    private String encoding;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {
        String path = ((HttpServletRequest) req).getServletPath();

        if (!excludedUrls.contains(path)) {
            String contentType = req.getContentType();
            if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE))
                req.setCharacterEncoding(encoding);
            chain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher(path).forward(req, resp);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePattern = filterConfig.getInitParameter(EXCLUDED_URLS);
        excludedUrls = Arrays.asList(excludePattern.split(COMMA));
        encoding = filterConfig.getInitParameter(ENCODING_INIT_PARAM_NAME);
        if (encoding == null)
            encoding = ENCODING_DEFAULT;
    }
}
