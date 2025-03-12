package main;

import entity.Interactable;
import entity.Player;
import util.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Screen settings
    private static final int originalTileSize = 16;
    private static final int scale = 3;

    // 48x48 Tile, we are scaling up each sprite by x3 while being 16x16
    private static final int tileSize = originalTileSize * scale;

    // Here we set the screen size based on columns and rows scaled by the tileSize
    private static final int maxScreenColumns = 16;
    private static final int maxScreenRows = 12;
    private static final int screenWidth = tileSize * maxScreenColumns;
    private static final int screenHeight = tileSize * maxScreenRows;

    private static GamePanel gamePanel;

    // Key input handler
    public static final KeyHandler keyHandler = new KeyHandler();

    Player player = new Player();



    // FPS
    final int FPS = 60;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    private Thread gameThread;
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Runs in a loop when the gameThread starts
    @Override
    public void run() {
        long currentTime;
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        int fpsCounter = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            // We are first updating game information, then we repaint the game to match the new info
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                fpsCounter++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + fpsCounter);
                fpsCounter = 0;
                timer = 0;
            }
        }
    }

    // Updates info
    public void update() {
        Interactable.updateInteractablesInList();
    }

    // Updates graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Interactable.renderInteractablesInList((Graphics2D) g);
    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }

    public static void setGamePanel(GamePanel panel) {
        gamePanel = panel;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getTileSize() {
        return tileSize;
    }
}
