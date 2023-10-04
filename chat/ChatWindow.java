package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatWindow extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_POSX = 560;
    private final int WINDOW_POSY = 240;

    private JTextArea chatArea;
    private JScrollPane chatPane;
    private String username;
    private String serverAndPort;

    public ChatWindow(String username, String serverAndPort, String chatHistory) {
        this.username = username;
        this.serverAndPort = serverAndPort;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setTitle(String.format("Чат (%s)", serverAndPort));
        JPanel mainPane = new JPanel(new GridLayout(4, 1));
        chatArea = new JTextArea(chatHistory);
        chatArea.setEditable(false);
        chatArea.setAutoscrolls(true);
        chatPane = new JScrollPane(chatArea);
        chatPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPane.add(chatPane);
        JLabel inputLabel = new JLabel("Введите сообщение: ");
        mainPane.add(inputLabel);
        JTextField inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });
        mainPane.add(inputField);
        JButton sendButton = new JButton("Отправить сообщение");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });
        mainPane.add(sendButton);
        add(mainPane);
        setVisible(true);
    }

    private void sendMessage(String message) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String toSend = String.format("[%s] %s: %s\n", dateTime, username, message);
        chatArea.append(toSend);
        chatPane.getVerticalScrollBar().setValue(chatPane.getVerticalScrollBar().getMaximum());
        logMessage(toSend);
    }

    private void logMessage(String message) {
        try {
            File chatLog = new File("src/chat", String.format("%s.txt", serverAndPort));
            FileWriter fw = new FileWriter(chatLog, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(message);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
