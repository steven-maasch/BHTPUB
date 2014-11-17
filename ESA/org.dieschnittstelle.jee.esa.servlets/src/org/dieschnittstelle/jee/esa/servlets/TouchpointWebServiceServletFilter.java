package org.dieschnittstelle.jee.esa.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class TouchpointWebServiceServletFilter implements Filter {

	protected static Logger logger = Logger
			.getLogger(TouchpointWebServiceServletFilter.class);

	public TouchpointWebServiceServletFilter() {
		System.err.println("TouchpointWebServiceServletFilter: constructor invoked\n");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		System.err.println("TouchpointWebServiceServletFilter: doFilter() invoked\n");
		
		String acceptLanguageHeader = ((HttpServletRequest) req).getHeader("accept-language");
		
		logger.info("got accept-language header: " + acceptLanguageHeader);
		
		if (acceptLanguageHeader == null) {
			chain.doFilter(req, resp);
		} else {
			((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
