package at.fhooe.mcm.lri.smdm.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the DB connection. This singleton returns an abstract database
 * connection based on the used web server (GAE/Apache). If the software runs in
 * a standard web server, a MySQL database is used. When running in GAE we need
 * to use the datastore provided from Google.
 * 
 * @author Lorenz
 * 
 */
public class DBConnectionManager {

	public static DBConnection getConnection(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException {
		// TODO check if running in GAE or apache/MySQL
		if(true) {
			return new DBMySQLConnection(dbURL, user, pwd);
		} else {
			// TODO open GAE datastore connection
		}
		
		return null;
	}
}
