package interactable.tile;

import interactable.Interactable;

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
    public Rectangle getCollider() {
        return null;
    }
}
