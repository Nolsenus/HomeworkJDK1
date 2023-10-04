package chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConnectWindow extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_POSX = 560;
    private final int WINDOW_POSY = 240;

    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField serverAddressField;
    private JTextField serverPortField;

    public ConnectWindow() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Подключение к чату");
        JPanel mainPane = new JPanel(new GridLayout(3, 1));
        JPanel loginPane = new JPanel(new GridLayout(1, 2));
        JPanel serverPane = new JPanel(new GridLayout(1 ,2));
        JPanel usernamePane = new JPanel(new GridLayout(2, 1));
        JPanel passwordPane = new JPanel(new GridLayout(2, 1));
        JPanel serverAddressPane = new JPanel(new GridLayout(2, 1));
        JPanel serverPortPane = new JPanel(new GridLayout(2, 1));
        JLabel usernameLabel = new JLabel("Логин:");
        usernamePane.add(usernameLabel);
        usernameField = new JTextField();
        usernamePane.add(usernameField);
        loginPane.add(usernamePane);
        JLabel passwordLabel = new JLabel("Пароль:");
        passwordPane.add(passwordLabel);
        passwordField = new JTextField();
        passwordPane.add(passwordField);
        loginPane.add(passwordPane);
        mainPane.add(loginPane);
        JLabel serverAddressLabel = new JLabel("Адрес сервера:");
        serverAddressPane.add(serverAddressLabel);
        serverAddressField = new JTextField();
        serverAddressPane.add(serverAddressField);
        serverPane.add(serverAddressPane);
        JLabel serverPortLabel = new JLabel("Порт подключения:");
        serverPortPane.add(serverPortLabel);
        serverPortField = new JTextField();
        serverPortPane.add(serverPortField);
        serverPane.add(serverPortPane);
        mainPane.add(serverPane);
        JButton connectButton = new JButton("Подключиться");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });
        mainPane.add(connectButton);
        add(mainPane);

        setVisible(true);
    }

    private void connect() {
        setVisible(false);
        String serverAndPort = String.format("%s-%s", serverAddressField.getText(), serverPortField.getText());
        String filename = String.format("%s.txt", serverAndPort);
        System.out.println(filename);
        File chatLog = new File("src/chat" , filename);
        try {
            if (chatLog.createNewFile()) {
                FileWriter fw = new FileWriter(chatLog, true);
                BufferedWriter bw = new BufferedWriter(fw);
                String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                bw.append(String.format("%s - %s (%s)", dateTime, "Создан чат", serverAndPort));
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder chatHistory = new StringBuilder();
        try {
            Scanner chatIn = new Scanner(chatLog);
            while (chatIn.hasNextLine()) {
                chatHistory.append(chatIn.nextLine());
                chatHistory.append('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new ChatWindow(usernameField.getText(), serverAndPort, chatHistory.toString());
    }
}
