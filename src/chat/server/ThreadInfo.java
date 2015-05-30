package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ThreadInfo to save all the thread information spawned by the server
 *
 */
public class ThreadInfo {
	
	private BufferedReader inStream;
	private PrintWriter outStream;
	private Socket connection;
	private StringBuffer chatHistory;
	
	/**
	 * Constructor for ThreadInfo 
	 * @param in BufferedReader inStream 
	 * @param out PrintWrite outStream
	 * @param socketIn Socket connection
	 * creates the chatHistory StringBuffer
	 */
	
	public ThreadInfo(Socket socketIn){
		try {
			inStream = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
			outStream = new PrintWriter(socketIn.getOutputStream(),true);
			connection = socketIn;
			chatHistory = new StringBuffer();
		} catch (IOException e) {
			System.out.println("Unable the get the input or output stream.");
			System.exit(1);
		}
	}
	/**
	 * @return the inStream
	 */
	public BufferedReader getInStream() {
		return inStream;
	}
	/**
	 * @return the outStream
	 */
	public PrintWriter getOutStream() {
		return outStream;
	}
	
	/**
	 * return the connection
	 * @return
	 */
	public Socket getSocket(){
		return connection;
	}
	
	/**
	 * method to add the input String into history
	 * @param msg String to be added
	 */
	public void addToHistory(String msg){
		chatHistory.append(msg);
		chatHistory.append("\n");
	}
	/**
	 * This method send the history stored to the required OutputStream
	 * @param out
	 */
	public void sendHistory(PrintWriter out){
		out.println(chatHistory);
	}
	/**
	 * overrides toString() method in java.lang.Object 
	 */
	@Override
	public String toString() {
		return "ThreadInfo [inStream=" + inStream + ", outStream=" + outStream
				+ ", connection=" + connection + "]";
	}
	/**
	 * this method closes all the I/O streams and socket
	 */
	public void closeAll(){
		try {
			inStream.close();
			outStream.checkError();
			connection.close();
			System.out.println("all closed");
		} catch (IOException e) {
			System.out.println("Error while close the I/O stream or socket!");
			System.exit(1);
		}
		
	}
}
