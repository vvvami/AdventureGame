package tile;

import interactable.Interactable;
import render.Sprite;

public class Tile extends Interactable {

    public Tile() {
        super();
    }

    @Override
    public String getAssetFolder() {
        return "tiles";
    }

    @Override
    public void registerSprites() {
        addSprite("grass_tile");
        setSprite("grass_tile");
    }
}
