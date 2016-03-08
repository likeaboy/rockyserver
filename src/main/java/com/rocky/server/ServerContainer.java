package com.rocky.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletRequest;

public class ServerContainer implements Runnable{

	public void run() {
		try {
			ServerSocket serSocket = new ServerSocket(9999);
			while(true){
				Socket s = serSocket.accept();
				ServletRequest req = null;
				try {
					req = new GenericServletRequest(s);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				while(!line.equals("")){
//					System.out.println(line);
//					line = reader.readLine();
//				}
				int contentLength = 0;
				ServletDispather.doDispather(req);
//				Response response = findReource(req);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
