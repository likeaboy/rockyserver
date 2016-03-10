package com.rocky.server;
import java.net.Socket;
import java.util.Stack;

import com.rocky.server.util.DebugUtil;


public class ServletProcessorStatck {
	private Stack<ServletProcessor> processors = new Stack<ServletProcessor>();
	private int max = 10;
	private int currentCount = 0;
	
	public ServletProcessorStatck(){
		init();
	}
	
	public ServletProcessorStatck(int max){
		this.max = max;
		init();
	}
	
	private void init(){
		for(int i=0;i<max;i++){
			processors.push(new ServletProcessor(null));
			currentCount++;
		}
	}	
	
	
	public synchronized ServletProcessor getProcessor(Socket socket){
		if(currentCount > 0){
			currentCount --;
			ServletProcessor pro = processors.pop();
			DebugUtil.printLog("[invoke getProcessor] processor socket before reset=" + pro.getSocket());
			pro.setSocket(socket);
			DebugUtil.printLog("[invoke getProcessor] processor socket after reset=" + pro.getSocket());
			DebugUtil.printLog("[invoke getProcessor] processor currentCount="+currentCount);
			return pro;
		}
		else return null;
	}
	
	public synchronized void recycle(ServletProcessor processor){
		processors.push(processor);
		currentCount ++;
		DebugUtil.printLog("processor:"+processor + " has been push");
		DebugUtil.printLog("[invoke recycle] processor currentCount="+currentCount);
	}
}
