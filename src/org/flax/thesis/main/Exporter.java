package org.flax.thesis.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.flax.thesis.objects.FDoc;

public class Exporter {
	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.main.Exporter.class.getName());
	public int ignoredCounter = 0;
	public int exportCounter = 0;
	public void run(){
		this.findAbstracts(Consts.CACHEPATH);
		logger.info("ignored files/ total files: "+ignoredCounter+"/"+(exportCounter+ignoredCounter) +". Export "+exportCounter+"files");
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
			    	  this.ExportToHTML(file);
		    	  }
		      }
		    }
		  }
	}
	
	public void ExportToHTML(File file){
		File exportsPath = new File(Consts.EXPORTSPATH);
		if(!exportsPath.exists()){
			exportsPath.mkdirs();
		}

		
		//formate the file
		FDoc fdoc = Formater.xmlToDoc(file);

		String savedPath = getDDCPath(Consts.EXPORTSPATH+"/"+"HTML", fdoc);
		
		if(!savedPath.isEmpty()){
			
			File exportsFilePath = new File(savedPath);	
			if(!exportsFilePath.exists()){
				exportsFilePath.getParentFile().mkdirs();
			}
			
			String fileNameWithOutExt = FilenameUtils.removeExtension(file.getName());
			
			
			String htmlString = fdoc.toHTML("HTML");
			File toSaveFile = new File(savedPath+"/"+fileNameWithOutExt+".html");
			try {
				FileUtils.writeStringToFile(toSaveFile, htmlString);
				exportCounter++;
			} catch (IOException e) {
				logger.error(e);
			}
		}
		
	}
	
	public String getDDCPath(String SavePath, FDoc fdoc){
		String targetPath = "";
		String DDC = Double.toString(fdoc.dcSubject);
		
		if(fdoc.content.isEmpty() || DDC.isEmpty() || DDC.length() <= 2 || !Character.isDigit(DDC.charAt(0)) || !Character.isDigit(DDC.charAt(1)) ){
			logger.warn("DDC: "+ DDC + "is exceptional. Ignored.");
			ignoredCounter++;
			return "";
		}
		
		targetPath = SavePath+"/"+DDC.charAt(0)+"/"+DDC.charAt(1);
		logger.info(targetPath);
		return targetPath;
		
	}
	
}
