package at.fhooe.mcm.lri.smdm.server.db;

import java.sql.Timestamp;

/**
 * Implements a class which allows the registering of a device/user with the
 * policy server. This object must be handed to the DBConnection to
 * register/unregister a device.
 * 
 * @author Lorenz Riedler
 * 
 */
public class DBUser {
	private String deviceId;
	private String registrationId;
	private String email;
	private Timestamp registrationTime;
	private Timestamp lastUpdate;

	public DBUser(String deviceId, String registrationId, String email,
			Timestamp timestamp, Timestamp lastUpdate) {
		super();
		this.deviceId = deviceId;
		this.registrationId = registrationId;
		this.email = email;
		this.registrationTime = timestamp;
		this.lastUpdate = lastUpdate;
	}

	//
	// Getter & Setters
	//

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Timestamp registrationTime) {
		this.registrationTime = registrationTime;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(deviceId);
		sb.append(", ");
		sb.append(registrationId);
		sb.append(", ");
		sb.append(email);
		sb.append(", ");
		sb.append(registrationTime);
		sb.append(", ");
		sb.append(lastUpdate);
		return sb.toString();
	}
}
