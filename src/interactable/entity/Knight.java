package interactable.entity;

import interactable.interactions.Collider;

public class Knight extends Entity {
    public Knight(float x, float y) {
        super(x, y);
        setCollision(true);
        setCollider(new Collider(this,0.2, 0.2, 0.8, 1));
    }

    @Override
    public void registerSprites() {
        addSprite("knight").setScale(2f);
        setSprite("knight");
    }
}
