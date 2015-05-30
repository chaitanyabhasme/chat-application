package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import chat.util.Worker;

/**
 * This class handles working on server side
 * implements Worker interface
 * must have displayMenu(),sendMessage() and recieveMessage()
 *
 */
public class ServerWorker implements Worker{
	
	/**
	 * This method displays a menu for server
	 */
	public void displayMenu(){
		System.out.println("**ServerMenu**\n1)Send Message to specific client\n2)Print Message from specific client\n3)Quit");
	}
	/**
	 * This method will send the message to required O/P Stream
	 * @param out OutputStream to which the string is written
	 * @return String which is sent
	 */
	public String sendMessage(PrintWriter out){
		String msg = null;
		System.out.println("Enter your message:");
		BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			msg = sysIn.readLine();
			out.println(msg);
		} catch (IOException e) {
			System.out.println("Unable to read from input!");
			System.exit(1);
		}
		return msg;
	}

	/**
	 * This method will receive Message from required I/P stream
	 * @param br InputStream from which the message is to be read
	 * @return String which is read
	 */
	public String receiveMessage(BufferedReader br){
		String msg=null;
		try {
			if(br.ready())
				msg = br.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read from input stream!");
		}
		return msg;
	}
}
