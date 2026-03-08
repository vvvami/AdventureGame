package main;

import interactable.Interactable;
import util.KeyHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    // window settings
    public static final int originalTileSize = 16;
    public static final int scale = 3;
    public static final int tileSize = originalTileSize * scale;

    public static final int maxWindowColumns = 24; // 1152
    public static final int maxWindowRows = 16;     // 768
    public static final int windowWidth = tileSize * maxWindowColumns;
    public static final int windowHeight = tileSize * maxWindowRows;

    public static final KeyHandler keyHandler = new KeyHandler();

    // Camera
    private static final Point cameraPos = new Point(0, 0);
    private static Rectangle camBounds;

    // Backbuffer
    private final BufferedImage backBuffer =
            new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);

    // update fps
    public final int updateFPS = 60;

    // game thread
    private Thread gameThread;
    private volatile boolean running;

    // fps
    private int fpsDisplay = 0;

    // debug
    private static final Font UI_FONT = new Font("VCR OSD Mono", Font.BOLD, 24);
    private static final Stroke DEBUG_STROKE = new BasicStroke(2);

    // config
    public boolean debug = false;
    public int renderFPS = 60;
    public boolean fullscreen = false;
    private Config config = new Config(this);

    public Game() {
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);

        // Important for BufferStrategy rendering:
        setIgnoreRepaint(true);

        addKeyListener(keyHandler);
        setFocusable(true);
        requestFocus();
    }

    public void startGameThread() {
        if (running) return;
        running = true;
        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }

    @Override
    public void run() {
        createBufferStrategy(2);
        BufferStrategy bs = getBufferStrategy();

        final double step = 1.0 / updateFPS;

        long lastTime = System.nanoTime();
        double accumulator = 0.0;

        long fpsTimer = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            long elapsed = now - lastTime;
            lastTime = now;

            double dt = elapsed / 1_000_000_000.0;
            accumulator += dt;
            fpsTimer += elapsed;

            while (accumulator >= step) {
                updateGame(step);      // deltaTime in seconds
                accumulator -= step;
            }

            render(bs);
            frames++;

            if (fpsTimer >= 1_000_000_000L) {
                fpsDisplay = frames;
                frames = 0;
                fpsTimer = 0;
            }

             Thread.yield();
        }
    }

    private void updateGame(double dt) {
        Interactable.updateInteractablesInList();
    }

    private void render(BufferStrategy bs) {
        do {
            Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
            try {
                // clear screen
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // resizing with the window
                double sx = getWidth() / (double) windowWidth;
                double sy = getHeight() / (double) windowHeight;
                double scale = Math.min(sx, sy);

                int drawW = (int) Math.round(windowWidth * scale);
                int drawH = (int) Math.round(windowHeight * scale);
                int offsetX = (getWidth() - drawW) / 2;
                int offsetY = (getHeight() - drawH) / 2;

                // saving the transform
                AffineTransform oldTx = g2.getTransform();

                // transform
                g2.translate(offsetX, offsetY);
                g2.scale(scale, scale);
                g2.setClip(0, 0, windowWidth, windowHeight);

                // interactables
                g2.translate(-cameraPos.x, -cameraPos.y);
                Interactable.renderInteractablesInList(g2);

                // debug
                if (debug) {
                    g2.setStroke(DEBUG_STROKE);
                    for (Interactable interactable : Interactable.list()) {
                        if (interactable.collideable() && interactable.isRendering) {
                            g2.setColor(Color.white);
                            g2.draw(interactable.getCollider().getBox());

                            int spriteWidth = (int) interactable.getSprite().getWidth();
                            int spriteHeight = (int) interactable.getSprite().getHeight();

                            Rectangle rectangle = new Rectangle((int) interactable.getX() - (spriteWidth / 2),
                                    (int) interactable.getY() - (spriteHeight / 2),
                                    spriteWidth, spriteHeight);
                            g2.setColor(Color.yellow);
                            g2.draw(rectangle);
                        }
                    }
                }

                // transform to old
                g2.setTransform(oldTx);
                g2.setClip(null);

                // ui
                g2.setComposite(AlphaComposite.SrcOver);
                g2.setColor(Color.WHITE);
                g2.setFont(UI_FONT);
                g2.drawString("FPS: " + fpsDisplay, 10, 30);

            } finally {
                g2.dispose();
            }
            bs.show();
        } while (bs.contentsLost());
    }


    public static void createCamBounds(float playerX, float playerY) {
        Rectangle bounds = new Rectangle(cameraPos.x, cameraPos.y, windowWidth, windowHeight);
        bounds.grow(-tileSize * 6, -tileSize * 6);
        camBounds = bounds;

        int xOffsetMin = (int) playerX - bounds.x;
        if (xOffsetMin < 0) cameraPos.x += xOffsetMin;

        int xOffsetMax = (int) playerX - (bounds.x + bounds.width);
        if (xOffsetMax > 0) cameraPos.x += xOffsetMax;

        int yOffsetMin = (int) playerY - bounds.y;
        if (yOffsetMin < 0) cameraPos.y += yOffsetMin;

        int yOffsetMax = (int) playerY - (bounds.y + bounds.height);
        if (yOffsetMax > 0) cameraPos.y += yOffsetMax;
    }

    public static Point getCamPos() { return cameraPos; }
    public static Rectangle getCamBounds() { return camBounds; }

    public static Rectangle getViewport() {
        return new Rectangle(cameraPos.x, cameraPos.y, windowWidth, windowHeight);
    }

    public Config getConfig() { return config; }
}