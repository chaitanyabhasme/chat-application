package chat.driver;

import chat.client.ClientDriver;
import chat.server.ServerDriver;

public class MainDriver {

	/**
	 * This main method will call Client Driver or Server Driver
	 * depending on the input arguments passed
	 * @param args arguments for client or server Driver
	 */
	public static void main(String[] args) {
		int port_id;
		String ip_address;
		if(args.length == 1){
			port_id = Integer.parseInt(args[0]);
			if(port_id > 1023)
				ServerDriver.serverMain(port_id);
			else
				System.out.println("Invalid port id! Please enter port greater 1023!");
		}
		else if(args.length == 2){
			port_id = Integer.parseInt(args[1]);
			ip_address = args[0];
			if(port_id > 1023)
				ClientDriver.clientMain(ip_address, port_id);
			else
				System.out.println("Invalid port id! Please enter port greater 1023!");
		}
		else{
			System.out.println("Input arguments invalid.Usage client:<IP_ADDRESS> <PORT_ID> or Usage server:<PORT_ID>");
		}

	}

}
