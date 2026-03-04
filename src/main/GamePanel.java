package main;

import interactable.Interactable;
import util.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // window settings
    public static final int originalTileSize = 16;
    public static final int scale = 3;

    // 48x48 Tile, we are scaling up each sprite by x3 while being 16x16
    public static final int tileSize = originalTileSize * scale;

    // Here we set the window size based on columns and rows scaled by the tileSize
    public static final int maxWindowColumns = 24; // 1152
    public static final int maxWindowRows = 16; // 768
    public static final int windowWidth = tileSize * maxWindowColumns;
    public static final int windowHeight = tileSize * maxWindowRows;

    private static GamePanel gamePanel;

    // Key input handler
    public static final KeyHandler keyHandler = new KeyHandler();

    private static Point cameraPos = new Point(0,0);
    private static Rectangle camBounds;

    // FPS
    public final int FPS = 60;


    public GamePanel() {
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
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
//                System.out.println("FPS: " + fpsCounter);
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
        Graphics2D g2 = (Graphics2D) g.create();
        Interactable.renderInteractablesInList(g2);
        g2.dispose();

        Graphics2D ui = (Graphics2D) g.create();
        ui.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        ui.setColor(Color.white);
        ui.setFont(new Font("VCR OSD Mono", Font.BOLD, 24));
        ui.drawString("FPS: " + FPS, 10, 30);
        ui.dispose();
    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }

    public static void setGamePanel(GamePanel panel) {
        gamePanel = panel;
    }

    public static void createCamBounds(float playerX, float playerY) {
        Rectangle camBounds = new Rectangle(cameraPos.x, cameraPos.y,
                windowWidth, windowHeight);
        camBounds.grow(-tileSize*6, -tileSize*6);
        GamePanel.camBounds = camBounds;

        int xOffsetMin = (int) playerX - camBounds.x;
        if (xOffsetMin < 0) {cameraPos.x += xOffsetMin;}

        int xOffsetMax = (int) playerX - (camBounds.x + camBounds.width);
        if (xOffsetMax > 0) {cameraPos.x += xOffsetMax;}

        int yOffsetMin = (int) playerY - camBounds.y;
        if (yOffsetMin < 0) {cameraPos.y += yOffsetMin;}

        int yOffsetMax = (int) playerY - (camBounds.y + camBounds.height);
        if (yOffsetMax > 0) {cameraPos.y += yOffsetMax;}
    }

    public static Point getCamPos() {
        return cameraPos;
    }

    public static Rectangle getCamBounds() {
        return camBounds;
    }

    public static Rectangle getViewport() {
        return new Rectangle(cameraPos.x, cameraPos.y, windowWidth, windowHeight);
    }
}
