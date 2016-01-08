package org.flax.thesis.main;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.flax.thesis.objects.FDoc;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Formater {
	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.main.Formater.class.getName());
	
	public static FDoc xmlToDoc(File file){
		logger.info(file.getPath());
		FDoc fdoc = new FDoc();
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			XPath xPath =  XPathFactory.newInstance().newXPath();

			fdoc.responseDate = (String) xPath.compile("/OAI-PMH/responseDate").evaluate(doc, XPathConstants.STRING);
			
			fdoc.request = (String) xPath.compile("/OAI-PMH/request").evaluate(doc, XPathConstants.STRING);
			fdoc.identifier = (String) xPath.compile("/OAI-PMH/GetRecord/record/header/identifier").evaluate(doc, XPathConstants.STRING);
			fdoc.datestamp = (String) xPath.compile("/OAI-PMH/GetRecord/record/header/datestamp").evaluate(doc, XPathConstants.STRING);
			
//			setSpec
			fdoc.title = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dc:title']").evaluate(doc, XPathConstants.STRING);
			fdoc.creator = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dc:creator']").evaluate(doc, XPathConstants.STRING);
			fdoc.institution = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='uketdterms:institution']").evaluate(doc, XPathConstants.STRING);
			fdoc.publisher = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dc:publisher']").evaluate(doc, XPathConstants.STRING);
			fdoc.issued = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dcterms:issued']").evaluate(doc, XPathConstants.STRING);
			fdoc.content = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dcterms:abstract']").evaluate(doc, XPathConstants.STRING);
			
			fdoc.dctype = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dc:type']").evaluate(doc, XPathConstants.STRING);
			fdoc.qualificationName = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='uketdterms:qualificationname']").evaluate(doc, XPathConstants.STRING);
			fdoc.qualificationLevel = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='uketdterms:qualificationlevel']").evaluate(doc, XPathConstants.STRING);
			fdoc.accessRights = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dcterms:accessRights']").evaluate(doc, XPathConstants.STRING);
			fdoc.dcIdentifier = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dc:identifier']").evaluate(doc, XPathConstants.STRING);
			fdoc.dcSource = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dc:source']").evaluate(doc, XPathConstants.STRING);
			fdoc.dcSubject = (String) xPath.compile("/OAI-PMH/GetRecord/record/metadata/*[name()='uketd_dc:uketddc']/*[name()='dc:subject']").evaluate(doc, XPathConstants.STRING);
			
			
			NodeList nodeList = (NodeList) xPath.compile("/OAI-PMH/GetRecord/record/header/setSpec/text()").evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
			    fdoc.setSpecs.add(nodeList.item(i).getNodeValue());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		
		return fdoc;
	}
}
