package interactable.entity;

import interactable.interactions.Collider;

public class Knight extends Entity {
    public Knight() {
        setCollision(true);
        setCollider(new Collider(this, 32, 32));
    }

    @Override
    public void registerSprites() {
        addSprite("knight").setScale(2f);
        setSprite("knight");
    }
}
