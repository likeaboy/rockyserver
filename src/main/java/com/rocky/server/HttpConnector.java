package com.rocky.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import com.rocky.server.util.DebugUtil;

public class HttpConnector implements Runnable{
	
	private ServletProcessorStatck processors = new ServletProcessorStatck(this);
	
	public ServletProcessorStatck getProcessors(){
		return processors;
	}

	public void run() {
		ServerSocket serverSocket = null;
		int port = 9999;
		try {
			serverSocket =  new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		DebugUtil.printLog("Server is started...");
		while(true){
			Socket s;
			try {
				s = serverSocket.accept();
			} catch (IOException e) {
				continue;
			}
			
//			ServletProcessor proccessor = new ServletProcessor(s);
			HttpProcessor proccessor = processors.getProcessor(s);
			if(proccessor == null)
				DebugUtil.printLog("no processor to handle the sorcket...");
			else
				proccessor.assign(s);
		}
		
	}
	
	public void start() {
	    Thread thread = new Thread(this);
	    thread.start();
	  }
	
}
