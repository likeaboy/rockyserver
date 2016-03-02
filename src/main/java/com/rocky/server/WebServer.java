package com.rocky.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WebServer {
	private static final String CONTEXT_PATH = "/Users/rocky/Documents/workspace-pgc/server";
	private static final String WEBROOT = "/src/main/webapp";
	private static final String GET = "GET";
	private static final String POST = "POST";
	
	public static void main(String[] args) {
		try {
			ServerSocket serSocket = new ServerSocket(9999);
			while(true){
				Socket s = serSocket.accept();
				Request req = new Request(s);
//				while(!line.equals("")){
//					System.out.println(line);
//					line = reader.readLine();
//				}
				int contentLength = 0;
				Response response = findReource(req);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Response findReource(Request req){
		Response response = new Response(req.getS());
		if(GET.equals(req.getMethod())){
			File file = new File(getResourceName(req.getUri()));
			BufferedReader br = null;
			BufferedWriter bw = null;
			
			try {
				if(file.exists()){
					br = new BufferedReader(new FileReader(file));
					bw = new BufferedWriter(new OutputStreamWriter(response.getS().getOutputStream(),"UTF-8"));
					String line = br.readLine();
					while(line!=null && !"".equals(line)){
						bw.write(line);
						line = br.readLine(); 
					}
					bw.flush();
					response.getS().getOutputStream().flush();
				}else{
					byte[] errorBs = "Server ERROR:404 NOT FOUND".getBytes();
					response.getS().getOutputStream().write(errorBs);
					response.getS().getOutputStream().flush();
					
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
		}else if(POST.equals(req.getMethod())){
			
		}

		return response;
	}
	
	private static String getResourceName(String absResPath){
		return CONTEXT_PATH + WEBROOT + absResPath;
	}
}
