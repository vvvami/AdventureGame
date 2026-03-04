package interactable.entity;

import interactable.Interactable;

import java.awt.*;

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

    public void move(float dx, float dy) {
        Rectangle futureCollider = this.getCollider().getBox();
        futureCollider.x += (int) dx;
        futureCollider.y += (int) dy;
        for (Interactable interactable : Interactable.getList()) {
            if (!interactable.isRendering) continue;
            if (interactable == this) continue;
            if (!interactable.hasCollision()) continue;
            if (futureCollider.intersects(interactable.getCollider().getBox())) {
                return;
            }
        }
        setX(getX() + dx);
        setY(getY() + dy);
    }

    @Override
    public String getAssetFolder() {
        return "entities";
    }
}
