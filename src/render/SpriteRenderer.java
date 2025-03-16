package render;

import entity.Interactable;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteRenderer {
    private Graphics2D graphics2D;

    private Interactable interactable;

    public static final String assetPath = "/assets/sprites/";

    public SpriteRenderer() {

    }

    public SpriteRenderer(Interactable interactable) {
        this.interactable = interactable;
    }

    public void renderSprite(Sprite sprite, int x, int y) {
       drawSpriteFromImage(
               getImageFromPath(
                       sprite.getFilePath()), x, y);
    }

    public void renderSprite(Sprite sprite, Interactable interactable) {
        drawSpriteFromImage(
                getImageFromPath(
                        sprite.getFilePath()), interactable.getX(), interactable.getY());
    }

    public void renderSprite(Sprite sprite) {
        drawSpriteFromImage(
                getImageFromPath(
                        sprite.getFilePath()), interactable.getX(), interactable.getY());
    }

    private void drawSpriteFromImage(BufferedImage bufferedImage, int x, int y) {
        graphics2D.drawImage(bufferedImage, x, y,
                GamePanel.getTileSize(), GamePanel.getTileSize(), null);
    }

    private BufferedImage getImageFromPath(String filePath) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

}
