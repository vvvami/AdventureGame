package interactable.tile;

import interactable.Interactable;
import interactable.interactions.Collider;

import java.awt.*;

public class Tile extends Interactable {

    public Tile() {
        super();
    }

    public Tile(int x, int y) {
        super(x, y);
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
        addSprite("floor");
        setSprite("floor");
    }

    @Override
    public Collider getCollider() {
        return null;
    }
}
