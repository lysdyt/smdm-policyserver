/**  */
package at.fhooe.mcm.lri.smdm.server.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * Device administration policy POJO. This policy file should contain all device
 * admin functionality.
 * 
 * @author Lorenz
 * 
 */
@Entity
public class Policy {
	private static final String VERSION_ID = null;
	private static final String PASSWORD_ENABLED = null;
	private static final String PASSWORD_MIN_LENGTH = null;
	private static final String PASSWORD_ALPHANUMERIC = null;
	private static final String PASSWORD_COMPLEX = null;
	private static final String PASSWORD_MIN_UPPER_LETTERS = null;
	private static final String PASSWORD_MIN_LETTERS = null;
	private static final String PASSWORD_MIN_LOW_LETTERS = null;
	private static final String PASSWORD_MIN_NON_LETTERS = null;
	private static final String PASSWORD_MIN_NUMERICAL = null;
	private static final String PASSWORD_MIN_SYMBOLS = null;
	private static final String PASSWORD_TIMEOUT = null;
	private static final String PASSWORD_HISTORY_COUNT = null;
	private static final String PASSWORD_MAX_FAILED_ATTEMPTS = null;
	private static final String TIME_INACTIVE_LOCK = null;
	private static final String REQUIRE_STORAGE_ENCRYPTION = null;
	private static final String CAMERA_DISABLE = null;
	/**
	 * The version of the policy. Must be unique, and higher than the old
	 * policy.
	 */
	@Id
	private long versionId;
	/**
	 * Set the required number of characters for the password. For example, you
	 * can require PIN or passwords to have at least six characters.
	 */
	private boolean passwordEnabled;
	/**
	 * Set the required number of characters for the password. For example, you
	 * can require PIN or passwords to have at least six characters.
	 */
	private int passwordMinLenght;
	/**
	 * Requires that passwords have a combination of letters and numbers. They
	 * may include symbolic characters.
	 */
	private boolean passwordAlphanumeric;
	/**
	 * Requires that passwords must contain at least a letter, a numerical
	 * digit, and a special symbol. Introduced in Android 3.0.
	 */
	private boolean passwordComplex;
	/**
	 * The minimum number of letters required in the password for all admins or
	 * a particular one. Introduced in Android 3.0.
	 */
	private int passwordMinLetters;
	/**
	 * The minimum number of lowercase letters required in the password for all
	 * admins or a particular one. Introduced in Android 3.0.
	 */
	private int passwordMinLowLetters;
	/**
	 * The minimum number of non-letter characters required in the password for
	 * all admins or a particular one. Introduced in Android 3.0.
	 */
	private int passwordMinNonLetters;

	/**
	 * The minimum number of numerical digits required in the password for all
	 * admins or a particular one. Introduced in Android 3.0.
	 */
	private int passwordMinNumerical;

	/**
	 * The minimum number of symbols required in the password for all admins or
	 * a particular one. Introduced in Android 3.0.
	 */
	private int passwordMinSymbols;

	/**
	 * The minimum number of uppercase letters required in the password for all
	 * admins or a particular one. Introduced in Android 3.0.
	 */
	private int passwordMinUpperLetters;

	/**
	 * When the password will expire, expressed as a delta in milliseconds from
	 * when a device admin sets the expiration timeout. Introduced in Android
	 * 3.0.
	 */
	private long passwordTimeout;
	/**
	 * This policy prevents users from reusing the last n unique passwords. This
	 * policy is typically used in conjunction with
	 * setPasswordExpirationTimeout(), which forces users to update their
	 * passwords after a specified amount of time has elapsed. Introduced in
	 * Android 3.0.
	 */
	private int passwordHistoryCount;

	/**
	 * Specifies how many times a user can enter the wrong password before the
	 * device wipes its data. The Device Administration API also allows
	 * administrators to remotely reset the device to factory defaults. This
	 * secures data in case the device is lost or stolen.
	 */
	private int maxFailedAttempts;
	/**
	 * Sets the length of time since the user last touched the screen or pressed
	 * a button before the device locks the screen. When this happens, users
	 * need to enter their PIN or passwords again before they can use their
	 * devices and access data. The value can be between 1 and 60 minutes.
	 */
	private int timeInactiveLock;

