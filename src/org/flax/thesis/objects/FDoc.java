package org.flax.thesis.objects;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

import org.flax.thesis.main.Consts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class FDoc {

	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.objects.FDoc.class.getName());

	public String responseDate = "";
	public String request = "";
	public String identifier = "";
	public String datestamp = "";
	public Timestamp timestamp;
	public ArrayList<String> setSpecs = new ArrayList<String>();
	public String title = "";
	public String creator = "";
	public String institution = "";
	public String publisher = "";
	public String issued = "";
	public String content = "";
	public String dctype = "";
	public String qualificationName = "";
	public String qualificationLevel = "";
	public String accessRights = "";
	public String dcIdentifier = "";
	public String dcSource = "";
	public double dcSubject = 0;
	public String originalSubject = "";
	public static String EXPORTTYPEHTML = "html";
	public static String EXPORTTYPEJSON = "json";
	public String discipline = "";
	public String DDCDescprition = "";

	public FDoc() {

	}

	public FDoc(ResultSet resultset) {
		try {
			this.responseDate = resultset.getString("responseDate");
			this.request = resultset.getString("request");
			this.identifier = resultset.getString("identifier");
			this.datestamp = resultset.getString("datestamp");
			this.timestamp = resultset.getTimestamp("timestamp");
			this.setSpecs = new ArrayList<String>(Arrays.asList(resultset.getString("setSpecs").split(",")));
			this.title = resultset.getString("title");
			this.creator = resultset.getString("creator");
			this.institution = resultset.getString("institution");
			this.publisher = resultset.getString("publisher");
			this.issued = resultset.getString("issued");
			this.content = resultset.getString("content");
			this.dctype = resultset.getString("dctype");
			this.qualificationName = resultset.getString("qualificationName");
			this.qualificationLevel = resultset.getString("qualificationLevel");
			this.accessRights = resultset.getString("accessRights");
			this.dcIdentifier = resultset.getString("dcIdentifier");
			this.dcSource = resultset.getString("dcSource");
			this.dcSubject = resultset.getDouble("dcSubject");
			this.originalSubject = resultset.getString("originalSubject");
			this.DDCDescprition = resultset.getString("DDCDescprition");
			this.discipline = resultset.getString("discipline");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FDoc [responseDate=" + responseDate + ", request=" + request + ", identifier=" + identifier
				+ ", datestamp=" + datestamp + ", timestamp=" + timestamp + ", setSpecs=" + setSpecs + ", title="
				+ title + ", creator=" + creator + ", institution=" + institution + ", publisher=" + publisher
				+ ", issued=" + issued + ", content=" + content + ", dctype=" + dctype + ", qualificationName="
				+ qualificationName + ", qualificationLevel=" + qualificationLevel + ", accessRights=" + accessRights
				+ ", dcIdentifier=" + dcIdentifier + ", dcSource=" + dcSource + ", dcSubject=" + dcSubject
				+ ", originalSubject=" + originalSubject + ", discipline=" + discipline + ", DDCDescprition="
				+ DDCDescprition + "]";
	}

	public String toHTML(String template) {
		String templateText = "";
		File templateFile = new File(Consts.TEMPLATESPATH + "/" + template + ".tpl");
		try {
			templateText = new String(Files.readAllBytes(templateFile.toPath()), StandardCharsets.UTF_8);

			Class<?> c = this.getClass();
			Field[] fields = c.getDeclaredFields();

			// TODO:setSpec
			for (Field field : fields) {
				String replaceStr = StringEscapeUtils.escapeHtml(field.get(this).toString());
				templateText = templateText.replaceAll("\\{\\$" + field.getName() + "\\}",
						Matcher.quoteReplacement(replaceStr));
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return templateText;
	}

	public String getDcSubject() {
		if (this.originalSubject.isEmpty()) {
			return "";
		}

		String dcSubjectValue = this.originalSubject;
		if (dcSubjectValue.isEmpty() || dcSubjectValue.length() <= 2 || !Character.isDigit(dcSubjectValue.charAt(0))
				|| !Character.isDigit(dcSubjectValue.charAt(1))) {
			return "";
		}

		return dcSubjectValue.substring(0, 2);

	}

	public String toJson() {
		String jsonString = "";
		ObjectMapper mapper = new ObjectMapper();
		// Object to JSON in String
		try {
			jsonString = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		return jsonString;
	}

}
