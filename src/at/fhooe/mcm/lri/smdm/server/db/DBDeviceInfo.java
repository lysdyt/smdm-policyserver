package at.fhooe.mcm.lri.smdm.server.db;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class DBDeviceInfo {
	private String deviceId;
	private Timestamp timestamp;
	//position
	private String positionGPS;
	private String positionNetwork;

	//misc
	private String enforcedPolicyVersion;
	private boolean cameraDisabled;
	private int deviceSDKVersion;
	//network
	private int mnc;
	private int mcc;
	private int cellId;
	private int ncc;	
	private String networkOperator;
	private String cellLoc;
	private String iMEI;
	private String deviceSoftwareVersion;
	private List<String> neighbourCellInfo;
	private String NetworkCountryIso;
	private String telephoneType;
	private String simState;
	private String simCountry;
	private String simOperatorCode;
	private String simOperatorName;
	private String simSerial;
	//wifi
	private Map<String,Integer> accessableWLAN;
	private String wifiSSID;
	private String wifiBSSID;
	private String wifiIp;
	private String wifiMAC;
	private String wifiRSSI;
	private String wifiState;
	private String wlanMAC;
	private String dump;

	public DBDeviceInfo(){
		super();
		accessableWLAN = null;
		neighbourCellInfo = null;
	}
	
//	public DBDeviceInfo(String deviceId, Timestamp timestamp, String position,
//			long enforcedPolicyVersion, boolean cameraDisabled, int mnc,
//			int cellId, int ncc) {
//		super();
//		this.deviceId = deviceId;
//		this.timestamp = timestamp;
//		this.position = position;
//		this.enforcedPolicyVersion = enforcedPolicyVersion;
//		this.cameraDisabled = cameraDisabled;
//		this.mnc = mnc;
//		this.cellId = cellId;
//		this.ncc = ncc;
//	}
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public static DBDeviceInfo fromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, DBDeviceInfo.class);
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

	public String getPositionGPS() {
		return positionGPS;
	}

	public void setPositionGPS(String position) {
		this.positionGPS = position;
	}
	public String getPositionNetwork() {
		return positionNetwork;
	}

	public void setPositionNetwork(String position) {
		this.positionNetwork = position;
	}

	public String getEnforcedPolicyVersion() {
		return enforcedPolicyVersion;
	}

	public void setEnforcedPolicyVersion(String enforcedPolicyVersion) {
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

	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
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

	/**
	 * @return the accessableWLAN
	 */
	public Map<String, Integer> getAccessableWLAN() {
		return accessableWLAN;
	}

	/**
	 * @param accessableWLAN the accessableWLAN to set
	 */
	public void setAccessableWLAN(Map<String, Integer> accessableWLAN) {
		this.accessableWLAN = accessableWLAN;
	}

	/**
	 * @return the connectedSSID
	 */
	public String getWifiSSID() {
		return wifiSSID;
	}

	/**
	 * @return the connectedBSSID
	 */
	public String getWifiBSSID() {
		return wifiBSSID;
	}

	
	/**
	 * @return the wifiState
	 */
	public String getWifiState() {
		return wifiState;
	}

	/**
	 * @return the wlanMAC
	 */
	public String getWlanMAC() {
		return wlanMAC;
	}

	/**
	 * @param connectedSSID the connectedSSID to set
	 */
	public void setWifiSSID(String connectedSSID) {
		this.wifiSSID = connectedSSID;
	}

	/**
	 * @param connectedBSSID the connectedBSSID to set
	 */
	public void setWifiBSSID(String connectedBSSID) {
		this.wifiBSSID = connectedBSSID;
	}

	
	/**
	 * @param wifiState the wifiState to set
	 */
	public void setWifiState(String wifiState) {
		this.wifiState = wifiState;
	}

	/**
	 * @param wlanMAC the wlanMAC to set
	 */
	public void setWlanMAC(String wlanMAC) {
		this.wlanMAC = wlanMAC;
	}

	/**
	 * @return the deviceSDKVersion
	 */
	public int getDeviceSDKVersion() {
		return deviceSDKVersion;
	}

	/**
	 * @param deviceSDKVersion the deviceSDKVersion to set
	 */
	public void setDeviceSDKVersion(int deviceSDKVersion) {
		this.deviceSDKVersion = deviceSDKVersion;
	}

	public String getWifiIp() {
		return wifiIp;
	}

	public void setWifiIp(String wifiIp) {
		this.wifiIp = wifiIp;
	}

	public String getWifiMAC() {
		return wifiMAC;
	}

	public void setWifiMAC(String wifiMAC) {
		this.wifiMAC = wifiMAC;
	}

	public String getWifiRSSI() {
		return wifiRSSI;
	}

	public void setWifiRSSI(String wifiRSSI) {
		this.wifiRSSI = wifiRSSI;
	}

	public String getNetworkOperator() {
		return networkOperator;
	}

	public void setNetworkOperator(String networkOperator) {
		this.networkOperator = networkOperator;
	}

	public String getCellLoc() {
		return cellLoc;
	}

	public void setCellLoc(String cellLoc) {
		this.cellLoc = cellLoc;
	}

	public String getiMEI() {
		return iMEI;
	}

	public void setiMEI(String iMEI) {
		this.iMEI = iMEI;
	}

	public String getDeviceSoftwareVersion() {
		return deviceSoftwareVersion;
	}

	public void setDeviceSoftwareVersion(String deviceSoftwareVersion) {
		this.deviceSoftwareVersion = deviceSoftwareVersion;
	}

	public List<String> getNeighbourCellInfo() {
		return neighbourCellInfo;
	}

	public void setNeighbourCellInfo(List<String> neighbourCellInfo) {
		this.neighbourCellInfo = neighbourCellInfo;
	}

	public String getNetworkCountryIso() {
		return NetworkCountryIso;
	}

	public void setNetworkCountryIso(String networkCountryIso) {
		NetworkCountryIso = networkCountryIso;
	}

	public String getTelephoneType() {
		return telephoneType;
	}

	public void setTelephoneType(String telephoneType) {
		this.telephoneType = telephoneType;
	}

	public String getSimState() {
		return simState;
	}

	public void setSimState(String simState) {
		this.simState = simState;
	}

	public String getSimCountry() {
		return simCountry;
	}

	public void setSimCountry(String simCountry) {
		this.simCountry = simCountry;
	}

	public String getSimOperatorCode() {
		return simOperatorCode;
	}

	public void setSimOperatorCode(String simOperatorCode) {
		this.simOperatorCode = simOperatorCode;
	}

	public String getSimOperatorName() {
		return simOperatorName;
	}

	public void setSimOperatorName(String simOperatorName) {
		this.simOperatorName = simOperatorName;
	}

	public String getSimSerial() {
		return simSerial;
	}

	public void setSimSerial(String simSerial) {
		this.simSerial = simSerial;
	}

	public String getDump() {
		return dump;
	}

	public void setDump(String dump) {
		this.dump = dump;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("Device ID: ");
		sb.append(this.deviceId);
		sb.append("\nTimestamp: ");
		sb.append(timestamp);
		sb.append("\nPosition GPS: ");
		sb.append(positionGPS);
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
		sb.append("\nDUMP:\n");
		sb.append(dump);
		
		return sb.toString();
	}
}
