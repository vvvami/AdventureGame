package render;

import java.util.ArrayList;

public class AnimatedSprite implements SpriteType {

    private ArrayList<Sprite> sprites = new ArrayList<>();

    private int frameCounter;

    private int speed;

    private int index;

    private String name;

    private static ArrayList<AnimatedSprite> animations = new ArrayList<>();

    public AnimatedSprite(String name, int speed) {
        checkIfNameExists(name);
        this.name = name;
        this.speed = speed;
        animations.add(this);
    }

    public static AnimatedSprite getAnimationFromName(String name) {
        for (AnimatedSprite animatedSprite : animations) {
            if (animatedSprite.name.equals(name)) {
                return animatedSprite;
            }
        }
        return null;
    }

    public AnimatedSprite addFrame(Sprite sprite) {
        this.sprites.add(sprite);
        return this;
    }

    public AnimatedSprite addFrame(String name) {
        this.sprites.add(Sprite.getSpriteFromName(name));
        return this;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    private void checkIfNameExists(String name) {
        for (AnimatedSprite animatedSprite : animations) {
            if (animatedSprite.name.equals(name)) {
                    throw new RuntimeException("This animation already exists!");
            }
        }
    }
}
