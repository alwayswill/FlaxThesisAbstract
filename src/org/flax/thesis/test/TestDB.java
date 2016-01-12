package org.flax.thesis.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.flax.thesis.util.Database;
import org.junit.Test;

public class TestDB {

	@Test
	public void test() throws SQLException {
		Database db = new Database();
//		db.DeleteTable();
//		db.CreateTable();
//		db.getCategories();
		db.getNumberOfAbsByUnis();
		db.close();
	}
}
