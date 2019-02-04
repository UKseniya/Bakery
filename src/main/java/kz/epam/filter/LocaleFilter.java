package kz.epam.filter;

import kz.epam.constants.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        req.setCharacterEncoding(Constants.ENCODING);
        if (req.getSession().getAttribute(Constants.LOCALE) == null) {
            req.getSession().setAttribute(Constants.LOCALE,req.getLocale());
        }
        String language = servletRequest.getParameter(Constants.LANGUAGE);
        String country = servletRequest.getParameter(Constants.COUNTRY);
        if (language != null) {
            Locale locale = new Locale(language, country);
            req.getSession().setAttribute(Constants.LOCALE, locale);
        }

        filterChain.doFilter(servletRequest, servletResponse);

//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        if (req.getParameter("cookieLocale") != null) {
//            Cookie cookie = new Cookie("lang", req.getParameter("cookieLocale"));
//            res.addCookie(cookie);
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
