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

import com.sun.jmx.snmp.daemon.CommunicationException;

import at.fhooe.mcm.lri.smdm.server.db.DBConnection;
import at.fhooe.mcm.lri.smdm.server.db.DBConnectionManager;
import at.fhooe.mcm.lri.smdm.server.db.DBUser;

@SuppressWarnings("serial")
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(RegisterServlet.class
			.getName());
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
		System.out.println("RegisterServlet");

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

		System.out.println(req.toString());
		Map<String, String[]> map = req.getParameterMap();
		System.out.println(map);

		String deviceID = req.getParameter("device_id");
		String registrationID = req.getParameter("registration_id");
		String email = req.getParameter("email");

		System.out.println("ID: " + deviceID);
		System.out.println("Registration ID: " + registrationID);
		System.out.println("Email: " + email);

		if (deviceID == null) {
			resp.setStatus(400);
			resp.getWriter().println(ERROR_STATUS + "(Must specify device id)");
			System.err.println("No devrice id in RegisterServlet");
			return;
		}

		// TODO Store the device id permanently
		System.out.println("Trying to store device: " + deviceID);
		if (dbConnection != null) {

			if (dbConnection.isRegistered(deviceID)) {
				// device already exists
				System.out.println("Device already exists");
				resp.getWriter()
				.println(ERROR_ALREADY_REGISTERED);
			} else {
				System.out.println("not registered yet");

				// create new entry for the db
				Timestamp timestamp = new Timestamp(
						(new java.util.Date()).getTime());
				DBUser user = new DBUser(deviceID, registrationID, email,
						timestamp, timestamp);
				boolean res = dbConnection.registerDevice(user);

				if (res) {
					// everything is awesome!
					resp.getWriter().println(OK_STATUS);
					System.out.println("Device with id " + deviceID + " successfully registered");
				} else {
					// what the duck happened?
					resp.getWriter()
							.println(
									ERROR_STATUS
											+ " registering the device in the DB failed");
				}
			}

		}

		dbConnection.close();
	}
}