package at.fhooe.mcm.lri.smdm.server.policy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.sun.jmx.snmp.daemon.CommunicationException;
import com.vaadin.server.VaadinService;

import at.fhooe.mcm.lri.mdmpoc.bundle.BuildSMDMBundle;
import at.fhooe.mcm.lri.smdm.server.PolicyServer;
import at.fhooe.mcm.lri.smdm.server.db.DBConnection;
import at.fhooe.mcm.lri.smdm.server.db.DBConnectionManager;
import at.fhooe.mcm.lri.smdm.server.servlets.DBHelper;

public class CreateSMDMPolicy {

	public static boolean createAndSafePolicyBundle() {
		byte[] zipBundle = null;

		// We will use this stream as output stream of our ZipOutputStream
		// and later we'll get the data from it by calling toByteArray() method
		ByteArrayOutputStream zipBaos = new ByteArrayOutputStream();

		try {
			// Create the ZIP file output stream (we will write our zip entries
			// (= files) into it)
			ZipOutputStream zipOut = new ZipOutputStream(zipBaos);

			// String pathOfFile1InWarDirectory =
			// "/content/device_admin_policy.json";
			//
			// StringBuilder sb = new StringBuilder();
			//
			// sb.append("/content/device_admin_policy.json");
			// sb.append("/content/eops.xml");
			// sb.append("/content/ifw.xml");
			// sb.append("/content/mac_permissions.xml");
			// sb.append("/content/property_contexts");
			// sb.append("/content/file_contexts");
			// sb.append("/content/sepolicy");
			// sb.append("/content/seapp_contexts");
			//
			// / Get the files as byte array
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();

			byte[] bDeviceAdmin = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../"
							+ "/content/device_admin_policy.json"));
			byte[] bEops = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../" + "/content/eops.xml"));
			byte[] bifw = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../" + "/content/ifw.xml"));
			byte[] bMac = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../"
							+ "/content/mac_permissions.xml"));
			byte[] bProperty = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../"
							+ "/content/property_contexts"));
			byte[] bFile = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../" + "/content/file_contexts"));
			byte[] bSEPolicy = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../" + "/content/sepolicy"));
			byte[] bSEApp = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../" + "/content/seapp_contexts"));
			byte[] bContext = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../" + "/content/context_policy"));

			if (bDeviceAdmin == null || bEops == null || bifw == null
					|| bMac == null || bProperty == null || bFile == null
					|| bFile == null || bSEPolicy == null || bSEApp == null
					|| bContext == null) {
				// this is bad
				throw new IOException("The policy files cannot be loaded");
			}

			byte[] privKey = IOUtils.toByteArray(classLoader
					.getResourceAsStream("../../" + "/content/testkey.pk8"));

			zipBundle = BuildSMDMBundle.buildSMDMBundle(privKey, "1", "NONE",
					"/content/smdm_update_bundle.zip", bDeviceAdmin, bFile,
					bProperty, bSEPolicy, bSEApp, bifw, bEops, bMac, bContext);

		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}

		// Get the zip data from the ByteArrayOutputStream
		// byte[] zipData = IOUtils.toByteArray(CreateSMDMPolicy.class
		// .getResourceAsStream("/content/smdm_update_bundle.zip"));

		// Serve the data to response's stream
		String filename = "smdm_update_bundle.zip";

		// following header statement instructs the web browser
		// to treat the file as attachment (= to download the file)
		if (zipBundle == null) {
			return false;
		}
		// write zip to db
		return writePolicyToDB(zipBundle);

	}

	public static boolean writePolicyToDB(byte[] policy) {
		boolean ret = false;

		DBConnection dbConnection = DBHelper.getConnection();

		ret = dbConnection.writePolicyToDB(policy);
		// dbConnection.close();

		return ret;
	}
}
