package at.fhooe.mcm.lri.smdm.server.db;

import java.sql.Timestamp;

/**
 * This class implements a object for storing and updating device informations.
 * 
 * @author Lorenz Riedler
 * 
 */
public class DBDeviceInfo {
	private String deviceId;
	private Timestamp timestamp;
	private String position;
	private long enforcedPolicyVersion;
	private boolean cameraDisabled;
	private int mnc;
	private int cellId;
	private int ncc;

	public DBDeviceInfo(String deviceId, Timestamp timestamp, String position,
			long enforcedPolicyVersion, boolean cameraDisabled, int mnc,
			int cellId, int ncc) {
		super();
		this.deviceId = deviceId;
		this.timestamp = timestamp;
		this.position = position;
		this.enforcedPolicyVersion = enforcedPolicyVersion;
		this.cameraDisabled = cameraDisabled;
		this.mnc = mnc;
		this.cellId = cellId;
		this.ncc = ncc;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public long getEnforcedPolicyVersion() {
		return enforcedPolicyVersion;
	}

	public void setEnforcedPolicyVersion(long enforcedPolicyVersion) {
		this.enforcedPolicyVersion = enforcedPolicyVersion;
	}

	public boolean getCameraDisabled() {
		return cameraDisabled;
	}

	public void setCameraDisabled(boolean cameraDisabled) {
		this.cameraDisabled = cameraDisabled;
	}

	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}

	public int getCellId() {
		return cellId;
	}

	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public int getNcc() {
		return ncc;
	}

	public void setNcc(int ncc) {
		this.ncc = ncc;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("Device ID: ");
		sb.append(this.deviceId);
		sb.append("\nTimestamp: ");
		sb.append(timestamp);
		sb.append("\nPosition: ");
		sb.append(position);
		sb.append("\nEnforced policy version: ");
		sb.append(enforcedPolicyVersion);
		sb.append("\nCamera disabled: ");
		sb.append(cameraDisabled);
		sb.append("\nMNC:");
		sb.append(mnc);
		sb.append("\nCell ID: ");
		sb.append(cellId);
		sb.append("\nNCC");
		sb.append(ncc);

		return sb.toString();
	}
}
