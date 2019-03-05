package kz.epam.filter;

import kz.epam.constant.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LocaleFilter implements Filter {

    private static final String EXCLUDED_URLS = "excludedUrls";
    private static final String COMMA = ",";
    private List<String> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludePattern = filterConfig.getInitParameter(EXCLUDED_URLS);
        excludedUrls = Arrays.asList(excludePattern.split(COMMA));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String path = ((HttpServletRequest) servletRequest).getServletPath();

        if (!excludedUrls.contains(path)) {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            req.setCharacterEncoding(Constant.ENCODING);
            if (req.getSession().getAttribute(Constant.LOCALE) == null) {
                Locale locale = req.getLocale();
                req.getSession().setAttribute(Constant.LOCALE, locale);
            }
            String language = servletRequest.getParameter(Constant.LANGUAGE);
            if (language != null) {
                Locale locale = new Locale(language);
                req.getSession().setAttribute(Constant.LOCALE, locale);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            servletRequest.getRequestDispatcher(path).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
