package com.lee.shop.filter;

import com.lee.shop.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LanguageFilter implements Filter {

    private static final String HL = "hl";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        resolveLanguageIfNecessary(request);

        chain.doFilter(servletRequest, servletResponse);
    }

    private void resolveLanguageIfNecessary(HttpServletRequest request) {
        String language = request.getParameter(HL);
        if (language != null) {
            language = language.toLowerCase();
            if (Constants.SUPPORTED_LANGUAGES.contains(language)) {
                request.getSession().setAttribute(Constants.CURRENT_LANGUAGE, language);
                return;
            }
        }
        language = (String) request.getSession().getAttribute(Constants.CURRENT_LANGUAGE);
        if (language == null) {
            language = request.getLocale().getLanguage().toLowerCase();
            if (Constants.SUPPORTED_LANGUAGES.contains(language)) {
                request.getSession().setAttribute(Constants.CURRENT_LANGUAGE, language);
                return;
            }
            request.getSession().setAttribute(Constants.CURRENT_LANGUAGE, Constants.DEFAULT_LANGUAGE);
        }
    }
}
