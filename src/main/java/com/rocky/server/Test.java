package com.rocky.server;

public class Test {
	public static void main(String[] args) {
		String uri = "/index.html?god=rocky";
		int index = 0;
		if((index = uri.indexOf("?")) > 0){
			String queryString = uri.substring(index+1);
			for(String kv : queryString.split("&")){
				System.out.println(""+(kv.split("=")[0] +"|"+ kv.split("=")[1]));
			}
			
			System.out.println(System.getProperty("user.dir"));
		}
	}
}
