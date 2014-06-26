package at.fhooe.mcm.lri.smdm.server.gae;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * An entity for Android device information.
 * 
 * Its associated endpoint, DeviceInfoEndpoint.java, was directly generated from
 * this class - the Google Plugin for Eclipse allows you to generate endpoints
 * directly from entities!
 * 
 * DeviceInfoEndpoint.java will be used for registering devices with this App
 * Engine application. Registered devices will receive messages broadcast by
 * this application over Google Cloud Messaging (GCM). If you'd like to take a
 * look at the broadcasting code, check out MessageEndpoint.java.
 * 
 * For more information, see
 * http://developers.google.com/eclipse/docs/cloud_endpoints.
 * 
 * NOTE: This DeviceInfoEndpoint.java does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
// DeviceInfoEndpoint has NO AUTHENTICATION - it is an OPEN ENDPOINT!
@Entity
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DeviceInfo {

	/*
	 * The Google Cloud Messaging registration token for the device. This token
	 * indicates that the device is able to receive messages sent via GCM.
	 */
	/** The device registration id. */
	@Id
	@Persistent
	private String deviceRegistrationID;

	/*
	 * Some identifying information about the device, such as its manufacturer
	 * and product name.
	 */
	/** The device information. */
	private String deviceInformation;

	/*
	 * Timestamp indicating when this device registered with the application.
	 */
	/** The timestamp. */
	private long timestamp;

	/** Boolean indicating of the devices camera is enabled or disabled. */
	private boolean cameraEnabled;

	/** The password forced. */
	private boolean passwordForced;

	/** The position. */
	private String position;

	/** The key. */
	@PrimaryKey
	@Persistent
	private Key key;

	@Persistent
	private Boolean debug;

	//
	// Methods
	//

	public DeviceInfo(Key key, String deviceRegID) {
		this.key = key;
		this.deviceRegistrationID = deviceRegID;
	}

	//
	// Getter and Setters
	//

	public boolean getDebug() {
		return debug != null ? debug.booleanValue() : false;
	}
	
    public void setDebug(boolean debug) {
        this.debug = new Boolean(debug);
    }


	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * Gets the password forced.
	 * 
	 * @return the password forced
	 */
	public boolean getPasswordForced() {
		return passwordForced;
	}

	/**
	 * Sets the password forced.
	 * 
	 * @param forced
	 *            the new password forced
	 */
	public void setPasswordForced(boolean forced) {
		this.passwordForced = forced;
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Gets the camera enabled.
	 * 
	 * @return the camera enabled
	 */
	public boolean getCameraEnabled() {
		return cameraEnabled;
	}

	/**
	 * Sets the position.
	 * 
	 * @param _pos
	 *            the new position
	 */
	public void setPosition(String _pos) {
		position = _pos;
	}

	/**
	 * Gets the device registration id.
	 * 
	 * @return the device registration id
	 */
	public String getDeviceRegistrationID() {
		return deviceRegistrationID;
	}

	/**
	 * Gets the device information.
	 * 
	 * @return the device information
	 */
	public String getDeviceInformation() {
		return this.deviceInformation;
	}

	/**
	 * Sets the camera enabled.
	 * 
	 * @param _enable
	 *            the new camera enabled
	 */
	public void setCameraEnabled(boolean _enable) {
		cameraEnabled = _enable;
	}

	/**
	 * Sets the device registration id.
	 * 
	 * @param deviceRegistrationID
	 *            the new device registration id
	 */
	public void setDeviceRegistrationID(String deviceRegistrationID) {
		this.deviceRegistrationID = deviceRegistrationID;
	}

	/**
	 * Sets the device information.
	 * 
	 * @param deviceInformation
	 *            the new device information
	 */
	public void setDeviceInformation(String deviceInformation) {
		this.deviceInformation = deviceInformation;
	}

	/**
	 * Gets the timestamp.
	 * 
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 * 
	 * @param timestamp
	 *            the new timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
