package com.rocky.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.rocky.server.util.DebugUtil;

public class GenericServletRequest implements HttpServletRequest{
	
	private InputStream is;
	private Socket s;
	private String protocol;
	private String method;
	private String contextPath;
	private String serverName;
	private int port = 80;
	private List<Cookie> cookies = new ArrayList<Cookie>();
	private Map<String,Object> paramterMaps = new HashMap<String,Object>();
	
	public GenericServletRequest(Socket s){
		DebugUtil.printLog("GenericServletRequest -- > socket=" + s);
		this.s = s;
		try {
			this.is = s.getInputStream();
		} catch (IOException e) {
			DebugUtil.printLog("GenericServletRequest -- > is");
			e.printStackTrace();
		}
		try {
			doParse();
		} catch (Exception e) {
			DebugUtil.printLog("GenericServletRequest -- > doParse");
			e.printStackTrace();
		}
	}
	
	private void doParse() throws Exception{
		BufferedReader reader
		   = new BufferedReader(new InputStreamReader(is));
		String line = reader.readLine();
		if(line == null)
			return;
		//GET /index.html HTTP/1.1
		String[] lines = line.split("\\s+");
		this.method = lines[0];
		String uri = lines[1];
		this.protocol = lines[2];
		boolean hasQuery = false;
		int index = 0;
		if((index = uri.indexOf("?")) > 0){
			String queryString = uri.substring(index+1);
			for(String kv : queryString.split("&")){
				paramterMaps.put(kv.split("=")[0], kv.split("=")[1]);
			}
			hasQuery = true;
		}
		if(hasQuery)
			this.contextPath = uri.substring(0, index);
		else
			this.contextPath = uri;
		
		while(!"".equals(line)){
			//System.out.println(line);
			line = reader.readLine();
			if(line.startsWith("Host:")){
				serverName = line.split("\\s")[1].split(":")[0];
				String _port = line.split("\\s")[1].split(":")[1];
				if(!"".equals(_port.trim())){
					port = Integer.parseInt(_port);
				}
			}else if(line.startsWith("Cookie:")){
				String cValues = line.split("\\s")[1];
				for(String cValue : cValues.split(";")){
					if(!"".equals(cValue.trim())){
						//JSESSIONID=0EDF82341CE11DBE5AC7479627D4D652
						cookies.add(new Cookie(cValue.split("=")[0], cValue.split("=")[1]));
					}
				}
			}
		}
	}
	
	public Socket getSocket(){
		return this.s;
	}

	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}

	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getParameter(String name) {
		Object object = paramterMaps.get(name);
		if(object instanceof String){
			return (String)object;
		}else if (object instanceof String[]){
			return ((String[])object)[0];
		}else if (object instanceof List){
			return (String)((List)object).get(0);
		}
		return null;
	}

	public Enumeration getParameterNames() {
		return null;
	}

	public String[] getParameterValues(String name) {
		Object obj = paramterMaps.get(name);
		if(obj instanceof List){
			return (String[])((List)obj).toArray();
		}else if(obj == null){
			return null;
		}else if(obj instanceof String){
			String[] s = new String[1];
			s[0] = (String)obj;
			return s;
		}
		return (String[])obj;
	}

	public Map getParameterMap() {
		return this.paramterMaps;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerName() {
		return serverName;
	}

	public int getServerPort() {
		return port;
	}

	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(String name, Object o) {
		// TODO Auto-generated method stub
		
	}

	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRealPath(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getDateHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getHeaders(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getIntHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getMethod() {
		return this.method;
	}

	public String getPathInfo() {
		return null;
	}

	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContextPath() {
		return this.contextPath;
	}

	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSession getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

}
