package at.fhooe.mcm.lri.smdm.server.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.mysql.jdbc.PreparedStatement;
import com.sun.jmx.snmp.daemon.CommunicationException;

import at.fhooe.mcm.lri.mdmpoc.bundle.BuildBundle;
import at.fhooe.mcm.lri.mdmpoc.bundle.BuildSMDMBundle;
import at.fhooe.mcm.lri.smdm.server.db.DBConnection;
import at.fhooe.mcm.lri.smdm.server.db.DBConnectionManager;

/**
 * Servlet implementation class CreateSMDMBundleServlet
 */
@WebServlet("/DownloadPolicyServlet")
public class DownloadPolicyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadPolicyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		byte[] zipBundle = null;
		
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
		zipBundle = dbConnection.getLatestPolicy();

		// // Serve the data to response's stream
		String filename = "smdm_update_bundle.zip";

		if (zipBundle != null) {
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);

			response.setContentType("application/x-download");
			response.setContentLength(zipBundle.length);
			response.getOutputStream().write(zipBundle);
		} else {
			response.sendError(500, "Download of the policy failed.\n\n");
		}
		
		dbConnection.close();
	}

	// TODO COULD BE REMOVED; REFACTORED IN CREATESMDMPOLICY
	// /**
	// * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	// * response)
	// */
	// protected void doPost(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	// byte[] zipBundle = null;
	//
	// // We will use this stream as output stream of our ZipOutputStream
	// // and later we'll get the data from it by calling toByteArray() method
	// ByteArrayOutputStream zipBaos = new ByteArrayOutputStream();
	//
	// try {
	// // Create the ZIP file output stream (we will write our zip entries
	// // (= files) into it)
	// ZipOutputStream zipOut = new ZipOutputStream(zipBaos);
	//
	// // String pathOfFile1InWarDirectory =
	// // "/content/device_admin_policy.json";
	// //
	// // StringBuilder sb = new StringBuilder();
	// //
	// // sb.append("/content/device_admin_policy.json");
	// // sb.append("/content/eops.xml");
	// // sb.append("/content/ifw.xml");
	// // sb.append("/content/mac_permissions.xml");
	// // sb.append("/content/property_contexts");
	// // sb.append("/content/file_contexts");
	// // sb.append("/content/sepolicy");
	// // sb.append("/content/seapp_contexts");
	// //
	// // / Get the files as byte array
	// byte[] bDeviceAdmin = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("content/device_admin_policy.json"));
	// byte[] bEops = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/eops.xml"));
	// byte[] bifw = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/ifw.xml"));
	// byte[] bMac = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/mac_permissions.xml"));
	// byte[] bProperty = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/property_contexts"));
	// byte[] bFile = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/file_contexts"));
	// byte[] bSEPolicy = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/sepolicy"));
	// byte[] bSEApp = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/seapp_contexts"));
	// byte[] bContext = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/context_policy"));
	//
	// if (bDeviceAdmin == null || bEops == null || bifw == null
	// || bMac == null || bProperty == null || bFile == null
	// || bFile == null || bSEPolicy == null || bSEApp == null
	// || bContext == null) {
	// // this is bad
	// throw new IOException("The policy files cannot be loaded");
	// }
	//
	// byte[] privKey = IOUtils.toByteArray(getServletContext()
	// .getResourceAsStream("/content/testkey.pk8"));
	//
	// zipBundle = BuildSMDMBundle.buildSMDMBundle(privKey, "1", "NONE",
	// "/content/smdm_update_bundle.zip", bDeviceAdmin, bFile,
	// bProperty, bSEPolicy, bSEApp, bifw, bEops, bMac, bContext);
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// // Creation of the zip file failed; inform the browser about it
	// response.sendError(
	// 500,
	// "Creation of the zip file failed with exception:\n\n"
	// + e.getLocalizedMessage());
	// return;
	// }
	//
	// // Get the zip data from the ByteArrayOutputStream
	// // byte[] zipData = IOUtils.toByteArray(getServletContext()
	// // .getResourceAsStream("/content/smdm_update_bundle.zip"));
	//
	// // Serve the data to response's stream
	// String filename = "smdm_update_bundle.zip";
	//
	// // following header statement instructs the web browser
	// // to treat the file as attachment (= to download the file)
	// if (zipBundle != null) {
	// response.setHeader("Content-Disposition", "attachment; filename="
	// + filename);
	//
	// response.setContentType("application/x-download");
	// response.setContentLength(zipBundle.length);
	// response.getOutputStream().write(zipBundle);
	//
	// // write zip to db
	// writePolicyToDB(zipBundle);
	// } else {
	// response.sendError(500, "Creation of the zip file failed.\n\n");
	// }
	// }
}
