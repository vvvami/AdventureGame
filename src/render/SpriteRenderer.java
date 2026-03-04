package render;

import interactable.Interactable;
import interactable.entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteRenderer {
    private Graphics2D graphics2D;

    private Interactable interactable;

    public static final String assetPath = "/assets/sprites/";
    public static final String spriteFileType = ".png";

    private SpriteManager spriteManager = new SpriteManager();

    public SpriteRenderer() {

    }

    public SpriteRenderer(Interactable interactable) {
        this.interactable = interactable;
    }

    public void renderSprite(String name) {
        Sprite sprite = Sprite.getSpriteFromName(name);
        renderSprite(sprite);
    }

    public void renderSprite(Sprite sprite, float x, float y) {

    }

    public void renderSprite(Sprite sprite) {
        if (sprite == null) {
            return;
        }
        if (isOutOfBounds()) {
            return;
        }
        interactable.isRendering = true;
        drawSpriteFromImage(
                getImageFromPath(
                        sprite.getFilePath()), (int) interactable.getX(), (int) interactable.getY(), sprite.getScale());
    }

    public void renderSpriteAnimation(String name) {
        AnimatedSprite animatedSprite = AnimatedSprite.getAnimationFromName(name);
        renderSpriteAnimation(animatedSprite);
    }

    public void renderSpriteAnimation(AnimatedSprite animatedSprite) {
        if (animatedSprite == null) {
            return;
        }

        Sprite nextSprite = animatedSprite.getSprites().get(animatedSprite.getFrameIndex());

        animatedSprite.setFrameCounter(animatedSprite.getFrameCounter() + 1);

        if (animatedSprite.getSpeed() == animatedSprite.getFrameCounter()) {
            animatedSprite.setFrameIndex(animatedSprite.getFrameIndex() + 1);
            animatedSprite.setFrameCounter(0);
        }

        if (animatedSprite.getFrameIndex() == animatedSprite.getSprites().size()) {
            animatedSprite.setFrameIndex(0);
        }
        this.renderSprite(nextSprite);
    }

    private void drawSpriteFromImage(BufferedImage image, int x, int y, float scale) {
        // center sprite after scaling
        float posX = centerSpritePositionWithScale(x, scale);
        float posY = centerSpritePositionWithScale(y, scale);

        // translate render position with cam position
        posX -= GamePanel.getCamPos().x;
        posY -= GamePanel.getCamPos().y;

        // draw the sprite
        AffineTransform transform = new AffineTransform();
        transform.translate(posX, posY);
        transform.scale(GamePanel.tileSize * scale / 16, GamePanel.tileSize * scale / 16);
        graphics2D.drawImage(image, transform, null);
    }

    private float centerSpritePositionWithScale(float pos, float scale) {
        pos -= (GamePanel.tileSize * scale / 2);
        return pos;
    }

    private BufferedImage getImageFromPath(String filePath) {
        return spriteManager.get(filePath);
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    private boolean isOutOfBounds() {
        if (interactable instanceof Player) {return false;}
        Rectangle viewport = GamePanel.getViewport();
        Interactable i = interactable;
        int offs = 48;

        if (i.getX() < viewport.x - offs
                || i.getX() > viewport.x + viewport.width + offs
                || i.getY() < viewport.y - offs
                || i.getY() > viewport.y + viewport.height + offs) {
            interactable.isRendering = false;
            return true;
        }
        return false;
    }

}
