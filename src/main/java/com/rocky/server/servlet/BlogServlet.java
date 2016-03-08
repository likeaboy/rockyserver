package com.rocky.server.servlet;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.rocky.server.GenericServletResponse;

public class BlogServlet extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		GenericServletResponse response = (GenericServletResponse)res;
		response.getSocket().getOutputStream().write("<h1>".getBytes());
		response.getSocket().getOutputStream().write("This is Blog Servlet!".getBytes());
		response.getSocket().getOutputStream().write("</h1>".getBytes());
	}
	
}
