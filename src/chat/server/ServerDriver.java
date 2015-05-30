package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import chat.util.ThreadPool;
/**
 * ServerDriver class for server side
 * waits for client to connect and spawn thread for each client connect
 *
 */
public class ServerDriver {
	
	public static void serverMain(int port_id){
		
		ServerSocket newServer = null;
		try{
			newServer = new ServerSocket(port_id);
			int i=1;
			while(true){
				Socket newClient = newServer.accept();
				ServerHandler newHandler = new ServerHandler(newClient);
				ThreadPool threadPool = ThreadPool.getInstance();
				Thread newThread = threadPool.borrowThread();
				newThread = new Thread(newHandler,Integer.toString(i));
				newThread.start();
				i++;
			}
		}catch(IOException e){
			System.out.println("Cannot listen to the port:" + port_id);
			System.exit(1);
		}finally{
				try {
					newServer.close();
				} catch (IOException e) {
					System.out.println("Error while closing the ServerSocket");
					System.exit(1);
				}	
			}
		}

}
