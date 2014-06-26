package at.fhooe.mcm.lri.smdm.server.policy;


public class DeviceAdminPolicyConstants {

	/**
	 * Constant for {@link #setPasswordQuality}: the policy has no requirements
	 * for the password. Note that quality constants are ordered so that higher
	 * values are more restrictive.
	 */
	public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;

	/**
	 * Constant for {@link #setPasswordQuality}: the policy allows for
	 * low-security biometric recognition technology. This implies technologies
	 * that can recognize the identity of an individual to about a 3 digit PIN
	 * (false detection is less than 1 in 1,000). Note that quality constants
	 * are ordered so that higher values are more restrictive.
	 */
	public static final int PASSWORD_QUALITY_BIOMETRIC_WEAK = 0x8000;

	/**
	 * Constant for {@link #setPasswordQuality}: the policy requires some kind
	 * of password, but doesn't care what it is. Note that quality constants are
	 * ordered so that higher values are more restrictive.
	 */
	public static final int PASSWORD_QUALITY_SOMETHING = 0x10000;

	/**
	 * Constant for {@link #setPasswordQuality}: the user must have entered a
	 * password containing at least numeric characters. Note that quality
	 * constants are ordered so that higher values are more restrictive.
	 */
	public static final int PASSWORD_QUALITY_NUMERIC = 0x20000;

	/**
	 * Constant for {@link #setPasswordQuality}: the user must have entered a
	 * password containing at least alphabetic (or other symbol) characters.
	 * Note that quality constants are ordered so that higher values are more
	 * restrictive.
	 */
	public static final int PASSWORD_QUALITY_ALPHABETIC = 0x40000;

	/**
	 * Constant for {@link #setPasswordQuality}: the user must have entered a
	 * password containing at least <em>both></em> numeric <em>and</em>
	 * alphabetic (or other symbol) characters. Note that quality constants are
	 * ordered so that higher values are more restrictive.
	 */
	public static final int PASSWORD_QUALITY_ALPHANUMERIC = 0x50000;

	/**
	 * Constant for {@link #setPasswordQuality}: the user must have entered a
	 * password containing at least a letter, a numerical digit and a special
	 * symbol, by default. With this password quality, passwords can be
	 * restricted to contain various sets of characters, like at least an
	 * uppercase letter, etc. These are specified using various methods, like
	 * {@link #setPasswordMinimumLowerCase(ComponentName, int)}. Note that
	 * quality constants are ordered so that higher values are more restrictive.
	 */
	public static final int PASSWORD_QUALITY_COMPLEX = 0x60000;

}
