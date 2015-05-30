package chat.util;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Worker Interface.
 * Each class which implements this interface must have displayMenu(),
 * sendMessage() and receiveMessage() functions
 *
 */
public interface Worker {
	
	public void displayMenu();
	
	public String sendMessage(PrintWriter out);
	
	public String receiveMessage(BufferedReader br);

}
