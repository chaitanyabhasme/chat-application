package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import chat.util.Worker;

/**
 * ServerHandler class implements Runnable
 * this class must implement run() method
 * handlers communication between server and particular client
 *
 */
public class ServerHandler implements Runnable{
	
	private Socket socket;
	private volatile static HashMap<String, ThreadInfo> threadList = null;
	
	/**
	 * Constructor for ServerHandler. Creates a Hashmap 
	 * which is shared between all the threads.
	 * @param socketIn socket connection to be handled
	 */
	public ServerHandler(Socket socketIn){
		socket = socketIn;
		if(threadList == null){
			synchronized(ServerHandler.class){
				if(threadList == null){
					threadList = new HashMap<String, ThreadInfo>();
				}
			}
		}
	}

	@Override
	public void run(){
		ThreadInfo newInfo = new ThreadInfo(socket);
		threadList.put(Thread.currentThread().getName(), newInfo);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			selectChoice(in);
		}
	}
	
	/**
	 * This method selects a input choice from the end user
	 * and call required methods for each choice entered 
	 * @param in InputStream from which the choice is to be read
	 */
	public void selectChoice(BufferedReader in){
		String clientID=null,msg=null;
		int choice;
		ThreadInfo tempInfo = null;
		synchronized(ServerHandler.class){
			Worker serverMenu = new ServerWorker();
			serverMenu.displayMenu();
			System.out.println("Select your choice:");
			try {
				choice = Integer.parseInt(in.readLine());
				switch(choice){
				case 1:
					System.out.println("Enter client ID:");
					clientID = in.readLine();
					tempInfo = threadList.get(clientID);
					if(tempInfo != null){
						msg = serverMenu.sendMessage((threadList.get(clientID)).getOutStream());
					}
					else{
						System.out.println("Invalid clientID");
					}
					if(msg != null){
						tempInfo.addToHistory(msg);
					}
					break;
					
				case 2:
					System.out.println("Enter client ID:");
					clientID = in.readLine();
					tempInfo = threadList.get(clientID);
					if(tempInfo != null){
						msg = serverMenu.receiveMessage((threadList.get(clientID)).getInStream());
					}
					else{
						System.out.println("Invalid clientID");
					}
					if(msg != null){
						if(msg.equals("BACKUP")){
							tempInfo.sendHistory((threadList.get(clientID)).getOutStream());
						}
						else{
							System.out.println(msg);
							tempInfo.addToHistory(msg);
						}
					}
					else{
						System.out.println("Client has exited");
					}
					break;
					
				case 3:
					socket.close();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid Choice");
						
				}
		
			} catch (IOException e) {
				System.out.println("Unable to read from input!");
				System.exit(1);
			}
		}
	}

	/**
	 * overrides toString() method in java.lang.Object
	 */
	@Override
	public String toString() {
		return "ServerHandler [socket=" + socket + "]";
	}
}
