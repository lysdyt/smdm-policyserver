package at.fhooe.mcm.lri.smdm.server.servlets;

import java.sql.SQLException;

import at.fhooe.mcm.lri.smdm.server.db.DBConnection;
import at.fhooe.mcm.lri.smdm.server.db.DBConnectionManager;

import com.sun.jmx.snmp.daemon.CommunicationException;

public class DBHelper {
	public static DBConnection getConnection() {
		// initialize DB Connection with "global" parameters from web.xml
		String dbURL = "jdbc:mysql://localhost/userdb";
		String dbUser = "dbUsername";
		String dbPwd = "";

		DBConnection dbConnection = null;
		try {
			dbConnection = DBConnectionManager.getConnection(dbURL, dbUser,
					dbPwd);
			System.out.println("DB Connection initialized successfully.");
		} catch (CommunicationException ce) {
			System.err.println("Cannot connect to database");
			ce.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dbConnection;
	}
}
