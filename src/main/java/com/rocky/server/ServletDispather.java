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
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rocky.server.servlet.BlogServlet;

public class ServletDispather {
	
	private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";

    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";
    
    private static final String LSTRING_FILE =
	"javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings =
	ResourceBundle.getBundle(LSTRING_FILE);
    
    private static final String CONTEXT_PATH = "/Users/rocky/Documents/workspace-pgc/server";
	private static final String WEBROOT = "/src/main/webapp";
	
	
	public static ServletResponse doDispather(ServletRequest servReq){
		HttpServletRequest httpServReq = (HttpServletRequest)servReq;
		String ctxPath = httpServReq.getContextPath();
		if(ctxPath.startsWith("/servlet")){
			return findServletResource(httpServReq);
		}else{
			return doFindStaticResource(httpServReq);
		}
	}
	
	private static String getResourceName(String absResPath){
		return CONTEXT_PATH + WEBROOT + absResPath;
	}
	
	private static HttpServletResponse doFindStaticResource(HttpServletRequest req){
		GenericServletRequest request = (GenericServletRequest)req;
		HttpServletResponse response = new GenericServletResponse(request.getSocket());
		if(METHOD_GET.equals(request.getMethod())){
			File file = new File(getResourceName(req.getContextPath()));
			BufferedReader br = null;
			BufferedWriter bw = null;
			
			try {
				if(file.exists()){
					br = new BufferedReader(new FileReader(file));
					bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),"UTF-8"));
					String line = br.readLine();
					while(line!=null && !"".equals(line)){
						bw.write(line);
						line = br.readLine(); 
					}
					bw.flush();
					response.getOutputStream().flush();
				}else{
					byte[] errorBs = "Server ERROR:404 NOT FOUND".getBytes();
					response.getOutputStream().write(errorBs);
					response.getOutputStream().flush();
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(br!=null){
					try {
						br.close();
						br = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(bw!=null){
					try {
						bw.close();
						bw = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
//				try {
//					response.getS().getOutputStream().close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}else if(METHOD_POST.equals(request.getMethod())){
			
		}

		return response;
	}
	
	
	public static HttpServletResponse findServletResource(HttpServletRequest request){
		HttpServletResponse response = new GenericServletResponse(((GenericServletRequest)request).getSocket());
		String contextPath = request.getContextPath();
		String servletName = contextPath.split("/")[2];
		String repository = CONTEXT_PATH + WEBROOT;
		try {
			URL url = new URL(repository);
			URLClassLoader loader = new URLClassLoader(new URL[]{url});
			Class servletClass = loader.loadClass("com.rocky.server.servlet.BlogServlet");
			BlogServlet bServlet = (BlogServlet)servletClass.newInstance();
			bServlet.service(request, response);;
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
