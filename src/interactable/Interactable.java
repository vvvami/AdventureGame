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

    private SpriteType currentSprite;

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

    @SuppressWarnings(value = "all")
    public Sprite addSprite(String name) {
        if (Sprite.getList().contains(Sprite.getSpriteFromName(name))) {
            return null;
        }

        String filePath = getFullFilePath(name);

        Sprite newSprite = new Sprite(filePath);
        return newSprite;
    }

    private String getFullFilePath(String name) {
        return SpriteRenderer.assetPath + this.getAssetFolder() + "/"
                + name + SpriteRenderer.spriteFileType;
    }

    public AnimatedSprite addAnimatedSprite(String name) {
        AnimatedSprite.getList().add(new AnimatedSprite(name, 12));
        return (AnimatedSprite) SpriteType.getList().getLast();
    }

    public AnimatedSprite addAnimatedSprite(String name, int speed) {
        AnimatedSprite.getList().add(new AnimatedSprite(name, speed));
        return (AnimatedSprite) SpriteType.getList().getLast();
    }

    public void setSprite(String name) {
        SpriteType sprite = Sprite.getSpriteFromName(name);
        if (sprite == null) {
            sprite = AnimatedSprite.getAnimationFromName(name);
        }

        this.currentSprite = sprite;
    }

    public AnimatedSprite getSpriteAnimation(String name) {
        return AnimatedSprite.getAnimationFromName(name);
    }

    @Override
    public String getAssetFolder() {
        return "";
    }

    @Override
    public void draw() {
        if (currentSprite instanceof AnimatedSprite) {
            getRenderer().renderSpriteAnimation((AnimatedSprite) currentSprite);
        } else {
            getRenderer().renderSprite((Sprite) currentSprite);
        }
    }


    @Override
    public void update() {

    }

    @Override
    public void registerSprites() {

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

    public static ArrayList<Interactable> getList() {
        return interactableList;
    }
}
