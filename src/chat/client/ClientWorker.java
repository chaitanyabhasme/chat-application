package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import chat.util.Logger;
import chat.util.Worker;

/**
 * This class handles working on client side
 * implements Worker interface
 * must have displayMenu(),sendMessage() and recieveMessage()
 *
 */
public class ClientWorker implements Worker{
	
	private String name;
	
	/**
	 * This method displays a menu for client
	 */
	public void displayMenu(){
		System.out.println("**ClientMenu**\n1)Give me a name\n2)Send Message to server\n3)Print Message from server\n4)Quit");
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
			if(msg != null){
				if(msg.equals("BACKUP")){
					out.println(msg);
				}
				else{
					msg = getName() + ":" + msg;
					out.println(msg);
				}
			}
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
		String msg = null;
		try {
			if(br.ready())
				msg = br.readLine();
		} catch (IOException e) {
			System.out.println("Unable to read from input stream!");
			System.exit(1);
		}
		return msg;
	}
	/**
	 * set the name of client
	 * @param nameIn String input name
	 */
	public void setName(String nameIn){
		name = nameIn;
	}
	
	/**
	 * method to get the name of a client
	 * @return name of client 
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method selects a input choice from the end user
	 * and call required methods for each choice entered 
	 * @param sysIn Input stream from which choice is read
	 * @param in Input Stream from which message is read
	 * @param out Output Stream to which message is written
	 * @return true of each choice is completed normally else false.
	 * 		also returns false on choice exit
	 */
	
	public boolean selectChoice(BufferedReader sysIn,BufferedReader in, PrintWriter out){
		String msg;
		int ch;
		System.out.println("Select your choice:");
		try {
			ch = Integer.parseInt(sysIn.readLine());
			switch(ch){
			case 1:
				System.out.println("Enter Name:");
				String myName = sysIn.readLine();
				setName(myName);
				break;
			case 2:
				msg = sendMessage(out);
				if(msg.equals("BACKUP"))
					getHistory(in);
				break;
			case 3:
				msg = receiveMessage(in);
				if(msg != null){
					System.out.println(msg);
				}
				else{
					System.out.println("Server exited");
					return false;
				}
				break;
			case 4:
				return false;
			default:
				System.out.println("Invalid Choice!");
			}
		} catch (IOException e) {
			System.out.println("Unable to read from the input!");
			System.exit(1);
		}
		return true;
	}
	
	/**
	 * This method reads the message history from the required
	 * input stream and dumps the history into local file
	 * using Logger
	 * @param in InputStream from which the message history is to read
	 */
	public void getHistory(BufferedReader in){
		String msg = null;
		Logger.setFileWriter(getName()+"BACKUP.txt");
		try {
			do{
				msg = in.readLine();
				Logger.dumpIntoFile(msg);
			}while(in.ready());
		} catch (IOException e) {
			System.out.println("Error reading the input stream!");
		}
		finally{
			Logger.closeFile();
		}
	}

	/**
	 * overrides toString() method in java.lang.Object
	 */
	@Override
	public String toString() {
		return "ClientWorker [name=" + name + "]";
	}
	
}
