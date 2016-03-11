package com.rocky.server;

import java.io.IOException;
import java.net.Socket;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rocky.server.util.DebugUtil;

public class HttpProcessor implements Runnable{
	
	private Socket socket;
	private HttpConnector connector;
	private boolean available = false;
	
	public HttpProcessor(HttpConnector connector){
		this.connector = connector;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	
	public void process(Socket socket){
		DebugUtil.printLog("ServletProcessor --> socket="+socket);
		if(this.socket.isClosed()){
			this.connector.getProcessors().recycle(this);
			return;
		}
		try{
			HttpServletRequest request = new GenericServletRequest(socket);
//			ServletDispather.doDispather(req);
			HttpServletResponse response = null;
			String ctxPath = request.getContextPath();
			if(ctxPath.startsWith("/servlet")){
				response = ServletProcessor.findServletResource(request);
			}else{
				response = StaticResourceProcessor.doFindStaticResource(request);
			}
			
			//解析response
			response.setHeader("Server", "Rocky Servlet Container");
			
			this.connector.getProcessors().recycle(this);
			DebugUtil.printLog("processor end...");
			try {
				if(!this.socket.isClosed())
					this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(Exception ee){
			this.connector.getProcessors().recycle(this);
			DebugUtil.printLog("processor end...");
		}
	}
	/**
	 * 1.接收socket对象
	 * 2.产生reqest对象，解析http协议头信息，写入request
	 * 
	 */
	public void run() {
		while(true){
			Socket socket = await();
			if(socket == null){
				continue;
			}
			
			process(socket);
		}
	}
	
	synchronized void assign(Socket socket){
		while(available){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.socket = socket;
		available = true;
		notifyAll();
	}
	
	private synchronized Socket await(){
		while(!available){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Socket socket = this.socket;
		available = false;
		notifyAll();
		return socket;
	}
}
