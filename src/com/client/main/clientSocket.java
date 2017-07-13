package com.client.main;

/*
  Dhenuka Bhargavi Rangam
  UNCC ID:800963261
  CNN Project1
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.net.Socket;

public class clientSocket implements Runnable {

	Socket clientSocket;
	String inputRequest = null;
	String outputResponse = null;
	String server_folder_path = "Files_on_server";

	public clientSocket(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			System.out.println("client has been accepted");
			newRequest();
		} catch (Exception e) {
			System.out.println("Exception is " + e);
		}
	}

	public void newRequest() {
		PrintStream outputStream = null;
		DataInputStream inputStream = null;

		try {

			inputStream = new DataInputStream(clientSocket.getInputStream());
			File serverfolder = new File(server_folder_path);
			if (serverfolder.exists()) {
				System.out.println("---------------");
			} else {
				serverfolder.mkdir();
			}
			inputRequest = inputStream.readLine();
			inputStream.readLine();
			inputStream.readLine();
			String fileRequested = inputRequest.split(" ")[1].split("/")[1];
			server_folder_path = server_folder_path + "/" + fileRequested; // Server
																			// Database
			File fileOnServer = new File(server_folder_path);
			if (inputRequest.contains("GET")) {
				if (fileOnServer.exists() && fileOnServer.isFile()) {
					outputResponse = "HTTP/1.1 200 OK\r\n" + "Server:localhost\r\n" + "\r\n";
					FileReader fread = new FileReader(server_folder_path);
					BufferedReader bread = new BufferedReader(fread);
					String buffer = null;
					while ((buffer = bread.readLine()) != null) {
						outputResponse += buffer + "\n";
					}
					bread.close();
					fread.close();
				} else {
					outputResponse = "HTTP/1.1 404 Not Found\r\n" + "Server:localhost\r\n" + "\r\n";
				}
			} else if (inputRequest.contains("PUT")) {
				File f = new File(server_folder_path);
				if (!f.exists()) {
					f.createNewFile();
				}
				String buffer = null;
				byte[] b = new byte[2048];
				FileOutputStream fos = new FileOutputStream(server_folder_path);

				int i = 0;
				i = inputStream.read(b);
				fos.write(b, 0, i);

				fos.close();
				System.out.println("Uploaded to the server");
				outputResponse = "HTTP/1.1 200 OK\r\n" + "Server:localhost\r\n" + "\r\n";

			} else {
				outputResponse = "HTTP/1.1 301 Bad Request\r\n" + "Server:localhost\r\n" + "\r\n";
			}
			outputStream = new PrintStream(clientSocket.getOutputStream());
			outputStream.println(outputResponse);
			Thread.sleep(1000);
			System.out
					.println("Client Connection: " + clientSocket.getInetAddress().getCanonicalHostName() + " closed");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
				inputStream.close();
				clientSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
