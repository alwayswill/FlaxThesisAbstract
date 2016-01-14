package org.flax.thesis.test;

import static org.junit.Assert.*;

import java.io.File;

import org.flax.thesis.main.Exporter;
import org.junit.Test;

public class TestExport {

	@Test
	public void test() {
		Exporter exporter = new Exporter();
		
		exporter.ExportToHTML(new File("/Users/will/Sites/scholarship/FlaxThesisAbstract/cache/identifiers/Uni_Full text ETDs:University_of_Liverpool/Abstract_oai:ethos.bl.uk:564242.xml"));
	}

}
