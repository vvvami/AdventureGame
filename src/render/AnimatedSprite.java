package render;

import java.util.ArrayList;

public class AnimatedSprite extends SpriteType {

    private ArrayList<Sprite> sprites = new ArrayList<>();

    private int frameCounter;

    private int speed;

    private int index;

    private String name;


    public AnimatedSprite(String name, int speed) {
        super();

        this.name = name;
        this.speed = speed;
    }

    public static AnimatedSprite getAnimationFromName(String name) {
        for (SpriteType sprite : SpriteType.getList()) {
            if (sprite instanceof AnimatedSprite animatedSprite
                    && animatedSprite.name.equals(name)) {
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

    public void scale(float scale) {
        for (Sprite sprite : sprites) {
            sprite.setScale((int) (sprite.getScale() * scale));
        }
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
        for (SpriteType sprite : SpriteType.getList()) {
            if (sprite instanceof AnimatedSprite animatedSprite
                    && animatedSprite.name.equals(name)) {
                    throw new RuntimeException("This animation already exists!");
            }
        }
    }
}
