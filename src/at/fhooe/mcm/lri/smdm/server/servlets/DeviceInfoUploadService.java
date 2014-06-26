package at.fhooe.mcm.lri.smdm.server.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.fhooe.mcm.lri.smdm.server.db.DBConnection;
import at.fhooe.mcm.lri.smdm.server.db.DBConnectionManager;
import at.fhooe.mcm.lri.smdm.server.db.DBUser;

import com.sun.jmx.snmp.daemon.CommunicationException;

@SuppressWarnings("serial")
@WebServlet("/upload")
public class DeviceInfoUploadService extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(DeviceInfoUploadService.class.getName());
	private static final String OK_STATUS = "OK";
	private static final String ERROR_STATUS = "ERROR";
	private static final String ERROR_ALREADY_REGISTERED = "ERROR_ALREADY_REGISTERED";

	/**
	 * @deprecated will be removed in next rel.
	 */
	@Deprecated
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("UploadServlet");

		resp.setContentType("text/plain");

//		ServletContext ctx = getServletContext();
//		DBConnection dbConnection = null;
//		Object dbConn = ctx.getAttribute("DBConnection");
//		if (dbConn != null && dbConn instanceof DBConnection) {
//			dbConnection = (DBConnection) dbConn;
//		} else {
//			// initialize DB Connection with "global" parameters from web.xml
//			String dbURL = ctx.getInitParameter("dbURL");
//			String dbUser = ctx.getInitParameter("dbUser");
//			String dbPwd = ctx.getInitParameter("dbPassword");
//
//			try {
//				dbConnection = DBConnectionManager.getConnection(dbURL, dbUser,
//						dbPwd);
//				ctx.setAttribute("DBConnection", dbConnection);
//				System.out.println("DB Connection initialized successfully.");
//			} catch (CommunicationException ce) {
//				System.err.println("Cannot connect to database");
//				ce.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}

		DBConnection dbConnection = DBHelper.getConnection();
		
		String blob = req.getHeader("Device-Info");
		if (blob == null) {
			resp.setStatus(400);
			resp.getWriter().println(ERROR_STATUS + "(no blob was given)");
			System.err.println("No blob received in request");
			return;
		}

		System.out.println("Received blob: " + blob);

		boolean res = dbConnection.storeBlob(blob);

		if (res) {
			// everything is awesome!
			resp.getWriter().println(OK_STATUS);
		} else {
			// what the duck happened?
			resp.getWriter().println(
					ERROR_STATUS + "Uploading the device info failed");
		}
		dbConnection.close();
	}

}