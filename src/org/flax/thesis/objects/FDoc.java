package org.flax.thesis.objects;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import org.flax.thesis.main.Consts;

import org.apache.log4j.Logger;

public class FDoc {

	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.objects.FDoc.class.getName());

	public String responseDate;
	public String request;
	public String identifier;
	public String datestamp;
	public ArrayList<String> setSpecs = new ArrayList<String>();
	public String title;
	public String creator;
	public String institution;
	public String publisher;
	public String issued;
	public String content;
	public String dctype;
	public String qualificationName;
	public String qualificationLevel;
	public String accessRights;
	public String dcIdentifier;
	public String dcSource;
	public String dcSubject;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FDoc [responseDate=" + responseDate + ", request=" + request + ", identifier=" + identifier
				+ ", datestamp=" + datestamp + ", setSpecs=" + setSpecs + ", title=" + title + ", creator=" + creator
				+ ", institution=" + institution + ", publisher=" + publisher + ", issued=" + issued + ", content="
				+ content + ", qualificationName=" + qualificationName + ", qualificationLevel=" + qualificationLevel
				+ ", accessRights=" + accessRights + ", dcIdentifier=" + dcIdentifier + ", dcSource=" + dcSource
				+ ", dcSubject=" + dcSubject + "]";
	}

	public String toHTML(String template) {
		String templateText = "";
		File templateFile = new File(Consts.TEMPLATESPATH + "/" + template + ".tpl");
		try {
			templateText = new String(Files.readAllBytes(templateFile.toPath()), StandardCharsets.UTF_8);

			Class<?> c = this.getClass();
			Field[] fields = c.getDeclaredFields();
			
//			TODO:setSpec
			for (Field field : fields) {
				templateText = templateText.replaceAll("\\{\\$" + field.getName() + "\\}", field.get(this).toString());
			}
		} catch (Exception e) {
			logger.error(e);
		}
		
		return templateText;
	}

}