	/**
	 * Specifies that the storage area should be encrypted, if the device
	 * supports it. Introduced in Android 3.0.
	 */
	private boolean requireStorageEncryption;

	/**
	 * Specifies that the camera should be disabled. Note that this doesn't have
	 * to be a permanent disabling. The camera can be enabled/disabled
	 * dynamically based on context, time, and so on. Introduced in Android 4.0.
	 */
	private boolean cameraDisable;

	//
	// Methods
	//

	/**
	 * Instantiates a new object of the class Policy.
	 * 
	 * @param _version
	 */
	public Policy(long _version) {
		versionId = _version;

	}

	/**
	 * Returns a JSONObject of the policy.
	 * 
	 * @return the policy as JSONObject
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws JSONException {
		JSONObject ret = new JSONObject();

		ret.put(VERSION_ID, versionId);
		ret.put(PASSWORD_ENABLED, passwordEnabled);
		ret.put(PASSWORD_MIN_LENGTH, passwordMinLenght);
		ret.put(PASSWORD_ALPHANUMERIC, passwordAlphanumeric);
		ret.put(PASSWORD_COMPLEX, passwordComplex);
		ret.put(PASSWORD_MIN_LETTERS, passwordMinLetters);
		ret.put(PASSWORD_MIN_LOW_LETTERS, passwordMinLowLetters);
		ret.put(PASSWORD_MIN_NON_LETTERS, passwordMinNonLetters);
		ret.put(PASSWORD_MIN_NUMERICAL, passwordMinNumerical);
		ret.put(PASSWORD_MIN_SYMBOLS, passwordMinSymbols);
		ret.put(PASSWORD_MIN_UPPER_LETTERS, passwordMinUpperLetters);
		ret.put(PASSWORD_TIMEOUT, passwordTimeout);
		ret.put(PASSWORD_HISTORY_COUNT, passwordHistoryCount);
		ret.put(PASSWORD_MAX_FAILED_ATTEMPTS, maxFailedAttempts);
		ret.put(TIME_INACTIVE_LOCK, timeInactiveLock);
		ret.put(REQUIRE_STORAGE_ENCRYPTION, requireStorageEncryption);
		ret.put(CAMERA_DISABLE, cameraDisable);

		return ret;
	}

	/**
	 * Returns a string representation of the policy.
	 */
	@Override
	public String toString() {
		// TODO add specific string
		return "" + versionId;
	}

	//
	// Getters and Setters
	//

	/**
	 * @return the versionId
	 */
	public long getVersionId() {
		return versionId;
	}

	/**
	 * @param versionId
	 *            the versionId to set
	 */
	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	/**
	 * @return the passwordEnabled
	 */
	public boolean isPasswordEnabled() {
		return passwordEnabled;
	}

	/**
	 * @param passwordEnabled
	 *            the passwordEnabled to set
	 */
	public void setPasswordEnabled(boolean passwordEnabled) {
		this.passwordEnabled = passwordEnabled;
	}

	/**
	 * @return the passwordMinLenght
	 */
	public int getPasswordMinLenght() {
		return passwordMinLenght;
	}

	/**
	 * @param passwordMinLenght
	 *            the passwordMinLenght to set
	 */
	public void setPasswordMinLenght(int passwordMinLenght) {
		this.passwordMinLenght = passwordMinLenght;
	}

	/**
	 * @return the passwordAlphanumeric
	 */
	public boolean isPasswordAlphanumeric() {
		return passwordAlphanumeric;
	}

	/**
	 * @param passwordAlphanumeric
	 *            the passwordAlphanumeric to set
	 */
	public void setPasswordAlphanumeric(boolean passwordAlphanumeric) {
		this.passwordAlphanumeric = passwordAlphanumeric;
	}

	/**
	 * @return the passwordComplex
	 */
	public boolean isPasswordComplex() {
		return passwordComplex;
	}

	/**
	 * @param passwordComplex
	 *            the passwordComplex to set
	 */
	public void setPasswordComplex(boolean passwordComplex) {
		passwordComplex = passwordComplex;
	}

	/**
	 * @return the passwordMinLetters
	 */
	public int getPasswordMinLetters() {
		return passwordMinLetters;
	}

	/**
	 * @param passwordMinLetters
	 *            the passwordMinLetters to set
	 */
	public void setPasswordMinLetters(int passwordMinLetters) {
		this.passwordMinLetters = passwordMinLetters;
	}

