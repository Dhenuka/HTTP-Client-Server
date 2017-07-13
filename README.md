# HTTP-Client-Server
CCN Project 1
HTTP Server and Client
Dhenuka Bhargavi Rangam
UNCC ID: 800963261
This project implements HTTP Get and Put methods using
Programming Language: JAVA
JDK Version: 8
IDE: Eclipse
Program Flow:
Client Side:
File Name: MyClient.java
Exceptions Handled using try and catch
Error conditions: Not providing four arguments in the specified order, retrieving file not present on server.
Client provides command line arguments (hostname/ server Name, port number of server, Command(GET), file name)

MyClient.java takes arguments and creates a socket which connects to specified server port Number on the named host

MyClient.java creates an output stream (Used to write to the server), input stream (Used to read from the server)			
	      GET			   					                   
GET request is created using filename 				  
and hostname of the server and sent 				   
using output stream i.e PrintStream				     
The file received is read using input Stream  
i.e DataInputStream								                               
								                               
PUT
PUT request is created using filename
and hostname of the server and a client
file is read using Buffered reader and 
written on output stream using 
Print stream so that server can read it

Server Side:
Files : MyServer.java,clientSocket.java
Exceptions Handled using try and catch
Error conditions: Not providing one argument i.e port number for the server,File not present on server(returns 404 not found response)
Closing of server is handled by closeServer method in MyServer.java
MyServer.java takes one argument i.e port number on which you want to host the server.
Server keeps accepting requests until it is closed using a while loop. A new Socket is created for every new client request and the request is re-directed to the new socket.
A new thread is created for every new request. clientSocket.java class implements runnable interface which has a run method which executes every time a new thread is created and handles GET and PUT requests. 
A folder is created where server stores all the files uploaded by clients and other server files.(Files_on_server).clientSocket.java opens an inputstream to read from the client and an output stream to write to the client. 
GET									                                        PUT	
Checks if the file exists on the server folder and 			    Puts the file on the server and sends 
returns the file and 200 OK message .			            	    200 OK if successfully saved.	
Steps:
Step 1: Compiling all the files using javac MyClient.java,javac MyclientSocket.java,javac MyServer.java
Step 2: Starting the server:
 		java MyServer portNumber(on which you want to start the server)
		Ex: java MyServer 12000
3.Get request sent by client for file “index.html”  present on server’s folder
4.Putting a file testServer.txt on the server
5.Closing the server by pressing ctrl+c
 






