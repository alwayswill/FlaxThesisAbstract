package org.flax.thesis.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Harvester {
	static final String appRootPath = System.getProperty("user.dir");
	static final String cachePath = appRootPath + "/cache";
	static final String identifierPath = appRootPath + "/cache" + "/identifiers";
	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.main.Harvester.class.getName());
	static final String UnilistFile = "UniList.xml";
	static final String indentifierListFileName = "IndentifierList.xml";
	static final String abstractPreFix = "Abstract_";
	static final String UniFolderPreFix = "Uni_";

	public void run() {
		this.checkCacheDir();
		this.downloadUniList();
		this.downloadIdentifiers();
		this.downloadAbstracts();

	}

	public void checkCacheDir() {
		File cachefile = new File(cachePath);
		if (!cachefile.exists()) {
			cachefile.mkdirs();
		}

		File identifierPathFile = new File(identifierPath);
		if (!identifierPathFile.exists()) {
			identifierPathFile.mkdirs();
		}

	}

	public void downloadUniList() {
		File UniListFile = new File(cachePath + "/" + UnilistFile);

		if (!UniListFile.exists()) {

			try {
				FileUtils.copyURLToFile(new URL(Consts.UNILISTLINK), UniListFile, Consts.TIMEOUT, Consts.TIMEOUT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void downloadIdentifiers() {
		ArrayList Unilist = this.getUniList();
		Iterator<String> iterator = Unilist.iterator();
		while (iterator.hasNext()) {
			String uniName = iterator.next();
			this.downloadIdentifiersByUni(uniName);
		}
	}

	public void downloadAbstracts() {

		//
		// List<Future> tasks = new ArrayList<Future>();
		//
		// ExecutorService es = Executors.newFixedThreadPool(10);
		//
		//
		//
		// es.shutdown();
		//
		// // use the results for something
		// for (Future fut : tasks)
		// logger.info(((java.util.concurrent.Future<String>) fut).get());
		// }

		List<Future> tasks = new ArrayList<Future>();
		ExecutorService es = Executors.newFixedThreadPool(10);

		File dir = new File(identifierPath);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File uniFolder : directoryListing) {
				if (uniFolder.isDirectory()) {
					ArrayList identifiers = getIdentifiers(uniFolder.getPath() + "/" + indentifierListFileName);
					for (int i = 0; i < identifiers.size(); i++) {
						String identifier = identifiers.get(i).toString();
						logger.info("Download identifier (" + uniFolder.getPath() + "): " + identifier);
						// this.downloadAbstractsByIdentifierId(identifier,
						// uniFolder.getPath());
						Callable<String> abstractsDownloadThread = new AbstractsDownloadThread(identifier,
								uniFolder.getPath());
						Future future = (Future) es.submit(abstractsDownloadThread);
						tasks.add(future);
					}

				}
			}
		}
		es.shutdown();

		// use the results for something
		try{
			for (Future fut : tasks){
				logger.info(((java.util.concurrent.Future<String>) fut).get());			
			}			
		} catch(Exception e){
			logger.error(e);
		}
	}

	public void downloadIdentifiersByUni(String uniName) {
		String indentifierListLink = "";

		File identifierUniPath = new File(
				identifierPath + "/" + UniFolderPreFix + StringEscapeUtils.escapeJava(uniName));
		if (!identifierUniPath.exists()) {
			identifierUniPath.mkdirs();
		}

		File identifierFile = new File(identifierPath + "/" + UniFolderPreFix + StringEscapeUtils.escapeJava(uniName)
				+ "/" + indentifierListFileName);
		if (!identifierFile.exists()) {

			try {
				indentifierListLink = Consts.IDENTIFIERSLIST + URLEncoder.encode(uniName, "UTF-8");
				logger.info(indentifierListLink);

				FileUtils.copyURLToFile(new URL(indentifierListLink), identifierFile, Consts.TIMEOUT, Consts.TIMEOUT);
			} catch (Exception e) {
				identifierFile.delete();
				e.printStackTrace();
			}
		}

	}

	public void downloadIdentifiersList() {
		File UniversityListFile = new File(cachePath + "/" + UnilistFile);

		if (!UniversityListFile.exists()) {

			try {
				FileUtils.copyURLToFile(new URL(Consts.UNILISTLINK), UniversityListFile, Consts.TIMEOUT,
						Consts.TIMEOUT);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ArrayList getUniList() {
		ArrayList uniList = new ArrayList();

		File fXmlFile = new File(cachePath + "/" + UnilistFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			String expression = "/OAI-PMH/ListSets/set/setSpec";
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				uniList.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uniList;
	}

	public ArrayList getIdentifiers(String identifierListFile) {
		ArrayList identifierList = new ArrayList();

		File fXmlFile = new File(identifierListFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			String expression = "/OAI-PMH/ListIdentifiers/header/identifier";
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				identifierList.add(nodeList.item(i).getFirstChild().getNodeValue().toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return identifierList;
	}
}
