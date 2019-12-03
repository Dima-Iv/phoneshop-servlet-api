package com.es.phoneshop.web.filter;

import com.es.phoneshop.web.security.DosProtectionService;
import com.es.phoneshop.web.security.MapDosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;

public class DosFilter implements Filter {
    private DosProtectionService dosProtectionService;
    Timer timer = new Timer();

    @Override
    public void init(FilterConfig filterConfig) {
        dosProtectionService = MapDosProtectionService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (dosProtectionService.allowed((HttpServletRequest) servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        ((HttpServletResponse) servletResponse).sendError(429);
    }

    @Override
    public void destroy() {

    }
}
