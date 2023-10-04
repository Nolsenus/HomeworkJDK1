package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Field extends JPanel {
    private int fieldWidth;
    private int fieldHeight;
    private char[][] field;
    private int winLength;
    private int gameMode;
    private int currentPlayer;
    private final int PLAYER_ONE = 0;
    private final int PLAYER_TWO = 1;
    public static final int PVP_MODE = 1;
    public static final int PVE_MODE = 0;
    private final int EMPTY_CELL = 0;
    private final int PLAYER_ONE_CELL = 1;
    private final int PLAYER_TWO_CELL = 2;

    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_PLAYER_ONE_WIN = 1;
    private static final int STATE_PLAYER_TWO_WIN = 2;
    private boolean isGameOver;

    private static final String MESSAGE_DRAW = "Ничья!";
    private static final String MESSAGE_PLAYER_WIN = "Победил игрок!";
    private static final String MESSAGE_AI_WIN = "Победил компьютер!";
    private static final String MESSAGE_PLAYER_ONE_WIN = "Победил первый игрок!";
    private static final String MESSAGE_PLAYER_TWO_WIN = "Победил второй игрок!";

    private static final Random RANDOM = new Random();
    private int cellWidth;
    private int cellHeight;

    public Field() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
        fieldWidth = 3;
        fieldHeight = 3;
        initField(3, 3);
        winLength = 3;
        isGameOver = false;
    }

    private void initField(int width, int height) {
        field = new char[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = EMPTY_CELL;
            }
        }
        isGameOver = false;
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldWidth && y >= 0 && y < fieldHeight;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[x][y] == EMPTY_CELL;
    }

    private void aiTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldWidth);
            y = RANDOM.nextInt(fieldHeight);
        } while (!isEmptyCell(x, y));
        field[x][y] = PLAYER_TWO_CELL;
    }

    private boolean checkWin(int c) {
        int streak, altStreak;
        for (int i = 0; i < fieldHeight; i++) {
            streak = 0;
            altStreak = 0;
            for (int j = 0; j < fieldWidth; j++) {
                if (field[j][i] == c) {
                    streak++;
                    if (streak == winLength) {
                        return true;
                    }
                } else {
                    streak = 0;
                }
                if (field[i][j] == c) {
                    altStreak++;
                    if (altStreak == winLength) {
                        return true;
                    }
                } else {
                    altStreak = 0;
                }
            }
        }
        for (int i = 0; i < fieldHeight - winLength + 1; i++) {
            for (int j = 0; j < fieldWidth - winLength + 1; j++) {
                if (field[j][i] == c) {
                    streak = 1;
                    for (int k = 1; k < winLength; k++) {
                        if (field[j + k][i + k] == c) {
                            streak++;
                        } else {
                            break;
                        }
                    }
                    if (streak == winLength) {
                        return true;
                    }
                }
            }
        }
        for (int i = 0; i < fieldHeight - winLength + 1; i++) {
            for (int j = winLength - 1; j < fieldWidth; j++) {
                if (field[j][i] == c) {
                    streak = 1;
                    for (int k = 1; k < winLength; k++) {
                        if (field[j - k][i + k] == c) {
                            streak++;
                        } else {
                            break;
                        }
                    }
                    if (streak == winLength) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isFieldFull() {
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                if (field[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkGameEnd(int cellType) {
        if (checkWin(cellType)) {
            gameOverType = cellType == PLAYER_ONE_CELL ? STATE_PLAYER_ONE_WIN : STATE_PLAYER_TWO_WIN;
            return true;
        }
        if (isFieldFull()) {
            gameOverType = STATE_DRAW;
            return true;
        }
        return false;
    }

    private void update(MouseEvent e) {
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        System.out.println(cellX + " : " + cellY);
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY) || isGameOver) return;
        char currentChar = (char) (currentPlayer == PLAYER_ONE ? PLAYER_ONE_CELL : PLAYER_TWO_CELL);
        field[cellX][cellY] = currentChar;
        repaint();
        if (checkGameEnd(currentChar)) {
            isGameOver = true;
            return;
        }
        if (gameMode == PVE_MODE) {
            aiTurn();
            repaint();
            if (checkGameEnd(PLAYER_TWO_CELL)) isGameOver = true;
        } else {
            currentPlayer = currentPlayer == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;
        }
    }

    public void startNewGame(int mode, int width, int height, int winLength) {
        System.out.printf("Game start (Mode: %d, Width: %d, Height: %d, Win Length: %d).\n",
                mode, width, height, winLength);
        this.fieldWidth = width;
        this.fieldHeight = height;
        this.winLength = winLength;
        this.gameMode = mode;
        currentPlayer = PLAYER_ONE;
        initField(width, height);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        g.setColor(Color.BLACK);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        cellWidth = panelWidth / fieldWidth;
        cellHeight = panelHeight / fieldHeight;
        for (int i = 1; i < fieldWidth; i++) {
            g.drawLine(cellWidth * i, 0, cellWidth * i, panelHeight);
        }
        for (int i = 1; i < fieldHeight; i++) {
            g.drawLine(0, cellHeight * i, panelWidth, cellHeight * i);
        }
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                if (field[i][j] == EMPTY_CELL) continue;
                if (field[i][j] == PLAYER_ONE_CELL) {
                    g.drawLine(i * cellWidth, j * cellHeight, (i + 1) * cellWidth, (j + 1) * cellHeight);
                    g.drawLine(i * cellWidth, (j + 1) * cellHeight, (i + 1) * cellWidth, j * cellHeight);
                } else if (field[i][j] == PLAYER_TWO_CELL) {
                    g.drawOval(i * cellWidth, j * cellHeight, cellWidth, cellHeight);
                } else {
                    throw new RuntimeException(
                            "Unexpected value in field.(" + field[i][j] + " at x = " + i + "; y = " + j);
                }
            }
        }
        if (isGameOver) showGameOverMessage(g);
    }

    private void showGameOverMessage(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        switch(gameOverType) {
            case STATE_DRAW:
                g.drawString(MESSAGE_DRAW, 320, getHeight() / 2); break;
            case STATE_PLAYER_ONE_WIN:
                g.drawString(gameMode == PVE_MODE ? MESSAGE_PLAYER_WIN : MESSAGE_PLAYER_ONE_WIN, 240, getHeight() / 2); break;
            case STATE_PLAYER_TWO_WIN:
                g.drawString(gameMode == PVE_MODE ? MESSAGE_AI_WIN : MESSAGE_PLAYER_TWO_WIN, 180, getHeight() / 2); break;
            default:
                throw new RuntimeException("Unexpected gameOverType: " + gameOverType);
        }
    }
}
