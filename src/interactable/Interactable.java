package interactable;

import render.*;

import java.awt.*;
import java.util.ArrayList;

public abstract class Interactable implements Renderable {
    private float x;
    private float y;

    private Rectangle collider;
    private boolean hasCollision;

    private SpriteRenderer renderer;
    private SpriteType currentSprite;
    private ArrayList<SpriteType> spriteLayers = new ArrayList<>();

    private static ArrayList<Interactable> interactableList = new ArrayList<>();

    public Interactable() {
        this(0,0);

    }

    public Interactable(int x, int y) {
        this.x = x;
        this.y = y;
        interactableList.add(this);
        renderer = new SpriteRenderer(this);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Interactable setY(float y) {
        this.y = y;
        return this;
    }

    public Interactable setX(float x) {
        this.x = x;
        return this;
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
        return SpriteRenderer.assetPath + getAssetFolder() + "/"
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
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }

        if (this.currentSprite instanceof AnimatedSprite animatedSprite
        && animatedSprite.getFrameIndex() == 0) {
            return;
        }

        this.currentSprite = sprite;
    }

    public SpriteType getCurrentSprite() {
        return currentSprite;
    }

    @Override
    public void draw() {
        if (currentSprite instanceof AnimatedSprite) {
            getRenderer().renderSpriteAnimation((AnimatedSprite) currentSprite);
        } else {
            getRenderer().renderSprite((Sprite) currentSprite);
        }

        for (SpriteType sprite : spriteLayers) {
            if (sprite instanceof AnimatedSprite) {
                getRenderer().renderSpriteAnimation((AnimatedSprite) sprite);
            } else {
                getRenderer().renderSprite((Sprite) sprite);
            }
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

    public String getAssetFolder() {
        return "";
    }

    public Rectangle getCollider() {
        return collider;
    }

    public void setCollider(Rectangle collider) {
        this.collider = collider;
    }

    public ArrayList<SpriteType> getSpriteLayers() {
        return spriteLayers;
    }

    public void setCurrentSprite(String name) {
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }
        this.currentSprite = sprite;
    }

    public void setSpriteLayers(ArrayList<SpriteType> spriteLayers) {
        this.spriteLayers = spriteLayers;
    }

    public void addSpriteLayer(String name) {
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }
        addSpriteLayer(sprite);
    }

    public void addSpriteLayer(SpriteType sprite) {
        if (spriteLayers.contains(sprite)) {return;}
        spriteLayers.add(sprite);
    }

    public void removeSpriteLayer(String name) {
        SpriteType sprite = AnimatedSprite.getAnimationFromName(name);
        if (sprite == null) {
            sprite = Sprite.getSpriteFromName(name);
        }
        removeSpriteLayer(sprite);
    }

    public void removeSpriteLayer(SpriteType sprite) {
        spriteLayers.remove(sprite);
    }

    public void clearSpriteLayers() {
        spriteLayers.clear();
    }

}
