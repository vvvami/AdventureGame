package interactable.interactions;

import interactable.Interactable;
import render.sprite.Sprite;
import util.AABB;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Collider {
    private double left = 0;
    private double top = 0;
    private double right = 1;
    private double bottom = 1;

    private AABB collisionBox;

    private static ArrayList<Collider> colliderList = new ArrayList<>();

    public Collider(Interactable interactable, double left, double top, double right, double bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        collisionBox = new AABB(
                interactable.getX(),
                interactable.getY(),
                interactable.getSprite().getWidth(),
                interactable.getSprite().getHeight());

        update(interactable);
        colliderList.add(this);
    }

    public Collider(Interactable interactable) {
        this(interactable, 0, 0, 1, 1);
        update(interactable);
    }

    public AABB getBox() {
        return collisionBox;
    }

    public float getWidth() {
        return collisionBox.width;
    }
    public float getHeight() {
        return collisionBox.height;
    }

    public float getX() {
        return collisionBox.x;
    }

    public float getY() {
        return collisionBox.y;
    }

    public void setX(float x) {
        collisionBox.x = x;
    }

    public void setY(float y) {
        collisionBox.y = y;
    }

    public void setWidth(float width) {
        collisionBox.width = width;
    }

    public void setHeight(float height) {
        collisionBox.height = height;
    }

    public void setBox(AABB collisionBox) {
        this.collisionBox = collisionBox;
    }

    public void grow(float width, float height) {
        this.collisionBox.grow(width, height);
    }

    public void grow(float amount) {
        this.collisionBox.grow(amount, amount);
    }

    public boolean isCollidingWith(Collider collider) {
        return this.collisionBox.intersects(collider.collisionBox);
    }

    public static ArrayList<Collider> list() {
        return colliderList;
    }

    public void update(Interactable interactable) {
        Sprite sprite = interactable.getSprite();
        this.collisionBox.x = (float) (interactable.getX() + sprite.getWidth() * left  - sprite.getWidth() / 2);
        this.collisionBox.y = (float) (interactable.getY() + sprite.getHeight() * top  - sprite.getHeight() / 2);
        this.collisionBox.width = (float) (sprite.getWidth() * (right - left));
        this.collisionBox.height = (float) (sprite.getHeight() * (bottom - top));
    }
}
