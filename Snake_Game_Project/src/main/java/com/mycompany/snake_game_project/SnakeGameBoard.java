package com.mycompany.snake_game_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JButton;

public class SnakeGameBoard extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 400;
    private final int BOARD_HEIGHT = 400;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 400;
    private final int DELAY = 140;

    private final int xPosition[];
    private final int yPosition[];

    private int snakeSize;
    private int apple_x_pos;
    private int apple_y_pos;
    private int numApples = 0;

    private boolean right = true;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean gameOn = true;

    private Timer gameTimer;
    private Image ballImg;
    private Image headImg;
    private Image appleImg;

    public SnakeGameBoard() {
        this.yPosition = new int[ALL_DOTS];
        this.xPosition = new int[ALL_DOTS];
        initializeSnakeBoard();
        this.setFocusable(true);

    }

    private void initializeSnakeBoard() {
        addKeyListener(new UserClickAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH + 40, BOARD_HEIGHT + 80));
        loadAllImages();
        initializeGame();
    }

    private void loadAllImages() {
        ImageIcon ballIcon = new ImageIcon("src/resources/dot.png");
        ballImg = ballIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        headImg = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        appleImg = appleIcon.getImage();
    }

    private void initializeGame() {
        snakeSize = 3;
        numApples = 0;
        for (int p = 0; p < snakeSize; p++) {
            xPosition[p] = 50 - p * 10;
            yPosition[p] = 50;
        }
        locateNewApple();
        gameTimer = new Timer(DELAY, this);
        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
    if (gameOn) {
        // Draw the background grid of light and dark green squares
        for (int row = 0; row < BOARD_HEIGHT / DOT_SIZE; row++) {
            for (int col = 0; col < BOARD_WIDTH / DOT_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(new Color(105, 240, 105)); // Light green color
                } else {
                    g.setColor(new Color(87, 217, 34)); // Dark green color
                }
                g.fillRect(col * DOT_SIZE + 20, row * DOT_SIZE + 40, DOT_SIZE, DOT_SIZE); // Draw each square
            }
        }

        // Draw the border around the playground
        g.setColor(Color.white);
        g.drawRect(20, 40, BOARD_WIDTH, BOARD_HEIGHT);

        // Draw the apple
        g.drawImage(appleImg, apple_x_pos + 20, apple_y_pos + 40, this);

        // Draw the snake
        for (int p = 0; p < snakeSize; p++) {
            if (p == 0) {
                g.drawImage(headImg, xPosition[p] + 20, yPosition[p] + 40, this); // Draw the head
            } else {
                g.drawImage(ballImg, xPosition[p] + 20, yPosition[p] + 40, this); // Draw the body
            }
        }

        // Draw the number of apples eaten
        g.setFont(new Font("Times New Roman", Font.BOLD, 16));
        g.drawString("Apples Eaten: " + numApples, 30, 30);

        Toolkit.getDefaultToolkit().sync();
    } else {
        gameOver(g);
    }
}

private void gameOver(Graphics g) {
    g.setColor(Color.red);
    g.setFont(new Font("Times New Roman", Font.BOLD, 40));
    FontMetrics metrics = getFontMetrics(g.getFont());
    g.drawString("Game Over", (BOARD_WIDTH - metrics.stringWidth("Game Over")) / 2, BOARD_HEIGHT / 2);

    // Create "Play Again" button
    JButton playAgainButton = new JButton("Play Again");
    playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
    playAgainButton.setBounds((BOARD_WIDTH / 2) - 70, (BOARD_HEIGHT / 2) + 50, 140, 40);
    playAgainButton.addActionListener(e -> resetGame());

    // Create "Exit" button
    JButton exitButton = new JButton("Exit");
    exitButton.setFont(new Font("Arial", Font.BOLD, 20));
    exitButton.setBounds((BOARD_WIDTH / 2) - 70, (BOARD_HEIGHT / 2) + 100, 140, 40);
    exitButton.addActionListener(e -> System.exit(0));

    // Add buttons to the panel
    this.setLayout(null);
    this.add(playAgainButton);
    this.add(exitButton);

    // Refresh the panel to display the buttons
    revalidate();
    repaint();
}


public void resetGame() {
    // Remove all components (like buttons) from the panel
    this.removeAll();

    // Reinitialize the game variables
    initializeGame();
    
    // Reset direction to default (moving right)
    right = true;
    left = false;
    up = false;
    down = false;

    // Reset the game state
    gameOn = true;

    // Request focus for key events
    requestFocusInWindow();

    // Redraw the game board
    repaint();
}

    private void checkApple() {
        if ((xPosition[0] == apple_x_pos) && (yPosition[0] == apple_y_pos)) {
            snakeSize++;
            numApples++; // Increment apple counter
            adjustSpeed();
            locateNewApple();
        }
    }

    private void adjustSpeed() {
        int maxApples = 50; // Maximum apples for speed adjustment
        int minDelay = 50; // Minimum delay for the fastest speed

        // Gradually decrease delay every 5 apples, capped at maxApples
        if (numApples % 5 == 0 && numApples <= maxApples) {
            int newDelay = Math.max(minDelay, DELAY - (numApples / 5) * 10);
            gameTimer.setDelay(newDelay); // Update the timer delay
        }
    }

    private void moveSnake() {
        for (int p = snakeSize; p > 0; p--) {
            xPosition[p] = xPosition[(p - 1)];
            yPosition[p] = yPosition[(p - 1)];
        }
        if (left) {
            xPosition[0] -= DOT_SIZE;
        }
        if (right) {
            xPosition[0] += DOT_SIZE;
        }
        if (up) {
            yPosition[0] -= DOT_SIZE;
        }
        if (down) {
            yPosition[0] += DOT_SIZE;
        }
    }

    private void checkSnakeCollision() {
        for (int p = snakeSize; p > 0; p--) {
            if ((p > 4) && (xPosition[0] == xPosition[p]) && (yPosition[0] == yPosition[p])) {
                gameOn = false;
            }
        }
        if (yPosition[0] >= BOARD_HEIGHT) {
            gameOn = false;
        }
        if (yPosition[0] < 0) {
            gameOn = false;
        }
        if (xPosition[0] >= BOARD_WIDTH) {
            gameOn = false;
        }
        if (xPosition[0] < 0) {
            gameOn = false;
        }
        if (!gameOn) {
            gameTimer.stop();
        }
    }

    private void locateNewApple() {
        int random = (int) (Math.random() * (BOARD_WIDTH / DOT_SIZE)); // Random position within board width
        apple_x_pos = random * DOT_SIZE;

        random = (int) (Math.random() * (BOARD_HEIGHT / DOT_SIZE)); // Random position within board height
        apple_y_pos = random * DOT_SIZE;
    }

    private class UserClickAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
                right = false;
            }
            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
                left = false;
            }
            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
                down = false;
            }
            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
                up = false;
            }

        }
    }

    public void actionPerformed(ActionEvent e) {
        if (gameOn) {
            checkApple();
            checkSnakeCollision();
            moveSnake();
        }
        repaint();
    }
}