	/**
	 * @return the passwordMinLowLetters
	 */
	public int getPasswordMinLowLetters() {
		return passwordMinLowLetters;
	}

	/**
	 * @param passwordMinLowLetters
	 *            the passwordMinLowLetters to set
	 */
	public void setPasswordMinLowLetters(int passwordMinLowLetters) {
		this.passwordMinLowLetters = passwordMinLowLetters;
	}

	/**
	 * @return the passwordMinNonLetters
	 */
	public int getPasswordMinNonLetters() {
		return passwordMinNonLetters;
	}

	/**
	 * @param passwordMinNonLetters
	 *            the passwordMinNonLetters to set
	 */
	public void setPasswordMinNonLetters(int passwordMinNonLetters) {
		this.passwordMinNonLetters = passwordMinNonLetters;
	}

	/**
	 * @return the passwordMinNumerical
	 */
	public int getPasswordMinNumerical() {
		return passwordMinNumerical;
	}

	/**
	 * @param passwordMinNumerical
	 *            the passwordMinNumerical to set
	 */
	public void setPasswordMinNumerical(int passwordMinNumerical) {
		this.passwordMinNumerical = passwordMinNumerical;
	}

	/**
	 * @return the passwordMinSymbols
	 */
	public int getPasswordMinSymbols() {
		return passwordMinSymbols;
	}

	/**
	 * @param passwordMinSymbols
	 *            the passwordMinSymbols to set
	 */
	public void setPasswordMinSymbols(int passwordMinSymbols) {
		this.passwordMinSymbols = passwordMinSymbols;
	}

	/**
	 * @return the passwordMinUpperLetters
	 */
	public int getPasswordMinUpperLetters() {
		return passwordMinUpperLetters;
	}

	/**
	 * @param passwordMinUpperLetters
	 *            the passwordMinUpperLetters to set
	 */
	public void setPasswordMinUpperLetters(int passwordMinUpperLetters) {
		this.passwordMinUpperLetters = passwordMinUpperLetters;
	}

	/**
	 * @return the passwordTimeout
	 */
	public long getPasswordTimeout() {
		return passwordTimeout;
	}

	/**
	 * @param passwordTimeout
	 *            the passwordTimeout to set
	 */
	public void setPasswordTimeout(long passwordTimeout) {
		this.passwordTimeout = passwordTimeout;
	}

	/**
	 * @return the passwordHistoryCount
	 */
	public int getPasswordHistoryCount() {
		return passwordHistoryCount;
	}

	/**
	 * @param passwordHistoryCount
	 *            the passwordHistoryCount to set
	 */
	public void setPasswordHistoryCount(int passwordHistoryCount) {
		this.passwordHistoryCount = passwordHistoryCount;
	}

	/**
	 * @return the maxFailedAttempts
	 */
	public int getMaxFailedAttempts() {
		return maxFailedAttempts;
	}

	/**
	 * @param maxFailedAttempts
	 *            the maxFailedAttempts to set
	 */
	public void setMaxFailedAttempts(int maxFailedAttempts) {
		this.maxFailedAttempts = maxFailedAttempts;
	}

	/**
	 * @return the timeInactiveLock
	 */
	public int getTimeInactiveLock() {
		return timeInactiveLock;
	}

	/**
	 * @param timeInactiveLock
	 *            the timeInactiveLock to set
	 */
	public void setTimeInactiveLock(int timeInactiveLock) {
		this.timeInactiveLock = timeInactiveLock;
	}

	/**
	 * @return the requireStorageEncryption
	 */
	public boolean isRequireStorageEncryption() {
		return requireStorageEncryption;
	}

	/**
	 * @param requireStorageEncryption
	 *            the requireStorageEncryption to set
	 */
	public void setRequireStorageEncryption(boolean requireStorageEncryption) {
		this.requireStorageEncryption = requireStorageEncryption;
	}

	/**
	 * @return the cameraDisable
	 */
	public boolean isCameraDisable() {
		return cameraDisable;
	}

	/**
	 * @param cameraDisable
	 *            the cameraDisable to set
	 */
	public void setCameraDisable(boolean cameraDisable) {
		this.cameraDisable = cameraDisable;
	}

}