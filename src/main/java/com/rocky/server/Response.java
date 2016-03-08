package com.rocky.server;

import java.net.Socket;

public class Response {
	private String content;
	private Socket s;
	
	public Response(Socket s){
		this.s = s;
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
}
