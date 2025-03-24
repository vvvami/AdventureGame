package tile;

import interactable.Interactable;
import render.Sprite;

public class Tile extends Interactable {

    public Tile() {
        super();
        addSprite("grass_tile");
    }

    @Override
    public String getAssetFolder() {
        return "tiles";
    }

    @Override
    public void draw() {
        this.getRenderer().renderSprite("grass_tile");

    }
}
