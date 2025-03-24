package interactable;

import render.*;

import java.awt.*;
import java.util.ArrayList;

public class Interactable implements Renderable {
    private int x = 100;
    private int y = 100;

    private int scale;

    private boolean hasCollision;

    private SpriteRenderer renderer;
    private ArrayList<SpriteType> sprites = new ArrayList<>();

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

    public ArrayList<SpriteType> getSprites() {
        return sprites;
    }

    @SuppressWarnings(value = "all")
    public <T extends Interactable> T addSprite(String name) {
        if (sprites.contains(Sprite.getSpriteFromName(name))) {
            return null;
        }

        String filePath = getFullFilePath(name);

        Sprite newSprite = new Sprite(filePath);
        sprites.add(newSprite);
        return (T) this;
    }

    private String getFullFilePath(String name) {
        return SpriteRenderer.assetPath + this.getAssetFolder() + "/"
                + name + SpriteRenderer.spriteFileType;
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
        return Sprite.getSpriteFromName(name);
    }

    public AnimatedSprite getSpriteAnimation(String name) {
        return AnimatedSprite.getAnimationFromName(name);
    }

    public void update() {

    }

    @Override
    public String getAssetFolder() {
        return "";
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
