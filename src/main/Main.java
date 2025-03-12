package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        mainWindow.setTitle("AdventureGame");

        GamePanel.setGamePanel(new GamePanel());
        mainWindow.add(GamePanel.getGamePanel());

        mainWindow.pack();

        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);

        GamePanel.getGamePanel().startGameThread();
    }

}