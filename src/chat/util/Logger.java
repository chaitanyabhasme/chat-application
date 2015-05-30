package chat.util;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Logger class to dump the required history into local file
 * @author Chaitanya
 *
 */
public class Logger {
	private static PrintWriter writer;
	
	/**
	 * prints the input String to file using writer
	 * @param msg String to be written
	 */
	public static void dumpIntoFile(String msg){
		writer.println(msg);
	}
	
	/**
	 * initialize the PrintWriter
	 * @param filename file to be written
	 */
	public static void setFileWriter(String filename){
		try {
			writer = new PrintWriter(filename);
		} catch (IOException e) {
			System.out.println("Unable to open the file!");
			System.exit(1);
		}
	}
	/**
	 * This method closes the file writer
	 */
	public static void closeFile(){
		writer.close();
	}
}
