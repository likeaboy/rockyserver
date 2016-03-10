package com.rocky.server;

import java.io.IOException;
import java.net.Socket;

import javax.servlet.ServletRequest;

import com.rocky.server.util.DebugUtil;

public class ServletProcessor implements Runnable{
	
	private Socket socket;
	private ServletConnector connector;
	
	public ServletProcessor(Socket socket){
		this.socket = socket;
	}
	
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	
	public void process(ServletConnector connector){
		this.connector = connector;
		Thread t = new Thread(this);
		t.start();
		DebugUtil.printLog("process start...");
		DebugUtil.printLog("this process=" + this);
	}
	/**
	 * 1.接收socket对象
	 * 2.产生reqest对象，解析http协议头信息，写入request
	 * 
	 */
	public void run() {
		DebugUtil.printLog("ServletProcessor --> socket="+this.socket);
		if(this.socket.isClosed()){
			this.connector.getProcessors().recycle(this);
			return;
		}
		try{
			ServletRequest req = new GenericServletRequest(this.socket);
			if(req == null)
				System.out.println("=================thread="+Thread.currentThread().getName() + " processor=" + this);
			ServletDispather.doDispather(req);
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
}
