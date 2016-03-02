package com.rocky.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Request {
	private String content;
	private String method;
	private String uri;
	private String protocol;
	private InputStream is;
	private Socket s;
	
	public Request(Socket s){
		this.s = s;
		try {
			doParse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doParse() throws Exception{
		BufferedReader reader
		   = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String line = reader.readLine();
		//GET /index.html HTTP/1.1
		String[] lines = line.split("\\s+");
		setMethod(lines[0]);
		setUri(lines[1]);
		setProtocol(lines[2]);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
//	public InputStream getIs() {
//		return is;
//	}
//
//	public void setIs(InputStream is) {
//		this.is = is;
//	}
	
	
	
}
