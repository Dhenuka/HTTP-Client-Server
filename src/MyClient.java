

/*MyClient Class is used to get a file from the server and to upload a file 
 * to the server.It takes four command line arguments hostname of the server,
 * portnumber of the server,command(GET/PUT),and file path.It displays the 
 * response from the server.When Getting a file response is 200 ok if file exists.
 * while putting a file response is 200 OK if the file is uploaded successfully.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class MyClient {

	String responseLine;

	public static void main(String[] args) {

		try {

			if (args.length == 4) {
				MyClient client = new MyClient();
				if (args[2].equals("GET")) {
					client.getRequest(args);
				} else if (args[2].equals("PUT")) {
					client.putRequest(args);
				}
			} else {
				System.out.println("Enter 4 arguments hostname,port,command,filepath respectively");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getRequest(String[] args) {

		DataInputStream input = null;
		PrintStream output = null;
		Socket MyClient = null;

		try {
			MyClient = new Socket(args[0], Integer.parseInt(args[1]));
			System.out.println("Connected to the server");
			input = new DataInputStream(MyClient.getInputStream());
			output = new PrintStream(MyClient.getOutputStream());
			output.print("GET /" + args[3] + "/ HTTP/1.1\r\n");
			output.print("Host: " + args[0] + "\r\n");
			output.print("\r\n");
			System.out.println("Sent the GET request");
			System.out.println("Receiving data from the server ");
			while ((responseLine = input.readLine()) != null) {
				System.out.println(responseLine);

			}
		} catch (IOException e) {
			System.out.println(e + "datastream exception");
		}

		finally {
			try {
				input.close();
				MyClient.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	public void putRequest(String[] args) {
		PrintStream output = null;
		DataInputStream dis = null;

		Socket MyClient = null;
		String response = null;

		try {

			MyClient = new Socket(args[0], Integer.parseInt(args[1]));
			System.out.println("Connected to the server");
			dis = new DataInputStream(MyClient.getInputStream());
			FileReader fread = new FileReader(args[3]);
			BufferedReader bread = new BufferedReader(fread);
			output = new PrintStream(MyClient.getOutputStream());
			output.print("PUT /" + args[3] + "/ HTTP/1.1\r\n");
			output.print("Host: " + args[0] + "\r\n");
			output.print("\r\n");
			System.out.println("PUT request sent");
			System.out.println("Uploading data to the server");
			while ((responseLine = bread.readLine()) != null) {

				output.print(responseLine);

			}

			System.out.println("Receiving response from the server");
			while ((response = dis.readLine()) != null) {
				System.out.println(response);

			}
		} catch (IOException e) {
			System.out.println(e + "socket exception");
		}

		finally {
			try {
				output.close();
				MyClient.close();
			} catch (IOException e) {
				System.out.println(e);
			}

		}
	}
}
