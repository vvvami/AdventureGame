package entity;

import render.SpriteRenderer;
import render.SpriteSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Interactable {
    private int x = 100;
    private int y = 100;

    private SpriteRenderer renderer;
    private SpriteSet spriteSet = new SpriteSet();

    private static ArrayList<Interactable> interactableList = new ArrayList<>();

    public Interactable() {
        interactableList.add(this);
        renderer = new SpriteRenderer(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public SpriteRenderer getRenderer() {
        return renderer;
    }

    public SpriteSet getSpriteSet() {
        return spriteSet;
    }

    public Interactable addSprite(String name, String filePath) {
        spriteSet.addFromPath(name, filePath);
        return this;
    }

    public void update() {

    }

    public void draw() {

    }

    public void drawSprite(String name) {
        this.getRenderer().drawSpriteFromImage(
                getImageFromName(name));
    }

    private BufferedImage getImageFromName(String name) {
        return getRenderer().getSpriteFromPath(
                getPathFromSpriteSet(name));
    }

    private String getPathFromSpriteSet(String name) {
        return this.getSpriteSet().getPathFromName(name);
    }

    public static void renderInteractablesInList(Graphics2D graphics2D) {
        for (Interactable interactable : interactableList) {
            interactable.renderer.setGraphics2D(graphics2D);
            interactable.draw();
        }
    }

    public static void updateInteractablesInList() {
        for (Interactable interactable : interactableList) {
            interactable.update();
        }
    }
}
