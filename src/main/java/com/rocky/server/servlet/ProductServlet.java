package com.rocky.server.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rocky.server.GenericServletResponse;

public class ProductServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3139260041046539846L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pid = req.getParameter("pid");
		if(pid != null){
			GenericServletResponse response = (GenericServletResponse)resp;
			response.getSocket().getOutputStream().write("<h1>".getBytes());
			response.getSocket().getOutputStream().write("Production Info".getBytes());
			response.getSocket().getOutputStream().write("</h1>".getBytes());
			response.getSocket().getOutputStream().write("<table>".getBytes());
			response.getSocket().getOutputStream().write("<th>ID</th><th>Name</th>".getBytes());
			response.getSocket().getOutputStream().write(("<tr><td>" + pid + "</td><td>Big Dog</td>").getBytes());
			response.getSocket().getOutputStream().write("</table>".getBytes());
		}
	}
	
	
}
