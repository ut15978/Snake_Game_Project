package com.mycompany.snake_game_project;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import java.awt.EventQueue;
public class SnakeGameClass extends JFrame{
    
    public SnakeGameClass(){
      initializeUI();     
    }
    private void initializeUI(){
        setPreferredSize(new Dimension(440, 480));  // Adjust width/height as needed
        add(new LobbyScreen(this));
        setResizable(false);
        pack();
        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
        JFrame snakeFrame = new SnakeGameClass();
        snakeFrame.setVisible(true);
        });
    }
}
