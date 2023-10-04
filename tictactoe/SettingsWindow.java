package tictactoe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow  extends JFrame {
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 300;
    private final int MIN_SIZE = 3;
    private final int MAX_SIZE = 10;

    private JButton startBtn = new JButton("Start new game");
    private JPanel mainPane = new JPanel(new GridLayout(9, 1));

    public SettingsWindow(GameWindow gw) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(gw);
        JLabel gameModeLabel = new JLabel("Выберите режим игры:");
        mainPane.add(gameModeLabel);
        JRadioButton pvpModeButton = new JRadioButton("Человек против человека");
        JRadioButton pveModeButton = new JRadioButton("Человек против компьютера");
        ButtonGroup gameModeSelect = new ButtonGroup();
        gameModeSelect.add(pvpModeButton);
        gameModeSelect.add(pveModeButton);
        JPanel gameModePanel = new JPanel(new GridLayout(1, 2));
        gameModePanel.add(pvpModeButton);
        gameModePanel.add(pveModeButton);
        mainPane.add(gameModePanel);
        JLabel chooseSizeLabel = new JLabel("Выберите размеры поля");
        mainPane.add(chooseSizeLabel);
        JLabel selectedSizeLabel = new JLabel("Установленный размер поля: " + MIN_SIZE);
        mainPane.add(selectedSizeLabel);
        JSlider sizeSlider = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);
        sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                selectedSizeLabel.setText("Установленный размер поля: " + sizeSlider.getValue());
            }
        });
        mainPane.add(sizeSlider);
        JLabel chooseWinLengthLabel = new JLabel("Выберите длину для победы");
        mainPane.add(chooseWinLengthLabel);
        JLabel selectedWinLengthLabel = new JLabel("Установленная длина для победы: " + MIN_SIZE);
        mainPane.add(selectedWinLengthLabel);
        JSlider winLengthSlider = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);
        winLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                selectedWinLengthLabel.setText("Установленная длина для победы: " + winLengthSlider.getValue());
            }
        });
        mainPane.add(winLengthSlider);
        mainPane.add(startBtn);
        add(mainPane);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mode;
                if (pvpModeButton.isSelected()) mode = 1;
                else mode = 0;
                gw.startNewGame(mode, sizeSlider.getValue(), sizeSlider.getValue(), winLengthSlider.getValue());
                setVisible(false);
            }
        });
    }
}
