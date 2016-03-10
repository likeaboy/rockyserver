package com.rocky.server.util;

public class DebugUtil {
	
	public static void printLog(String content){
		System.out.print("[" + Thread.currentThread().getName() + "]");
		System.out.print(content);
		System.out.println();
	}
}
