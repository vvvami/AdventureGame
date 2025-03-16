package entity;

import render.AnimatedSprite;
import render.Renderable;
import render.SpriteRenderer;
import render.Sprite;

import java.awt.*;
import java.util.ArrayList;

public class Interactable {
    private int x = 100;
    private int y = 100;

    private SpriteRenderer renderer;
    private ArrayList<Renderable> sprites = new ArrayList<>();

    private static ArrayList<Interactable> interactableList = new ArrayList<>();

    public static final String assetPath = SpriteRenderer.assetPath + "entities/";

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

    public ArrayList<Renderable> getSprites() {
        return sprites;
    }

    public void addSprite(String name) {
        String filePath = assetPath + name + ".png";
        if (sprites.contains(Sprite.getSpriteFromName(name, this))) {
            return;
        }

        sprites.add(new Sprite(filePath));
    }

    public AnimatedSprite addAnimatedSprite(String name) {
        sprites.add(new AnimatedSprite(name, 10));
        return (AnimatedSprite) sprites.getLast();
    }

    public AnimatedSprite addAnimatedSprite(String name, int speed) {
        sprites.add(new AnimatedSprite(name, speed));
        return (AnimatedSprite) sprites.getLast();
    }

    public Sprite getSprite(String name) {
        return Sprite.getSpriteFromName(name, this);
    }

    public AnimatedSprite getSpriteAnimation(String name) {
        return AnimatedSprite.getAnimationFromName(name, this);
    }

    public void update() {
    }

    public void draw() {

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
