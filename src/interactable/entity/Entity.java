package interactable.entity;

import interactable.Interactable;

public class Entity extends Interactable {

    private int speed;

    public Entity() {

    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String getAssetFolder() {
        return "entities";
    }
}
