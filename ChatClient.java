import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private PrintWriter out;
    private Socket socket;

    public ChatClient() {
        setTitle("Chat Client");
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

                // Client ends chat
                if (message.equalsIgnoreCase("exit")) {
                    closeChat();
                }
            }
            inputField.setText("");
        });

        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
        setVisible(true);

        connectToServer();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 12345);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    chatArea.append("Server: " + line + "\n");

                    // If server ends chat
                    if (line.equalsIgnoreCase("exit")) {
                        chatArea.append("Chat ended by server.\n");
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
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException ignored) {}

        // Ensure the chat window closes
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Chat ended.");
            dispose();  // Close the JFrame
            System.exit(0);  // Exit the application
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient::new);
    }
}
