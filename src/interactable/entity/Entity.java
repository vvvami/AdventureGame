package interactable.entity;

import interactable.Interactable;

import java.awt.*;

public abstract class Entity extends Interactable {

    private float speed;

    public Entity(float x, float y) {
        super(x, y);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String getAssetFolder() {
        return "entities";
    }
}
