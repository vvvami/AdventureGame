package render;

import entity.Interactable;

import java.util.ArrayList;
import java.util.Arrays;

public class AnimatedSprite implements Renderable {

    private ArrayList<Sprite> sprites = new ArrayList<>();

    private int frameCounter;

    private int speed;

    private int index;

    private String name;

    public AnimatedSprite(String name, int speed, Sprite ... sprites) {
        this.sprites = (ArrayList<Sprite>) Arrays.stream(sprites).toList();
        this.speed = speed;
        this.name = name;
    }

    public AnimatedSprite(String name, int speed) {
        this.name = name;
        this.speed = speed;
    }

    public static AnimatedSprite getAnimationFromName(String name, Interactable interactable) {
        for (Renderable renderable : interactable.getSprites()) {
            if (renderable instanceof AnimatedSprite animatedSprite
            && animatedSprite.name.equals(name)) {
                return animatedSprite;
            }
        }
        return null;
    }

    public void render(SpriteRenderer renderer) {
        if (sprites.isEmpty()) {
            return;
        }

        Sprite nextSprite = sprites.get(index);

        frameCounter++;
        if (speed == frameCounter) {
            index++;
            frameCounter = 0;
        }

        if (index == sprites.size()) {
            index = 0;
        }
        renderer.renderSprite(nextSprite);
    }

    public AnimatedSprite addFrame(Sprite sprite) {
        this.sprites.add(sprite);
        return this;
    }

    public AnimatedSprite addFrame(String name, Interactable interactable) {
        this.sprites.add(Sprite.getSpriteFromName(name, interactable));
        return this;
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
}
