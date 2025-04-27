import java.awt.*;
import java.io.*;
import java.net.*; 
import javax.swing.*;

public class ChatServer extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter out;
    private Socket socket;

    public ChatServer() {
        setTitle("Chat Server");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        inputField.addActionListener(_ -> {
            String message = inputField.getText();
            if (out != null) {
                out.println(message);
                chatArea.append("You: " + message + "\n");

                // Server ends chat too
                if (message.equalsIgnoreCase("exit")) {
                    closeChat();
                }
            }
            inputField.setText("");
        });

        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
        setVisible(true);

        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(12345)) {
                chatArea.append("Server started. Waiting for client...\n");
                socket = serverSocket.accept();
                chatArea.append("Client connected.\n");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    chatArea.append("Client: " + line + "\n");

                    // If client says exit
                    if (line.equalsIgnoreCase("exit")) {
                        chatArea.append("Chat ended by client.\n");
                        closeChat();
                        break;
                    }
                }

            } catch (IOException e) {
                chatArea.append("Connection closed.\n");
            }
        }).start();
    }

    private void closeChat() {
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Chat ended.");
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatServer::new);
    }
}
