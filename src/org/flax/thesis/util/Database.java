package org.flax.thesis.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.DeleteDbFiles;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.flax.thesis.main.Consts;
import org.flax.thesis.main.Exporter;
import org.flax.thesis.objects.FDoc;

public class Database {
	final static org.apache.log4j.Logger logger = Logger.getLogger(org.flax.thesis.util.Database.class.getName());
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:" + Consts.DBPATH + "/AbstractDB";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";
	public Connection connection;

	public static void DeleteDBFiles(){
		 DeleteDbFiles.execute(Consts.DBPATH, "AbstractDB", true);
	}
	
	public void DeleteTables(){
		this.connection = getDBConnection();
		Statement stmt = null;
		String sql = "DROP TABLE Abstract";
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.execute(sql);
			stmt.close();
			connection.commit();
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public Database(){
		connection = getDBConnection();
	}
	
	public void CreateTable() throws SQLException {
		this.connection = getDBConnection();
		Statement stmt = null;
		String sql = "CREATE TABLE Abstract(id bigint auto_increment primary key, responseDate varchar(30), request text, identifier varchar(255), datestamp varchar(30), timestamp timestamp,  title text, creator varchar(100), institution varchar(255), publisher varchar(255), issued varchar(30), content text, dctype varchar(255), qualificationName varchar(255), qualificationLevel varchar(255), accessRights varchar(255), dcIdentifier text, dcSource text, dcSubject double, setSpecs text, originalSubject text, discipline varchar(255), DDCDescprition varchar(255))";
		try {
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.execute(sql);
			stmt.close();
			connection.commit();
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			connection.close();
		}
	}
	
	public void GetAbstracts(String type){
		PreparedStatement selectPreparedStatement = null;
		String sql = "SELECT * from ABSTRACT";
		try {
			connection.setAutoCommit(false);
		       selectPreparedStatement = connection.prepareStatement(sql);
	            ResultSet rs = selectPreparedStatement.executeQuery();
	            while (rs.next()) {
	            	FDoc fdoc = new FDoc(rs);
	            	Exporter.Export(fdoc, type);
	            }
	            selectPreparedStatement.close();
	            connection.commit();
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	
	public void getCategories() throws SQLException {
		PreparedStatement selectPreparedStatement = null;
		String sql = "SELECT COUNT(id) AS theCount, ORIGINALSUBJECT from ABSTRACT GROUP BY ORIGINALSUBJECT ORDER BY theCount DESC";
		try {
			connection.setAutoCommit(false);
		       selectPreparedStatement = connection.prepareStatement(sql);
	            ResultSet rs = selectPreparedStatement.executeQuery();
	            while (rs.next()) {
	            	String message = "Category: "+rs.getString("ORIGINALSUBJECT") +" Count: "+rs.getInt("theCount")+ "\r\n";
	    			
	    			File toSaveFile = new File(Consts.APPROOTPATH+"/statics/category.repo");
	    			try {
	    				FileUtils.writeStringToFile(toSaveFile, message, true);
	    			} catch (IOException e) {
	    				logger.error(e);
	    			}
	            }
	            selectPreparedStatement.close();
	            connection.commit();
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			connection.close();
		}
	}
	
	
	public void getNumberOfAbsByUnis() throws SQLException {
		PreparedStatement selectPreparedStatement = null;
		String sql = "SELECT COUNT(id) AS theCount, INSTITUTION from ABSTRACT GROUP BY INSTITUTION ORDER BY theCount DESC";
		try {
			connection.setAutoCommit(false);
		       selectPreparedStatement = connection.prepareStatement(sql);
	            ResultSet rs = selectPreparedStatement.executeQuery();
	            while (rs.next()) {
	            	String message = "Uni: "+rs.getString("INSTITUTION") +" | Count: "+rs.getInt("theCount")+ "\r\n";
	    			
	    			File toSaveFile = new File(Consts.APPROOTPATH+"/statics/NumberOfAbsByUnis.repo");
	    			try {
	    				FileUtils.writeStringToFile(toSaveFile, message, true);
	    			} catch (IOException e) {
	    				logger.error(e);
	    			}
	            }
	            selectPreparedStatement.close();
	            connection.commit();
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			connection.close();
		}
	}
	
	
	public void insertAbstract(FDoc fdoc)  throws SQLException{

           this.connection.setAutoCommit(false);
           
	        PreparedStatement insertPreparedStatement = null;
	        String InsertQuery = "INSERT INTO ABSTRACT" + "(responseDate, request, identifier, datestamp, timestamp, title, creator, institution, publisher, issued, content, dctype, qualificationName ,qualificationLevel, accessRights, dcIdentifier, dcSource, dcSubject, setSpecs, originalSubject, discipline, DDCDescprition) values" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	        try {
	            insertPreparedStatement = connection.prepareStatement(InsertQuery);
	            insertPreparedStatement.setString(1, fdoc.responseDate);
	            insertPreparedStatement.setString(2, fdoc.request);
	            insertPreparedStatement.setString(3, fdoc.identifier);
	            insertPreparedStatement.setString(4, fdoc.datestamp);
	            insertPreparedStatement.setTimestamp(5, fdoc.timestamp);
	            insertPreparedStatement.setString(6, fdoc.title);
	            insertPreparedStatement.setString(7, fdoc.creator);
	            insertPreparedStatement.setString(8, fdoc.institution);
	            insertPreparedStatement.setString(9, fdoc.publisher);
	            insertPreparedStatement.setString(10, fdoc.issued);
	            insertPreparedStatement.setString(11, fdoc.content);
	            insertPreparedStatement.setString(12, fdoc.dctype);
	            insertPreparedStatement.setString(13, fdoc.qualificationName);
	            insertPreparedStatement.setString(14, fdoc.qualificationLevel);
	            insertPreparedStatement.setString(15, fdoc.accessRights);
	            insertPreparedStatement.setString(16, fdoc.dcIdentifier);
	            insertPreparedStatement.setString(17, fdoc.dcSource);
	            insertPreparedStatement.setDouble(18, fdoc.dcSubject);
	            
	            insertPreparedStatement.setString(19,  StringUtils.join(fdoc.setSpecs, ","));
	            insertPreparedStatement.setString(20,  fdoc.originalSubject);
	            insertPreparedStatement.setString(21,  fdoc.discipline);
	            insertPreparedStatement.setString(22,  fdoc.DDCDescprition);
	            
	            insertPreparedStatement.executeUpdate();
	            insertPreparedStatement.close();
	           
	            connection.commit();
	        } catch (SQLException e) {
	            System.out.println("Exception Message " + e.getLocalizedMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	public static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
	
	public void close(){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
