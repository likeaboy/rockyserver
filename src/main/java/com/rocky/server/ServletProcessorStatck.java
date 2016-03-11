package com.rocky.server;
import java.net.Socket;
import java.util.Stack;

import com.rocky.server.util.DebugUtil;


public class ServletProcessorStatck {
	private Stack<HttpProcessor> processors = new Stack<HttpProcessor>();
	private int curProcessors = 0;
	private int minProcessors = 5;
	private int maxProcessors = 20;
	private HttpConnector connector;
	
	public ServletProcessorStatck(HttpConnector connector){
		this.connector = connector;
		init();
	}
	
	public ServletProcessorStatck(int minProcessors,int maxProcessors){
		this.minProcessors = minProcessors;
		this.maxProcessors = maxProcessors;
		init();
	}
	
	private void init(){
		/*for(int i=0;i<max;i++){
			processors.push(new HttpProcessor(null));
			currentCount++;
		}*/
		
		while (curProcessors < minProcessors) {   
		     if ((maxProcessors > 0) && (curProcessors >= maxProcessors))   
		       break;   
		     HttpProcessor processor = newProcessor();   
		     recycle(processor);   
		   } 
	}	
	
	private HttpProcessor newProcessor(){
		return new HttpProcessor(connector);
	}
	
	
	public synchronized HttpProcessor getProcessor(Socket socket){
		if(curProcessors > 0){
			curProcessors --;
			HttpProcessor pro = processors.pop();
			DebugUtil.printLog("[invoke getProcessor] processor socket before reset=" + pro.getSocket());
			pro.setSocket(socket);
			DebugUtil.printLog("[invoke getProcessor] processor socket after reset=" + pro.getSocket());
			DebugUtil.printLog("[invoke getProcessor] processor currentCount="+curProcessors);
			return pro;
		}
		else return null;
	}
	
	public synchronized void recycle(HttpProcessor processor){
		processors.push(processor);
		curProcessors ++;
		DebugUtil.printLog("processor:"+processor + " has been push");
		DebugUtil.printLog("[invoke recycle] processor currentCount="+curProcessors);
	}
}
