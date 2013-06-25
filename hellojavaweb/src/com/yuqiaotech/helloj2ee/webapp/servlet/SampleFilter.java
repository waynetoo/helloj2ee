package com.yuqiaotech.helloj2ee.webapp.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class SampleFilter
 */
public class SampleFilter implements Filter {


    public SampleFilter() {
        System.out.println("SampleFilter.SampleFilter():过滤器实例化");
    }

	public void destroy() {
		System.out.println("SampleFilter.destroy():过滤器销毁");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	//这是为了UserDaoSecurityProxy里能获取request之类的对象。

		System.out.println("SampleFilter.doFilter() 被调用了 ");
		// place your code here
		System.out.println("request protocol = "+request.getProtocol());
		System.out.println("request localAddr = "+request.getLocalAddr());
		System.out.println("request URI = "+((HttpServletRequest)request).getRequestURI());
		System.out.println("request query String = "+((HttpServletRequest)request).getQueryString());
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
		System.out.println("SampleFilter.doFilter() 调用结束");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String name = fConfig.getInitParameter("name");
		System.out.println("SampleFilter.init() 拦截器初始化方法被调用了 name="+name);
	}

}
