package render.sprite;

import interactable.Interactable;
import interactable.entity.Player;
import main.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteRenderer {
    private Graphics2D graphics2D;

    private Interactable interactable;

    public static final String assetPath = "/assets/sprites/";
    public static final String spriteFileType = ".png";

    private static SpriteManager spriteManager = new SpriteManager();

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

        Sprite currentSprite = animatedSprite.getSprites().get(animatedSprite.getFrameIndex());
        this.renderSprite(currentSprite);
    }

    public void updateSpriteAnimation(AnimatedSprite animatedSprite) {
        if (animatedSprite == null) {
            return;
        }

        animatedSprite.setFrameCounter(animatedSprite.getFrameCounter() + 1);

        if (animatedSprite.getFrameCounter() >= animatedSprite.getSpeed()) {
            animatedSprite.setFrameCounter(0);
            animatedSprite.setFrameIndex(animatedSprite.getFrameIndex() + 1);

            if (animatedSprite.getFrameIndex() >= animatedSprite.getSprites().size()) {
                animatedSprite.setFrameIndex(0);
            }
        }
    }

    private void drawSpriteFromImage(BufferedImage image, int x, int y, float scale) {
        // center sprite after scaling
        float posX = centerSpritePositionWithScale(x, scale);
        float posY = centerSpritePositionWithScale(y, scale);

        /*translate render position with cam position
        posX -= GamePanel.getCamPos().x;
        posY -= GamePanel.getCamPos().y;*/

        // draw the sprite
        AffineTransform transform = new AffineTransform();
        transform.translate(posX, posY);
        transform.scale(Game.scale * scale, Game.scale * scale);
        graphics2D.drawImage(image, transform, null);
    }

    private float centerSpritePositionWithScale(float pos, float scale) {
        pos -= (Game.tileSize * scale / 2);
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
        Rectangle viewport = Game.getViewport();
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

    public static SpriteManager browse() {
        return spriteManager;
    }

}
