package com.rocky.server;

import java.util.ResourceBundle;

public class Constants {
	protected static final String METHOD_DELETE = "DELETE";
	protected static final String METHOD_HEAD = "HEAD";
	protected static final String METHOD_GET = "GET";
	protected static final String METHOD_OPTIONS = "OPTIONS";
	protected static final String METHOD_POST = "POST";
	protected static final String METHOD_PUT = "PUT";
	protected static final String METHOD_TRACE = "TRACE";

	protected static final String HEADER_IFMODSINCE = "If-Modified-Since";
	protected static final String HEADER_LASTMOD = "Last-Modified";
    
	protected static final String LSTRING_FILE =
	"javax.servlet.http.LocalStrings";
	protected static ResourceBundle lStrings =
	ResourceBundle.getBundle(LSTRING_FILE);
    
	///Users/rocky/Documents/workspace-pgc/server
	protected static final String CONTEXT_PATH = System.getProperty("user.dir");
	protected static final String WEBROOT = "/src/main/webapp";
}
