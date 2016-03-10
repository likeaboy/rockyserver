package com.rocky.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

import javax.servlet.ServletRequest;

import com.rocky.server.util.DebugUtil;

public class ServletConnector implements Runnable{
	
	private ServletProcessorStatck processors = new ServletProcessorStatck();
	
	public ServletProcessorStatck getProcessors(){
		return processors;
	}

	public void run() {
		DebugUtil.printLog("Server is started...");
		try {
			ServerSocket serSocket = new ServerSocket(9999);
			while(true){
				Socket s = serSocket.accept();
				
//				ServletProcessor proccessor = new ServletProcessor(s);
				ServletProcessor proccessor = processors.getProcessor(s);
				if(proccessor == null)
					DebugUtil.printLog("Do not process...");
				else
					proccessor.process(this);
			}
		} catch (IOException e) {
			DebugUtil.printLog("ServletConnector --> run");
			e.printStackTrace();
		}
	}
	
}
