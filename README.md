This project was for university which was made by us in the 4th semester.

Simple Java Chat Application (Server and Client)
This project is a basic chat application written in Java using Sockets and Swing GUI. It includes a server and a client that can send messages to each other in real time over a local network (localhost).
Features
1) Simple text-based chat
2) Graphical User Interface (GUI) using JFrame, JTextArea, and JTextField
3) Server waits for a client connection
4) Client connects to the server
5) Either side can terminate the chat by typing exit
6) Graceful shutdown and UI notifications when chat ends

How it Works
1) Server starts and listens on port 12345.
2) Client connects to localhost on port 12345.
3) Messages are exchanged through sockets.
4) "exit" command from either side closes the connection and exits the program.

Requirements
Java JDK 8 or higher

How to Run
1. Compile the Java files
   javac ChatServer.java
   javac ChatClient.java
2. Start the Server
   java ChatServer
The server window will open and wait for a client to connect.

3. Start the Client
In another terminal window or your IDE:
   java ChatClient
The client window will open and connect to the server.

Here are some sample Screenshots on how the code should work and how the output should be looking: -
![WhatsApp Image 2025-04-23 at 15 46 15_e86ff096](https://github.com/user-attachments/assets/0a6e9d21-0c88-48c4-a755-de298b0f4f29)
![WhatsApp Image 2025-04-23 at 15 46 15_2143399b](https://github.com/user-attachments/assets/ee126b49-232e-44fb-8739-dc1d9f6e947b)

