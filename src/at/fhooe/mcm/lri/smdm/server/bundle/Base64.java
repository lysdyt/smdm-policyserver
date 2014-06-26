package at.fhooe.mcm.lri.smdm.server.bundle;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

public class Base64 {

	public static final int DEFAULT = 0;
	public static final int NO_WRAP = 2;

//	public static void main(String[] args) {
//		try {
//			byte[] file = Helper.toByteArray(new File("res/policy.xml"));
//			byte[] otherEncode = encode(file, DEFAULT);
//			byte[] encoded = Base64_2.encodeBase64(file, true);
//			System.err.println("base64_2 encoded file: " + new String(encoded));
//			System.err
//					.println("other encoded file: " + new String(otherEncode));
//			System.err.println("array length: " + encoded.length);
//			System.err.println("unencoded: " + new String(file));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	/**
	 * Returns a base64 encoded byte array. The flag defines if the byte array
	 * should be formated with RFC
	 * 
	 * @param file
	 * @param flags
	 * @return
	 */
	public static byte[] encode(byte[] file, int flags) {
		String base64String = DatatypeConverter.printBase64Binary(file);
		byte[] ret = base64String.getBytes();
		return ret;
	}
}
