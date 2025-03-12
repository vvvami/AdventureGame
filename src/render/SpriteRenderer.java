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

    private static String assetPath = "/assets/sprites/";

    public SpriteRenderer(Interactable interactable) {
        this.interactable = interactable;
    }


    public void drawSpriteFromImage(BufferedImage bufferedImage) {
        graphics2D.drawImage(bufferedImage, interactable.getX(), interactable.getY(),
                GamePanel.getTileSize(), GamePanel.getTileSize(), null);
    }



    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    public BufferedImage getSpriteFromPath(String filePath) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fromPath(String path) {
        return assetPath + path;
    }


}
