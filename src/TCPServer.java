import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class TCPServer implements Runnable {

	public TCPServer() {
		super();

	}

	@Override
	public void run() {
		try {
			System.setProperty("javax.net.ssl.keyStore", "../files/server.keys");
			System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			System.setProperty("javax.net.ssl.trustStore", "../files/truststore");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");
			
			//Create server socket
			SSLServerSocket s = null;  
			SSLServerSocketFactory ssf = null;  
			 
			ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			
			try {  
			    s = (SSLServerSocket) ssf.createServerSocket(6458);  
			}  
			catch( IOException e) {  
			    System.out.println("Server - Failed to create SSLServerSocket");  
			    e.getMessage();  
			    return;  
			} 
			
			// Require client authentication  
			s.setNeedClientAuth(true);
			
			SSLSocket socket;

			while(true){	
				socket = (SSLSocket) s.accept();
				System.out.println("Accepted new Client.");

				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));	

				Server.addClient(socket);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
