package com.rocky.server.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class BlogServlet extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		res.getOutputStream().println("<h1>");
		res.getOutputStream().println("This is Blog Servlet!");
		res.getOutputStream().println("</h1>");
	}
	
}
