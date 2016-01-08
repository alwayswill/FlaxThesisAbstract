package org.flax.thesis.main;

import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class AbstractsDownloadThread implements Callable<String> {

	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.main.AbstractsDownloadThread.class.getName());
	public String identifier = "";
	public String path = "";
	
	public AbstractsDownloadThread(String identifier, String Path){
		this.identifier = identifier;
		this.path = Path;
	}
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		this.downloadAbstractsByIdentifierId(this.identifier, this.path);
		return Thread.currentThread().getName() + " download completed.";
	}
	
	public void downloadAbstractsByIdentifierId(String identifier, String Path){
		
		File abstractFile =  new File (Path+"/"+Consts.ABSTRACTPREFIX+StringEscapeUtils.escapeJava(identifier)+".xml");
		if(!abstractFile.exists()){

			try {
				String abstractFileLink = Consts.ABSTRACTLINK+URLEncoder.encode(identifier, "UTF-8");
				System.out.println("Download Abstract: "+ abstractFileLink);

				FileUtils.copyURLToFile(new URL(abstractFileLink), abstractFile, Consts.TIMEOUT, Consts.TIMEOUT);
			} catch (Exception e) {
				abstractFile.delete();
				e.printStackTrace();
			}
		}
	}

}
