package render;

import interactable.Interactable;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteRenderer {
    private Graphics2D graphics2D;

    private Interactable interactable;

    public static final String assetPath = "/assets/sprites/";
    public static final String spriteFileType = ".png";

    public SpriteRenderer() {

    }

    public SpriteRenderer(Interactable interactable) {
        this.interactable = interactable;
    }

    public void renderSprite(String name) {
        Sprite sprite = Sprite.getSpriteFromName(name);
        renderSprite(sprite);
    }

    public void renderSprite(Sprite sprite) {
        if (sprite == null) {
            return;
        }

        drawSpriteFromImage(
                getImageFromPath(
                        sprite.getFilePath()), interactable.getX(), interactable.getY(), sprite.getScale());
    }

    public void renderSpriteAnimation(String name) {
        AnimatedSprite animatedSprite = AnimatedSprite.getAnimationFromName(name);
        renderSpriteAnimation(animatedSprite);
    }

    public void renderSpriteAnimation(AnimatedSprite animatedSprite) {
        if (animatedSprite == null) {
            return;
        }
        Sprite nextSprite = animatedSprite.getSprites().get(animatedSprite.getIndex());

        animatedSprite.setFrameCounter(animatedSprite.getFrameCounter() + 1);
        if (animatedSprite.getSpeed() == animatedSprite.getFrameCounter()) {
            animatedSprite.setIndex(animatedSprite.getIndex() + 1);
            animatedSprite.setFrameCounter(0);
        }

        if (animatedSprite.getIndex() == animatedSprite.getSprites().size()) {
            animatedSprite.setIndex(0);
        }
        this.renderSprite(nextSprite);
    }

    private void drawSpriteFromImage(BufferedImage bufferedImage, int x, int y, int scale) {
        x = centerSpritePositionWithScale(x, scale);
        y = centerSpritePositionWithScale(y, scale);

        graphics2D.drawImage(bufferedImage, x, y,
                GamePanel.tileSize * scale, GamePanel.tileSize * scale, null);
    }

    private int centerSpritePositionWithScale(int num, int scale) {
        num -= (GamePanel.tileSize * scale / 2) - GamePanel.tileSize / 2;
        return num;
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
