package com.rocky.server;

import com.rocky.server.util.DebugUtil;

/**
 * bootstrap
 * @author rocky
 *
 */
public class Bootstrap {
	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
	    connector.start();
	    DebugUtil.printLog("Server is starting...");
	}
}
