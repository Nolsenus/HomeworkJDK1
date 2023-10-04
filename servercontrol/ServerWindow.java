package servercontrol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerWindow extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_POSX = 560;
    private final int WINDOW_POSY = 240;

    private final String MESSAGE_SERVER_START = "Server started.";
    private final String MESSAGE_SERVER_STOP = "Server stopped.";
    private final String MESSAGE_SERVER_ALREADY_STARTED = "Server already working.";
    private final String MESSAGE_SERVER_ALREADY_STOPPED = "Server already stopped.";

    private JTextArea outputArea;

    private boolean isServerWorking;

    public ServerWindow() {
        isServerWorking = false;
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server control");
        JPanel mainPane = new JPanel(new GridLayout(2,1));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane textPane = new JScrollPane(outputArea);
        textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPane.add(textPane);
        JPanel buttonPane = new JPanel(new GridLayout(1, 2));
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message;
                if (isServerWorking) {
                    message = MESSAGE_SERVER_ALREADY_STARTED;
                } else {
                    message = MESSAGE_SERVER_START;
                    isServerWorking = true;
                }
                sendLogMessage(message);
            }
        });
        buttonPane.add(startButton);
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message;
                if (isServerWorking) {
                    message = MESSAGE_SERVER_STOP;
                    isServerWorking = false;
                } else {
                    message = MESSAGE_SERVER_ALREADY_STOPPED;
                }
                sendLogMessage(message);
            }
        });
        buttonPane.add(stopButton);
        mainPane.add(buttonPane);
        add(mainPane);
        setVisible(true);
    }

    private void sendLogMessage(String message) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss.SSS"));
        outputArea.append(String.format("\n[%s] %s", dateTime, message));
    }
}
