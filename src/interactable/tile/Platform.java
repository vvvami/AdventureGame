package interactable.tile;

import interactable.interactions.Collider;

public class Platform extends Tile {
    public Platform(float x, float y) {
        super(x, y);
        setCollider(new Collider(this, 0, 0.3, 1, 0.7));
        setCollision(true);
        setFriction(0.5f);
    }

    @Override
    public void registerSprites() {
        addSprite("platform_middle");
        setSprite("platform_middle");
    }
}
