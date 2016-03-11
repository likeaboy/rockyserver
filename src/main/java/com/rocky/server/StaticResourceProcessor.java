package com.rocky.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticResourceProcessor {
	private static String getResourceName(String absResPath){
		return Constants.CONTEXT_PATH + Constants.WEBROOT + absResPath;
	}
	
	public static HttpServletResponse doFindStaticResource(HttpServletRequest req){
		GenericServletRequest request = (GenericServletRequest)req;
		GenericServletResponse response = new GenericServletResponse(request.getSocket());
		if(Constants.METHOD_GET.equals(request.getMethod())){
			File file = new File(getResourceName(req.getContextPath()));
			BufferedReader br = null;
			BufferedWriter bw = null;
			
			try {
				if(file.exists()){
					br = new BufferedReader(new FileReader(file));
					bw = new BufferedWriter(new OutputStreamWriter(response.getSocket().getOutputStream(),"UTF-8"));
					String line = br.readLine();
					while(line!=null && !"".equals(line)){
						bw.write(line);
						line = br.readLine(); 
					}
					bw.flush();
					response.getSocket().getOutputStream().flush();
				}else{
					byte[] errorBs = "Server ERROR:404 NOT FOUND".getBytes();
					response.getSocket().getOutputStream().write(errorBs);
					response.getSocket().getOutputStream().flush();
					
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
		}else if(Constants.METHOD_POST.equals(request.getMethod())){
			
		}

		return response;
	}
	
}
