package org.flax.thesis.test;

import static org.junit.Assert.*;

import org.flax.thesis.main.Consts;
import org.flax.thesis.main.Filter;
import org.junit.Test;

public class TestFilter {

	@Test
	public void test() {
		Filter.getDB(Consts.FILE_NAME_DEWEYDECIMALTODISCIPLINE);
	}

}
