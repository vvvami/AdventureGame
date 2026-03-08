package interactable.tile;

import interactable.Interactable;
import interactable.interactions.Collider;

import java.awt.*;

public abstract class Tile extends Interactable {

    float friction = 1f;

    public Tile() {
        super();
    }

    public Tile(float x, float y) {
        super(x, y);
        this.setGravity(false);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public String getAssetFolder() {
        return "tiles";
    }

    @Override
    public void registerSprites() {
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getFriction() {
        return friction;
    }
}
