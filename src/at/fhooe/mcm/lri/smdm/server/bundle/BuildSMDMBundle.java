package at.fhooe.mcm.lri.smdm.server.bundle;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.google.common.base.Joiner;
import com.google.common.io.ByteStreams;

public class BuildSMDMBundle {

	public static void main(String[] args) {
		try {
			// load all the files
			System.out.println("Loading all files");
			byte[] file1 = Helper.toByteArray(new File("res/policy.xml"));
			byte[] fileEops = Helper.toByteArray(new File("res/eops.xml"));
			byte[] fileMac = Helper.toByteArray(new File(
					"res/mac_permissions.xml"));
			byte[] fileDeviceAdmin = Helper.toByteArray(new File(
					"res/device_admin_policy.json"));
			byte[] fileIfw = Helper.toByteArray(new File("res/ifw.xml"));
			byte[] file_contexts = Helper.toByteArray(new File(
					"res/file_contexts"));
			byte[] property_contexts = Helper.toByteArray(new File(
					"res/property_contexts"));
			byte[] sepolicy = Helper.toByteArray(new File("res/sepolicy"));
			byte[] seapp_contexts = Helper.toByteArray(new File(
					"res/seapp_contexts"));

			System.out.println("building bundle");
			buildBundle("res/testkey.pk8", "1", "NONE",
					"update_bundle_geil.zip", null, false, file1, fileEops,
					fileMac, fileDeviceAdmin);
			// build IFW bundle
			buildBundle("res/testkey.pk8", "1", "NONE", "ifw_bundle.zip", null,
					false, fileIfw);

			// build eops bundle
			buildBundle("res/testkey.pk8", "1", "NONE", "eops_bundle.zip",
					null, false, fileEops);

			// build perms bundle
			buildBundle("res/testkey.pk8", "1", "NONE", "mac_perms_bundle.zip",
					null, false, fileMac);

			// build device admin bundle
			buildBundle("res/testkey.pk8", "1", "NONE",
					"device_admin_bundle.zip", null, false, fileDeviceAdmin);

			// build selinux update bundle
			// buildsebundle -k
			// $ANDROID_BUILD_TOP/build/target/product/security/testkey.pk8 --
			// file_contexts property_contexts sepolicy seapp_contexts

			buildBundle("res/testkey.pk8", "1", "NONE", "sepolicy_bundle.zip",
					null, true, file_contexts, property_contexts, sepolicy,
					seapp_contexts);

			buildSMDMBundle("res/testkey.pk8", "1", "NONE", "smdm_bundle.zip",
					fileDeviceAdmin, file_contexts, property_contexts,
					sepolicy, seapp_contexts, fileIfw, fileEops, fileMac);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void buildIfwBundle(String privateKey, String version,
			String requiredHash, String outputName, String otherMeta,
			Boolean base64Encoding, byte[] file) {
		buildBundle(privateKey, version, requiredHash, outputName, otherMeta,
				base64Encoding, file);
	}

	public static void buildSMDMBundle(String privateKey, String version,
			String requiredHash, String outputName, byte[] deviceAdminPolicy,
			byte[] file_contexts, byte[] property_contexts, byte[] sepolicy,
			byte[] seapp_contexts, byte[] ifw, byte[] eops, byte[] mac_perms) {

		try {
			// XXX TODO FIXME change this, it is just stupid not doing this with
			// loops and collections
			// Device Admin
			byte[] bundleDeviceAdmin = build_bundle(false, deviceAdminPolicy);
			byte[] signDeviceAdmin = sign_bundle(bundleDeviceAdmin, version,
					privateKey, requiredHash, null);
			// SELINUX
			byte[] bundleSELinux = build_bundle(true, file_contexts,
					property_contexts, sepolicy, seapp_contexts);
			byte[] signSELinux = sign_bundle(bundleSELinux, version,
					privateKey, requiredHash, null);
			// IFW
			byte[] bundleIfw = build_bundle(false, ifw);
			byte[] signIfw = sign_bundle(bundleIfw, version, privateKey,
					requiredHash, null);
			// EOPS
			byte[] bundleEops = build_bundle(false, eops);
			byte[] signEops = sign_bundle(bundleEops, version, privateKey,
					requiredHash, null);
			// MAC_PERMS
			byte[] bundleMac = build_bundle(false, mac_perms);
			byte[] signMac = sign_bundle(bundleMac, version, privateKey,
					requiredHash, null);

			String joinedDeviceAdmin = Joiner.on(":").join(requiredHash,
					new String(signDeviceAdmin), version);
			String joinedSELinux = Joiner.on(":").join(requiredHash,
					new String(signSELinux), version);
			String joinedIfw = Joiner.on(":").join(requiredHash,
					new String(signIfw), version);
			String joinedEops = Joiner.on(":").join(requiredHash,
					new String(signEops), version);
			String joinedMac = Joiner.on(":").join(requiredHash,
					new String(signMac), version);

			byte[] joined_bytes_DeviceAdmin = joinedDeviceAdmin.getBytes();
			byte[] joined_bytes_SELinux = joinedSELinux.getBytes();
			byte[] joined_bytes_Ifw = joinedIfw.getBytes();
			byte[] joined_bytes_Eops = joinedEops.getBytes();
			byte[] joined_bytes_Mac = joinedMac.getBytes();

			// building the zip file
			final ZipOutputStream out = new ZipOutputStream(
					new FileOutputStream(outputName));
			ZipEntry e = new ZipEntry("update_bundle_device_admin");
			out.putNextEntry(e);
			out.write(bundleDeviceAdmin, 0, bundleDeviceAdmin.length);
			out.closeEntry();
			e = new ZipEntry("update_bundle_metadata_device_admin");
			out.putNextEntry(e);
			out.write(joined_bytes_DeviceAdmin, 0,
					joined_bytes_DeviceAdmin.length);
			out.closeEntry();

			e = new ZipEntry("update_bundle_selinux");
			out.putNextEntry(e);
			out.write(bundleSELinux, 0, bundleSELinux.length);
			out.closeEntry();
			e = new ZipEntry("update_bundle_metadata_selinux");
			out.putNextEntry(e);
			out.write(joined_bytes_SELinux, 0, joined_bytes_SELinux.length);
			out.closeEntry();

			e = new ZipEntry("update_bundle_ifw");
			out.putNextEntry(e);
			out.write(bundleIfw, 0, bundleIfw.length);
			out.closeEntry();
			e = new ZipEntry("update_bundle_metadata_ifw");
			out.putNextEntry(e);
			out.write(joined_bytes_Ifw, 0, joined_bytes_Ifw.length);
			out.closeEntry();

			e = new ZipEntry("update_bundle_eops");
			out.putNextEntry(e);
			out.write(bundleEops, 0, bundleEops.length);
			out.closeEntry();
			e = new ZipEntry("update_bundle_metadata_eops");
			out.putNextEntry(e);
			out.write(joined_bytes_Eops, 0, joined_bytes_Eops.length);
			out.closeEntry();

			e = new ZipEntry("update_bundle_mac");
			out.putNextEntry(e);
			out.write(bundleMac, 0, bundleMac.length);
			out.closeEntry();
			e = new ZipEntry("update_bundle_metadata_mac");
			out.putNextEntry(e);
			out.write(joined_bytes_Mac, 0, joined_bytes_Mac.length);
			out.closeEntry();

			// close zip
			out.close();
			System.out.println("done");
		} catch (IOException ioex) {
			System.err.println("IOException error: " + ioex.toString()
					+ ". Exiting.");
		} catch (GeneralSecurityException gex) {
			System.err.println("Security Exception error: " + gex.toString()
					+ ". Exiting.");
		} catch (IllegalArgumentException iax) {
			System.err.println(iax.toString());
		}
	}

	public static void buildBundle(String privateKey, String version,
			String requiredHash, String outputName, String otherMeta,
			Boolean base64Encoding, byte[]... file) {

		try {
			byte[] bundle = build_bundle(base64Encoding, file);
			byte[] signed = sign_bundle(bundle, version, privateKey,
					requiredHash, otherMeta);

			String joined = Joiner.on(":").join(requiredHash,
					new String(signed), version);
			if (otherMeta != null) {
				joined += ":" + otherMeta;
			}
			byte[] joined_bytes = joined.getBytes();

			// building the zip file
			final ZipOutputStream out = new ZipOutputStream(
					new FileOutputStream(outputName));
			ZipEntry e = new ZipEntry("update_bundle");
			out.putNextEntry(e);
			out.write(bundle, 0, bundle.length);
			out.closeEntry();
			e = new ZipEntry("update_bundle_metadata");
			out.putNextEntry(e);
			out.write(joined_bytes, 0, joined_bytes.length);
			out.closeEntry();
			out.close();
			System.out.println("done");
		} catch (IOException ioex) {
			System.err.println("IOException error: " + ioex.toString()
					+ ". Exiting.");
		} catch (GeneralSecurityException gex) {
			System.err.println("Security Exception error: " + gex.toString()
					+ ". Exiting.");
		} catch (IllegalArgumentException iax) {
			System.err.println(iax.toString());
		}
	}

	/**
	 * Given an array of paths to files, create a bundle capable of being loaded
	 * via the ConfigUpdateInstallReceiver mechanism. The order of the entries
	 * in the array will be preserved when building the bundle. The bundle as a
	 * byte array is returned and is capable of being directly loaded via the
	 * ConfigUpdateInstallReceiver mechanism. The format of the returned bundle
	 * depends on the number of passed paths. If more than one file is passed
	 * then a header representing file lengths will precede the file contents.
	 * If only one file is passed then no byte header is attached. If there are
	 * no paths passed then no bundle is created; however, an empty byte array
	 * will still be returned. No metadata about the bundle is returned;
	 * additional processing must be performed to calculate that data.
	 * 
	 * @param paths
	 *            ArrayList of strings representing paths to config files to
	 *            include in the bundle.
	 * @param encodingFunction
	 *            the scheme used to encode the bundle.
	 * 
	 * @exception IOException
	 *                produced by failed or interrupted I/O operations on any of
	 *                the requested paths. Also thrown if the passed paths list
	 *                is null or the encoding scheme is null.
	 * 
	 * @return byte array of the created config bundle.
	 */
	public static byte[] build_bundle(boolean baseEncoding, byte[]... file)
			throws IOException {

		if (file == null) {
			throw new IOException("Given files are null");
		}

		int numOfPaths = file.length;
		int[] lengths = new int[numOfPaths];
		byte[][] files = new byte[numOfPaths][];

		for (int i = 0; i < numOfPaths; i++) {
			if (baseEncoding) {
				// we need to base64 encode the byte array
				files[i] = Base64.encode(file[i], Base64.DEFAULT);
			} else {
				// no base64 encoding
				files[i] = file[i];
			}
			lengths[i] = files[i].length;
		}

		ByteBuffer b = ByteBuffer.allocate(numOfPaths * 4);
		for (int i = 0; i < numOfPaths; i++) {
			b.putInt(lengths[i]);
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		if (numOfPaths > 1) {
			output.write(b.array());
		}
		for (int i = 0; i < numOfPaths; i++) {
			output.write(files[i]);
		}

		return output.toByteArray();
	}

	/**
	 * Takes a byte array as well as the version, previous hash and optional
	 * meta value and computes the digital signature using RSA and SHA-512. The
	 * secured message is then returned as a byte array.
	 * 
	 * @param bundle
	 *            byte array representing the built config bundle.
	 * @param version
	 *            the version of this config update.
	 * @param privKey
	 *            the path to the pkcs8 DER formatted private key used to sign
	 *            the config update.
	 * @param requiredHash
	 *            the hash of the previous config update that will be replaced.
	 * @param otherMeta
	 *            a generic string that will be added to the signed bundle. If
	 *            the string is null it will not be part of the signed bundle.
	 * 
	 * @exception IOException
	 *                produced by failed or interrupted I/O operations when
	 *                retrieving the key.
	 * @exception GeneralSecurityException
	 *                generic security exceptions that result from signature and
	 *                hashing attempts.
	 * 
	 * @return a byte array of the signed message.
	 */
	public static byte[] sign_bundle(byte[] bundle, String version,
			String privKey, String requiredHash, String otherMeta)
			throws IOException, GeneralSecurityException {

		InputStream is = new FileInputStream(new File(privKey));
		byte[] privateKey = ByteStreams.toByteArray(is);
		is.close();
		PrivateKey pk = getPrivateKey(privateKey, privKey);

		Signature signer = Signature.getInstance("SHA512withRSA");
		signer.initSign(pk);
		signer.update(bundle);
		signer.update(version.getBytes());
		signer.update(requiredHash.getBytes());
		if (otherMeta != null) {
			signer.update(otherMeta.getBytes());
		}

		// The signature should be one large string
		return Base64.encode(signer.sign(), Base64.NO_WRAP);
	}

	/**
	 * Return a PrivateKey object of the private key after being decrypted with
	 * a password if needed. The private key is assumed to be encoded according
	 * to the pkcs8 standard.
	 * 
	 * @param privateKey
	 *            the private key to decrypt given as byte array.
	 * @param keyPath
	 *            path to the key given as a string.
	 * 
	 * @exception IOException
	 *                produced by failed or interrupted I/O operations when
	 *                retrieving the password for the key.
	 * @exception GeneralSecurityException
	 *                generic security exceptions that result from signature and
	 *                key operations.
	 * 
	 * @return a PrivateKey interface object to the underlying key material.
	 */
	// TODO should be private
	public static PrivateKey getPrivateKey(byte[] privateKey, String keyPath)
			throws IOException, GeneralSecurityException {

		KeySpec spec = decryptPrivateKey(privateKey, keyPath);
		if (spec == null) {
			spec = new PKCS8EncodedKeySpec(privateKey);
		}

		try {
			return KeyFactory.getInstance("RSA").generatePrivate(spec);
		} catch (InvalidKeySpecException ex) {
			System.err.println(keyPath
					+ " probably not a PKCS#8 DER formatted RSA cert.");
			throw new GeneralSecurityException(ex);
		}
	}

	/**
	 * Based on ghstark's post on Aug 6, 2006 at
	 * http://forums.sun.com/thread.jspa?threadID=758133&messageID=4330949
	 * 
	 * Convert a pkcs8 formatted private key into a PrivateKey interface object.
	 * The private key can be encrypted or not. If encrypted, the user will be
	 * prompted for the password.
	 * 
	 * @param privateKey
	 *            the private key to decrypt given as byte array.
	 * @param keyPath
	 *            path to the key given as a string.
	 * 
	 * @exception IOException
	 *                produced by failed or interrupted I/O operations when
	 *                retrieving the password for the key.
	 * @exception GeneralSecurityException
	 *                generic security exceptions that result from signature and
	 *                key operations.
	 * 
	 * @return a KeySpec object which can be used to derive additional key
	 *         material if the passed private key is encrypted. If the private
	 *         key isn't encrypted then null is returned.
	 */
	// TODO should be private
	public static KeySpec decryptPrivateKey(byte[] privateKey, String keyFile)
			throws IOException, GeneralSecurityException {

		EncryptedPrivateKeyInfo epkInfo;
		try {
			epkInfo = new EncryptedPrivateKeyInfo(privateKey);
		} catch (IOException ex) {
			// Probably not an encrypted key.
			return null;
		}

		char[] password = getPassword(keyFile);

		SecretKeyFactory skFactory = SecretKeyFactory.getInstance(epkInfo
				.getAlgName());
		Key key = skFactory.generateSecret(new PBEKeySpec(password));

		Cipher cipher = Cipher.getInstance(epkInfo.getAlgName());
		cipher.init(Cipher.DECRYPT_MODE, key, epkInfo.getAlgParameters());

		try {
			return epkInfo.getKeySpec(cipher);
		} catch (InvalidKeySpecException ex) {
			System.err.println("Password for " + keyFile + " may be bad.");
			throw new GeneralSecurityException(ex);
		}
	}

	/**
	 * Prompt the user for a password. The password isn't echoed back to the
	 * screen and is returned as a char array. This function assumes there is a
	 * console device associated with the current JVM. This might not be the
	 * case, for instance if started by a background job scheduler. Thus, this
	 * function might have to change in the future.
	 * 
	 * @param keyPath
	 *            the path to the key as a string.
	 * 
	 * @exception IOException
	 *                produced by failed or interrupted I/O operations on the
	 *                current console.
	 * 
	 * @return a char array of the password needed to decrypt the key or null if
	 *         an error occured with the console.
	 */
	// TODO should be private
	public static char[] getPassword(String keyPath) throws IOException {

		char[] password = null;
		Console cons = System.console();
		if (cons != null) {
			final String con = "Enter password for " + keyPath;
			password = cons.readPassword("%s> ", con);
		}

		return password;
	}
}
