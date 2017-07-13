/*MyServer class takes portNumber as argument to start the server at this port.
 * It always accepts requests from Clients(GET/PUT).As server gets a new request it
 * creates a new port and redirects its request to this port.The request runs as a new thread.
 * ClientSocket is the class which implements runnable interface.Using this class a new thread is 
 * created.When ctrl+c is pressed  "closeserver" method gets executed which closes the server
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	public static void main(String[] args) {
		// Creating a port number for server
		ServerSocket myserver = null;
		DataInputStream input = null;
		PrintStream output = null;
		Socket serviceSocket = null;
		int portNum = 0;

		try {
			if (args.length == 1) {
				portNum = Integer.parseInt(args[0]);
				myserver = new ServerSocket(portNum);
				closeServer(myserver);
				System.out.println("Server started");
				// Accepting all the requests for the server port number and
				// each time it creates a new client socket
				while (true) {
					serviceSocket = myserver.accept();
					Thread thread = new Thread(new clientSocket(serviceSocket));
					thread.start();
				}
			} else {
				System.out.println("Please enter port number of the server");
				return;
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	private static void closeServer(ServerSocket serverSocket) {
		final ServerSocket serverSock = serverSocket;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {

					if (!serverSock.isClosed()) {
						serverSock.close();
					}

					System.out.println("The server is closed!!!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
