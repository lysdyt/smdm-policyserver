package at.fhooe.mcm.lri.smdm.server.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

public class DBMySQLConnection implements DBConnection {

	private Connection connection;
	private Statement statement;

	public DBMySQLConnection(String dbURL, String user, String pwd)
			throws ClassNotFoundException, SQLException {
		// XXX remove after debugging
		new com.mysql.jdbc.Driver();
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(dbURL, user, pwd);

		statement = connection.createStatement();
	}

	@Override
	public boolean isConnected() {
		if (connection != null) {
			try {
				return (!connection.isClosed());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean registerDevice(DBUser user) {
		// TODO Auto-generated method stub

		if (isConnected()) {
			if (isRegistered(user.getDeviceId())) {
				System.err.println("Device already registered");
				return false;
			}
			StringBuffer sql = new StringBuffer();

			sql.append("INSERT INTO `users`(`device_id`, `registration_id`, `email`, `registration_time`, `last_update`) VALUES (");
			// sql.append("'NULL', '");
			sql.append("'");
			sql.append(user.getDeviceId());
			sql.append("', '");
			sql.append(user.getRegistrationId());
			sql.append("', '");
			sql.append(user.getEmail());
			sql.append("', '");
			sql.append(user.getRegistrationTime().toString());
			sql.append("', '");
			sql.append(user.getLastUpdate().toString());
			sql.append("')");

			System.out.println("SQL Statement: " + sql.toString());

			try {
				int ret = statement.executeUpdate(sql.toString());
				System.out.println("execurteUpdate returned: " + ret);
				if (ret == 1) {
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public DBUser getUser(String deviceId) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM `users` WHERE `device_id`='");
		sql.append(deviceId);
		sql.append("';");

		System.out.println("SQL Statement: " + sql.toString());

		try {
			ResultSet set = statement.executeQuery(sql.toString());

			if (!set.next()) {
				// empty result
				System.out.println("No user in the db");
				return null;
			}

			DBUser user = new DBUser(set.getString("device_id"),
					set.getString("registration_id"), set.getString("email"),
					set.getTimestamp("registration_time"),
					set.getTimestamp("last_update"));

			System.out.println("User read from DB: " + user);

			// check if there are more than one user with the same ID in the DB.
			if (set.next()) {
				System.err
						.println("THERE SHOULD NOT BE TWO OR MORE ENTRIES WITH THE SAME DEVICE ID IN THE DATABASE!!!!!");
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO return
		return null;
	}

	@Override
	public List<DBUser> getUsers() {
		List<DBUser> users = new ArrayList<DBUser>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM `users`;");

		System.out.println("SQL Statement: " + sql.toString());

		try {
			ResultSet set = statement.executeQuery(sql.toString());

			if (!set.next()) {
				// empty result
				System.out.println("No user in the db");
				return users;
			}

			do {

				String deviceId = set.getString("device_id");
				String registrationId = set.getString("registration_id");
				String email = set.getString("email");
				Timestamp regTime = set.getTimestamp("registration_time");
				Timestamp lastUpdate = set.getTimestamp("last_update");

				users.add(new DBUser(deviceId, registrationId, email, regTime,
						lastUpdate));
			} while (set.next());

			System.out.println("Users read from DB: " + users.size());

			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public boolean addDeviceInfo(DBDeviceInfo info) {
		// TODO Auto-generated method stub

		if (isConnected()) {
			StringBuffer sql = new StringBuffer();

			sql.append("INSERT INTO `deviceinfo`(`id`, `device_id`, `timestamp`, `position`, `enforced_policy_version`, `camera_disabled`, `mnc`, `cell_id`, `ncc`) VALUES (");

			sql.append("NULL");
			sql.append(info.getDeviceId());
			sql.append("', '");
			sql.append(info.getTimestamp().toString());
			sql.append("', '");
			sql.append(info.getPosition());
			sql.append("', '");
			sql.append(info.getEnforcedPolicyVersion());
			sql.append("', '");
			sql.append(info.getCameraDisabled());
			sql.append("', '");
			sql.append(info.getMnc());
			sql.append("', '");
			sql.append(info.getCellId());
			sql.append("', '");
			sql.append(info.getNcc());
			sql.append(")");

			System.out.println(sql.toString());

			try {
				int ret = statement.executeUpdate(sql.toString());

				System.out.println("executeUpdate returned; " + ret);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public DBDeviceInfo getLatestDeviceInfo(String deviceId) {
		DBDeviceInfo info = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM `deviceinfo` WHERE `device_id`='");
		sql.append(deviceId);
		sql.append("' ORDER BY timestamp DESC LIMIT 1;");

		System.out.println("SQL Statement: " + sql.toString());

		try {
			ResultSet set = statement.executeQuery(sql.toString());

			if (!set.next()) {
				// empty set, not registered
				System.out.println("not registered");
				return info;
			}

			boolean camera_disabled;
			int boolValue = set.getInt("camera_disabled");
			System.out.println("Read bool value for camera disabled: "
					+ boolValue);
			if (boolValue == 1) {
				// should be true
				camera_disabled = true;
			} else {
				camera_disabled = false;
			}

			info = new DBDeviceInfo(set.getString("device_id"),
					set.getTimestamp("timestamp"), set.getString("position"),
					set.getLong("enforced_policy_version"), camera_disabled,
					set.getInt("mnc"), set.getInt("cell_id"), set.getInt("ncc"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public boolean writePolicyToDB(byte[] policy) {
		String statement = "INSERT INTO `policy` (`policy`) VALUES (?);";

		System.out.println("SQL Statement: " + statement);

		PreparedStatement ps = null;
		try {
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(statement);

			ps.setBytes(1, policy);
			ps.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public List<DBDeviceInfo> getAllDeviceInfos(String deviceId) {
		List<DBDeviceInfo> infos = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM `deviceinfo` WHERE `device_id`='");
		sql.append(deviceId);
		sql.append("' ORDER BY timestamp DESC LIMIT 1;");

		System.out.println("SQL Statement: " + sql.toString());

		try {
			ResultSet set = statement.executeQuery(sql.toString());

			if (!set.next()) {
				// empty set, not registered
				System.out.println("not registered");
				return infos;
			}

			boolean camera_disabled;
			do {
				int boolValue = set.getInt("camera_disabled");
				System.out.println("Read bool value for camera disabled: "
						+ boolValue);
				if (boolValue == 1) {
					// should be true
					camera_disabled = true;
				} else {
					camera_disabled = false;
				}

				infos.add(new DBDeviceInfo(set.getString("device_id"), set
						.getTimestamp("timestamp"), set.getString("position"),
						set.getLong("enforced_policy_version"),
						camera_disabled, set.getInt("mn"), set
								.getInt("cell_id"), set.getInt("ncc")));

				System.out.println("Read devices infos from DB: "
						+ infos.size());
			} while (set.next());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infos;
	}

	@Override
	public boolean isRegistered(String deviceId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM `users` WHERE `device_id`='");
		sql.append(deviceId);
		sql.append("';");

		System.out.println("SQL Statement: " + sql.toString());

		try {
			ResultSet set = statement.executeQuery(sql.toString());

			if (!set.next()) {
				// empty set, not registered
				System.out.println("not registered");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("registered");
		return true;
	}

	@Override
	public boolean close() {
		try {
			statement.cancel();
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean open() {
		throw new RuntimeException();
	}

	@Override
	public boolean unregisterDevice(String deviceId) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM `users` WHERE `device_id`='");
		sql.append(deviceId);
		sql.append("';");

		System.out.println("SQL Statement: " + sql.toString());

		try {
			int ret = statement.executeUpdate(sql.toString());

			System.out.println("execurteUpdate returned: " + ret);

			if (ret > 0) {
				// should have worked
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO return
		return false;
	}

	@Override
	public String getLatestPolicyVersion() {
		String sql = "SELECT * FROM `policy` ORDER BY timestamp DESC LIMIT 1;";

		try {
			ResultSet set = statement.executeQuery(sql);

			if (!set.next()) {
				// no policy jet
				return null;
			}

			Timestamp stamp = set.getTimestamp("timestamp");

			if (stamp != null) {
				return stamp.toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public byte[] getLatestPolicy() {
		String sql = "SELECT * FROM `policy` ORDER BY timestamp DESC LIMIT 1;";

		try {
			ResultSet set = statement.executeQuery(sql);

			if (!set.next()) {
				// no policy jet
				return null;
			}

			byte[] policy = set.getBytes("policy");

			return policy;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean storeBlob(String blob) {

		String sql = "INSERT INTO `deviceinfo` (`dump`) VALUES ('" + blob
				+ "');";

		try {
			int ret = statement.executeUpdate(sql);
			System.out.println("execurteUpdate returned: " + ret);
			if (ret == 1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
