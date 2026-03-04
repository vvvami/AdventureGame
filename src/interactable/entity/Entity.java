package interactable.entity;

import interactable.Interactable;

public abstract class Entity extends Interactable {

    private float speed;

    public Entity() {

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
