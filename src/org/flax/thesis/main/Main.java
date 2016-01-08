package org.flax.thesis.main;

import java.net.Authenticator;

public class Main {
	static final String appRootPath = System.getProperty("user.dir");
	public static void main(String[] args) throws Exception {
		
		Authenticator.setDefault(new ProxyAuthenticator("xxx", "xxx"));
		System.setProperty("http.proxyHost", "proxy.waikato.ac.nz");
		System.setProperty("http.proxyPort", "8080");
		
		
//		Harvester harvester = new Harvester();
//		harvester.run();
		Exporter exporter = new Exporter();
		exporter.run();
	}
}
