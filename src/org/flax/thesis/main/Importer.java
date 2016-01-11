package org.flax.thesis.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.flax.thesis.objects.FDoc;
import org.flax.thesis.util.Database;

public class Importer {
	
	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.main.Importer.class.getName());
	public int ignoredCounter = 0;
	public int exportCounter = 0;
	Database db = new Database();
	
	public void run(){
		this.findAbstracts(Consts.CACHEPATH);
		try {
			db.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
	}
		
	public void findAbstracts(String path){
		 File dir = new File(path);
		 File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File file : directoryListing) {
		      if(file.isDirectory()){
		    	  findAbstracts(file.getPath());
		      }else{
		    	  if(file.getName().startsWith(Consts.ABSTRACTPREFIX)){
			    	  this.importIntoDB(file);
		    	  }
		      }
		    }
		  }
	}
	
	public void importIntoDB(File file){
		
		//formate the file
		FDoc fdoc = Formater.xmlToDoc(file);

		try {

			db.insertAbstract(fdoc);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
	}
}
