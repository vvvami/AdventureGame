package main;

import interactable.Interactable;
import interactable.entity.Player;
import render.light.Light;
import util.AABB;
import util.KeyHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
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

    // light
    private static final double LIGHT_SCALE = 0.125;
    private static final int AMBIENT_ALPHA = 210;
    private BufferedImage lightingBuffer;
    private int lightingW = -1;
    private int lightingH = -1;
    private final Light playerLight = new Light(0, 0, 140, 0.5f, Color.red);

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
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
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
                AffineTransform viewportTx = g2.getTransform();

                // interactables
                g2.translate(-cameraPos.x, -cameraPos.y);
                Interactable.renderInteractablesInList(g2);

                g2.setTransform(viewportTx);

                playerLight.x = Player.list().getFirst().getX();
                playerLight.y = Player.list().getFirst().getY();
                renderLightingBuffer(windowWidth, windowHeight);
                g2.drawImage(lightingBuffer, 0, 0, windowWidth, windowHeight, null);

                // transform to old
                g2.setTransform(oldTx);
                g2.setClip(null);
                g2.setComposite(AlphaComposite.SrcOver);

                // debug
                if (debug) {
                    g2.setStroke(DEBUG_STROKE);
                    for (Interactable interactable : Interactable.list()) {
                        if (interactable.isRendering && interactable.getCollider() != null) {
                            g2.setColor(Color.white);
                            g2.draw(interactable.getCollider().getBox().toRect());

                            float spriteWidth = interactable.getSprite().getWidth();
                            float spriteHeight = interactable.getSprite().getHeight();

                            AABB rectangle = new AABB(interactable.getX() - (spriteWidth / 2),
                                    interactable.getY() - (spriteHeight / 2),
                                    spriteWidth, spriteHeight);
                            g2.setColor(Color.blue);
                            g2.draw(rectangle.toRect());

                            if (interactable.futColliderDebug != null) {
                                g2.setColor(Color.red);
                                g2.draw(interactable.futColliderDebug);
                            }

                            if (interactable.intersectDebug != null) {
                                g2.setColor(Color.green);
                                g2.draw(interactable.intersectDebug);
                            }
                        }
                    }
                }
                // ui
                g2.setColor(Color.WHITE);
                g2.setFont(UI_FONT);
                g2.drawString("FPS: " + fpsDisplay, 10, 30);
                g2.drawString("X: " + Registry.player.getX(), 10, 60);
                g2.drawString("Y: " + Registry.player.getY(), 10, 90);

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

    // lighting
    private void ensureLightingBuffer(int viewW, int viewH) {
        int lw = Math.max(1, (int)Math.ceil(viewW * LIGHT_SCALE));
        int lh = Math.max(1, (int)Math.ceil(viewH * LIGHT_SCALE));

        if (lightingBuffer == null || lw != lightingW || lh != lightingH) {
            lightingBuffer = new BufferedImage(lw, lh, BufferedImage.TYPE_INT_ARGB);
            lightingW = lw;
            lightingH = lh;
        }
    }

    private void renderLightingBuffer(int viewW, int viewH) {
        ensureLightingBuffer(viewW, viewH);

        Graphics2D lg = lightingBuffer.createGraphics();
        try {
            lg.setComposite(AlphaComposite.Src);
            lg.setColor(new Color(8, 12, 24, AMBIENT_ALPHA));
            lg.fillRect(0, 0, lightingW, lightingH);

            for (Light light : Light.list()) {
                if (light.enabled) {
                    drawLight(lg, light, viewW, viewH);
                }
            }

            if (playerLight.enabled) {
                drawLight(lg, playerLight, viewW, viewH);
            }

        } finally {
            lg.dispose();
        }
    }

    private void drawLight(Graphics2D lg, Light light, int viewW, int viewH) {
        double vx = light.x - cameraPos.x;
        double vy = light.y - cameraPos.y;

        // cull in viewport space
        if (vx + light.radius < 0 ||
                vy + light.radius < 0 ||
                vx - light.radius > viewW ||
                vy - light.radius > viewH) {
            return;
        }

        float cx = (float)(vx * LIGHT_SCALE);
        float cy = (float)(vy * LIGHT_SCALE);

        float holeR = Math.max(1f, (float)(light.radius * LIGHT_SCALE));
        float glowR = holeR * 1.25f;

        // 1) cut a hole in darkness
        lg.setComposite(AlphaComposite.DstOut);
        lg.setPaint(new RadialGradientPaint(
                new Point2D.Float(cx, cy),
                holeR,
                new float[] {0f, 0.5f, 1f},
                new Color[] {
                        new Color(0, 0, 0, (int)(255 * light.strength)),
                        new Color(0, 0, 0, (int)(160 * light.strength)),
                        new Color(0, 0, 0, 0)
                }
        ));
        lg.fill(new Ellipse2D.Float(cx - holeR, cy - holeR, holeR * 2f, holeR * 2f));

        // 2) add colored glow
        Color c = light.color;
        lg.setComposite(AlphaComposite.SrcOver);
        lg.setPaint(new RadialGradientPaint(
                new Point2D.Float(cx, cy),
                glowR,
                new float[] {0f, 0.35f, 1f},
                new Color[] {
                        new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(120 * light.strength)),
                        new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(50 * light.strength)),
                        new Color(c.getRed(), c.getGreen(), c.getBlue(), 0)
                }
        ));
        lg.fill(new Ellipse2D.Float(cx - glowR, cy - glowR, glowR * 2f, glowR * 2f));
    }

    public Config getConfig() { return config; }
}