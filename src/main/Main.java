package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        Game game = new Game();
        frame.dispose();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setForeground(Color.black);
        frame.add(game);
        frame.setLocationRelativeTo(null);

        // registering all game stuff
        Registry.register();

        // config
        File config = new File("config.txt");
        if (config.exists()) {
            game.getConfig().loadConfig();
        } else {
            game.getConfig().saveConfig();
        }

        if (game.fullscreen) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            DisplayMode dm = gd.getDisplayMode();
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocation(0, 0);
            frame.setSize(dm.getWidth(), dm.getHeight());
        } else {
            frame.setUndecorated(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
        frame.validate();
        frame.setVisible(true);

        game.requestFocus();
        game.startGameThread();
    }
}