package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int WINDOW_POSX = 560;
    private final int WINDOW_POSY = 240;

    private JButton startBtn = new JButton("New game");
    private JButton exitBtn = new JButton("Exit");
    private Field field;
    private SettingsWindow settings;
    public GameWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("TicTacToe");
        setResizable(false);
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(startBtn);
        bottomPanel.add(exitBtn);
        add(bottomPanel, BorderLayout.SOUTH);
        field = new Field();
        add(field);
        settings = new SettingsWindow(this);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setVisible(true);
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public void startNewGame(int mode, int width, int height, int winLength) {
        field.startNewGame(mode, width, height, winLength);
    }
}
