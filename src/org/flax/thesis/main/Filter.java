package org.flax.thesis.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class Filter {
	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.main.Filter.class.getName());

	public static String getDDCDescription(String originalDDC) {
		HashMap categories = Filter.getDB(Consts.FILE_NAME_DDC_CATEGORY);
		return (String) categories.get(originalDDC);
	}

	public static String getDiscipline(String originalDDC) {
		HashMap categories = Filter.getDB(Consts.FILE_NAME_DEWEYDECIMALTODISCIPLINE);
		return (String) categories.get(originalDDC);
	}

	public static HashMap getDB(String dbname) {
		HashMap categories = new HashMap();
		Path db = Paths.get(Consts.DBPATH, dbname);

		try {
			List<String> lines = Files.readAllLines(db, Charset.forName("UTF-8"));
			for (String line : lines) {
				String[] categoryData = line.split(",");
				String key  = categoryData[0];
				String value  = categoryData[1];
				categories.put(key, value);
			}
		} catch (IOException e) {
			logger.error(e);
		}
		return categories;
	}
}
