package com.yuqiaotech.helloj2ee.webapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * 做个简单的访问统计，统计数据记录在文件里。然后做一个页面访问排行榜的功能，练习排序，Comparator等。
 * 本类是上述作业的参考答案，但没有文件操作的内容，也基本没有注释。因为仅仅是参考答案 ：-)。
 */
public class SimpleClickstreamFilter implements Filter {
	public final static String ClickstreamMapKey = "simpleClickstreamMap";
	Map<String,Integer> statMap;
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		Integer clicks = statMap.get(uri);
		if(clicks == null)clicks=0;
		clicks++;
		statMap.put(uri, clicks);
		if(uri.endsWith("/simpleClickstream")){
			Set<Map.Entry<String,Integer>> entries = statMap.entrySet();
			List<Map.Entry<String,Integer>> entriesList = new ArrayList();
			entriesList.addAll(entries);
			//也可以用Arrays。
			Collections.sort(entriesList, new Comparator<Map.Entry<String,Integer>>() {
				public int compare(Entry<String, Integer> o1,
						Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			request.setAttribute("entriesList", entriesList);
			request.getRequestDispatcher("/javaweb/simpleClickstream.jsp").forward(request, response);
			return;
		}
	
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
		statMap = new Hashtable();
		fConfig.getServletContext().setAttribute(ClickstreamMapKey, statMap);
	}

	public void destroy() {
	}

}
