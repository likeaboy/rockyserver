package com.rocky.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rocky.server.servlet.BlogServlet;

public class ServletProcessor {
	
	public static HttpServletResponse findServletResource(HttpServletRequest request){
		HttpServletResponse response = new GenericServletResponse(((GenericServletRequest)request).getSocket());
		String contextPath = request.getContextPath();
		String servletName = contextPath.split("/")[2];
//		String repository = CONTEXT_PATH + WEBROOT;
		String repository = "";
		File file = new File(repository);
		try {
			URL url = file.toURI().toURL();
			URLClassLoader loader = new URLClassLoader(new URL[]{url});
			Class servletClass = loader.loadClass("com.rocky.server.servlet." + servletName);
			BlogServlet bServlet = (BlogServlet)servletClass.newInstance();
			bServlet.service(request, response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
