package com.mycompany.snake_game_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyScreen extends JPanel {

    private final JFrame frame;

    public LobbyScreen(JFrame frame) {
        this.frame = frame;
        initializeLobbyScreen();
    }

    private void initializeLobbyScreen() {
        setLayout(new GridBagLayout());
        setBackground(Color.black);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some space between components

        // Title label (split into two lines)
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Color.black);

        JLabel titleLine1 = new JLabel("Snake Game", JLabel.CENTER);
        titleLine1.setFont(new Font("Arial", Font.BOLD, 40));  // Larger font size
        titleLine1.setForeground(Color.white);
        
        JLabel titleLine2 = new JLabel("by Chi Phuc Nguyen", JLabel.CENTER);
        titleLine2.setFont(new Font("Arial", Font.PLAIN, 20));  // Smaller font size
        titleLine2.setForeground(Color.white);

        titlePanel.add(titleLine1);
        titlePanel.add(titleLine2);

        // Position title in the middle of the screen
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titlePanel, gbc);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        // Play Button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        playButton.setBackground(new Color(50, 205, 50)); // Green color
        playButton.setForeground(Color.white);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the Snake game screen
                frame.getContentPane().removeAll();
                SnakeGameBoard gameBoard = new SnakeGameBoard();
                frame.add(gameBoard);
                frame.pack();  // Adjust the window size to fit the SnakeGameBoard
                frame.revalidate();
                frame.repaint();

                // Request focus so the game board can listen to key events
                gameBoard.requestFocusInWindow();
            }
        });
        buttonPanel.add(playButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setBackground(new Color(255, 69, 0)); // Red color
        exitButton.setForeground(Color.white);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application
            }
        });
        buttonPanel.add(exitButton);

        // Position the button panel below the title
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(buttonPanel, gbc);
    }
}
