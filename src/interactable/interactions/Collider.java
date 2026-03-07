package interactable.interactions;

import interactable.Interactable;
import render.sprite.Sprite;

import java.awt.*;
import java.util.ArrayList;

public class Collider {
    private double left = 0;
    private double top = 0;
    private double right = 1;
    private double bottom = 1;

    private Rectangle collisionBox;

    private static ArrayList<Collider> colliderList = new ArrayList<>();

    public Collider(Interactable interactable, double left, double top, double right, double bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        collisionBox = new Rectangle((int) interactable.getX(), (int) interactable.getY(),
                (int) interactable.getSprite().getWidth(),
                (int) interactable.getSprite().getHeight());
        update(interactable);
        colliderList.add(this);
    }

    public Collider(Interactable interactable) {
        this(interactable, 0, 0, 1, 1);
        update(interactable);
    }

    public Rectangle getBox() {
        return collisionBox;
    }

    public int getWidth() {
        return collisionBox.width;
    }
    public int getHeight() {
        return collisionBox.height;
    }

    public int getX() {
        return collisionBox.x;
    }

    public int getY() {
        return collisionBox.y;
    }

    public void setX(int x) {
        collisionBox.x = x;
    }

    public void setY(int y) {
        collisionBox.y = y;
    }

    public void setWidth(int width) {
        collisionBox.width = width;
    }

    public void setHeight(int height) {
        collisionBox.height = height;
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

    public static ArrayList<Collider> getColliderList() {
        return colliderList;
    }

    public void update(Interactable interactable) {
        Sprite sprite = interactable.getSprite();
        this.collisionBox.x = (int) (interactable.getX() + sprite.getWidth() * left  - (float) sprite.getWidth() / 2);
        this.collisionBox.y = (int) (interactable.getY() + sprite.getHeight() * top  - (float) sprite.getHeight() / 2);
        this.collisionBox.width = (int) (sprite.getWidth() * (right - left));
        this.collisionBox.height = (int) (sprite.getHeight() * (bottom - top));
    }
}
