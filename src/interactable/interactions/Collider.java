package interactable.interactions;

import interactable.Interactable;
import main.GamePanel;
import render.AnimatedSprite;
import render.Sprite;

import java.awt.*;

public class Collider {
    private Rectangle collisionBox;

    public Collider(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public Collider(Interactable interactable, int width, int height) {
        this(new Rectangle(
                (int) interactable.getX() - GamePanel.tileSize / 2,
                (int) interactable.getY() - GamePanel.tileSize / 2,
                width, height));
    }

    public Collider(Interactable interactable) {
        this(interactable, 16, 16);
    }

    public Rectangle getBox() {
        return collisionBox;
    }

    public void setBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public void grow(int width, int height) {
        this.collisionBox.grow(width, height);
    }

    public void grow(int amount) {
        this.collisionBox.grow(amount, amount);
    }

    public boolean isCollidingWith(Collider collider) {
        return this.collisionBox.intersects(collider.collisionBox);
    }

    public void update(Interactable interactable) {
        this.collisionBox.x = (int) (interactable.getX() - ((float) collisionBox.width / 2));
        this.collisionBox.y = (int) (interactable.getY() - ((float) collisionBox.height / 2));
    }
}
