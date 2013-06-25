package com.yuqiaotech.helloj2ee.webapp.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 监听器。
 */
public class ContextSetupListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent e) {
		System.out.println("本项目【"+e.getServletContext().getContextPath()+"】被载入了 ContextSetupListener.contextInitialized() 被调用了");
	}
	public void contextDestroyed(ServletContextEvent e) {
		System.out.println("本项目【"+e.getServletContext().getContextPath()+"】被关闭 ContextSetupListener.contextDestroyed() 被调用了。");
	}
}
