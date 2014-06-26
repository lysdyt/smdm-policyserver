package at.fhooe.mcm.lri.smdm.server.bundle;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.DatatypeConverter;

/**
 * Implements some static helper methods.
 * 
 * @author Lorenz
 * 
 */
public class Helper {


	/**
	 * Returns the given file from path as a byte array.
	 * 
	 * @param context
	 *            the application's context
	 * @param file
	 *            the path to the file to read
	 * @return the file as a byte array
	 * @throws IOException
	 */
	public static byte[] toByteArray(File file)
			throws IOException {
		
		Path path = Paths.get(file.getAbsolutePath());
		return (Files.readAllBytes(path));
	}
	
	
}
