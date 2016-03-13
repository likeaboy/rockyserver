package com.rocky.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rocky.server.servlet.BlogServlet;

public class ServletProcessor {
	
	public static HttpServletResponse findServletResource(HttpServletRequest request){
		HttpServletResponse response = new GenericServletResponse(((GenericServletRequest)request).getSocket());
		OutputStream output = null;
		try {
			output = ((GenericServletResponse)response).getSocket().getOutputStream();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		String contextPath = request.getContextPath();
		String servletName = contextPath.split("/")[2];
//		String repository = CONTEXT_PATH + WEBROOT;
		String repository = "";
		File file = new File(repository);
		try {
			URL url = file.toURI().toURL();
			URLClassLoader loader = new URLClassLoader(new URL[]{url});
			Class servletClass = loader.loadClass("com.rocky.server.servlet." + servletName);
			HttpServlet servlet = (HttpServlet)servletClass.newInstance();
			
			String responseHeader = "HTTP/1.1 200 \r\n" +
			        "Content-Type: text/html;charset=UTF-8\r\n" +
			        "\r\n";
			output.write(responseHeader.getBytes());
			
			servlet.service(request, response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			 String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
				        "Content-Type: text/html\r\n" +
				        "Content-Length: 1024\r\n" +
				        "\r\n" +
				        "<h1>Servlet Error: No Servlet to handle the request!</h1>";
				      try {
						output.write(errorMessage.getBytes());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return response;
	}
}
