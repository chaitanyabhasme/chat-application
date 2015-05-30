package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import chat.util.Worker;

/**
 * ClientDriver for client side connection.
 * Creates a socket for given port number and manages connection
 * through this socket
 *
 */
public class ClientDriver {
	
	public static void clientMain(String ip_address, int port_id){
		PrintWriter out = null;
		BufferedReader in = null;
		Socket clSocket = null;
		boolean check;
		try {
			clSocket = new Socket(ip_address,port_id);
			in = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
			out = new PrintWriter(clSocket.getOutputStream(),true);
		} catch (IOException e) {
			System.out.println("Unable to establish a connection!");
			System.exit(1);
		}
		BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
		Worker client = new ClientWorker();
		client.displayMenu();
		do
		{
			check = ((ClientWorker) client).selectChoice(sysIn,in,out);
		}while(check);
		try {
			in.close();
			out.close();
			clSocket.close();
			System.out.println("Client closed");
		} catch (IOException e) {
			System.out.println("Error while closing the ClientSocket!");
			System.exit(1);
		}
	}
}
