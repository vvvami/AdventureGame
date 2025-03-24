package main;

import render.Sprite;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.setTitle("AdventureGame");
        mainWindow.setForeground(Color.black);

        GamePanel.setGamePanel(new GamePanel());
        mainWindow.add(GamePanel.getGamePanel());

        mainWindow.pack();

        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);

        Registry.register();

        GamePanel.getGamePanel().startGameThread();
    }

}