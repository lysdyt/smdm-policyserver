package at.fhooe.mcm.lri.smdm.server.db;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for storing user data. This interface is the abstraction for either
 * connecting to a MySQL database or the Google datastore.
 * 
 * @author Lorenz Riedler
 * 
 */
public interface DBConnection {

	/**
	 * Returns true if the DB connection is active, false otherwise.
	 * 
	 * @return
	 */
	public boolean isConnected();

	/**
	 * Registers a device/user to with the policy server.
	 * 
	 * @param user
	 * @return true if successful, false otherwise
	 */
	public boolean registerDevice(DBUser user);

	/**
	 * Returns a device/user entry from the policy server.
	 * 
	 * @param deviceId
	 * @return
	 */
	public DBUser getUser(String deviceId);

	/**
	 * Returns all device/user entry from the policy server.
	 * 
	 * @return array of all registered devcies
	 */
	public List<DBUser> getUsers();

	/**
	 * Adds a device info object to the policy server.
	 * 
	 * @param info
	 * @return
	 */
	public boolean addDeviceInfo(DBDeviceInfo info);

	/**
	 * Returns the latest device info from the DB.
	 * 
	 * @param deviceId
	 * @return
	 */
	public DBDeviceInfo getLatestDeviceInfo(String deviceId);

	/**
	 * Returns all DBDeviceInfo object from the DB.
	 * 
	 * @param deviceId
	 * @return
	 */
	public List<DBDeviceInfo> getAllDeviceInfos(String deviceId);
	
	public String getLatestPolicyVersion();
	public boolean writePolicyToDB(byte[] policy);
	public byte[] getLatestPolicy();
	public boolean storeBlob(String blob);

	/**
	 * Checks if the given device id is already registered with the policy
	 * server.
	 * 
	 * @param deviceId
	 * @return
	 */
	public boolean isRegistered(String deviceId);

	/**
	 * Closes the connection to the database.
	 * 
	 * @return
	 */
	public boolean close();

	/**
	 * XXX not implemented
	 * 
	 * @return
	 */
	public boolean open();

	/**
	 * Unregisters a device from the policy server.
	 * 
	 * @param deviceId
	 * @return
	 */
	public boolean unregisterDevice(String deviceId);
}
