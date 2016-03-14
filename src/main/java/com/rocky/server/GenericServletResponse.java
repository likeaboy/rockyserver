package com.rocky.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenericServletResponse implements HttpServletResponse{
	
	private Socket socket;
	// the default buffer size
	  private static final int BUFFER_SIZE = 1024;
	  HttpServletRequest request;
	  OutputStream output;
	
	public GenericServletResponse(Socket socket){
		this.socket = socket;
		try {
			this.output = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket(){
		return this.socket;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCharacterEncoding(String charset) {
		// TODO Auto-generated method stub
		
	}

	public void setContentLength(int len) {
		// TODO Auto-generated method stub
		
	}

	public void setContentType(String type) {
		// TODO Auto-generated method stub
		
	}

	public void setBufferSize(int size) {
		// TODO Auto-generated method stub
		
	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void resetBuffer() {
		// TODO Auto-generated method stub
		
	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub
		
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
		
	}

	public boolean containsHeader(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public String encodeURL(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeRedirectURL(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public String encodeRedirectUrl(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendError(int sc, String msg) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendError(int sc) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void sendRedirect(String location) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void setDateHeader(String name, long date) {
		// TODO Auto-generated method stub
		
	}

	public void addDateHeader(String name, long date) {
		// TODO Auto-generated method stub
		
	}

	public void setHeader(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	public void addHeader(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	public void setIntHeader(String name, int value) {
		// TODO Auto-generated method stub
		
	}

	public void addIntHeader(String name, int value) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(int sc) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(int sc, String sm) {
		// TODO Auto-generated method stub
		
	}
	
	/* This method is used to serve a static page */
	  public void sendStaticResource() throws IOException {
	    byte[] bytes = new byte[BUFFER_SIZE];
	    FileInputStream fis = null;
	    try {
	      /* request.getUri has been replaced by request.getRequestURI */
	      File file = new File(Constants.CONTEXT_PATH + Constants.WEBROOT, request.getContextPath());
	      fis = new FileInputStream(file);
	      /*
	         HTTP Response = Status-Line
	           *(( general-header | response-header | entity-header ) CRLF)
	           CRLF
	           [ message-body ]
	         Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
	      */
	      int ch = fis.read(bytes, 0, BUFFER_SIZE);
	      while (ch!=-1) {
	        output.write(bytes, 0, ch);
	        ch = fis.read(bytes, 0, BUFFER_SIZE);
	      }
	    }
	    catch (FileNotFoundException e) {
	      String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
	        "Content-Type: text/html\r\n" +
	        "Content-Length: 23\r\n" +
	        "\r\n" +
	        "<h1>File Not Found</h1>";
	      output.write(errorMessage.getBytes());
	    }
	    finally {
	      if (fis!=null)
	        fis.close();
	    }
	  }
	  
	  public void setRequest(HttpServletRequest request) {
		    this.request = request;
		  }
	
}
