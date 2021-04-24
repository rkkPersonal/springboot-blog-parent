/*
package org.xr.org.xr.happy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * @author Steven
 *//*

@WebFilter(filterName = "charactersEncoderFilter")
@Component
public class CharactersEncoderFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CharactersEncoderFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("开始执行 CharactersEncoderFilter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setContentType("application/json; charset=UTF-8");
        resp.setCharacterEncoding("utf-8");

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
*/
