package render.sprite;

import java.util.ArrayList;

public abstract class SpriteType {
    private static ArrayList<SpriteType> spriteList = new ArrayList<>();

    public SpriteType() {
        spriteList.add(this);
    }

    public abstract Sprite get();

    public static ArrayList<SpriteType> getList() {
        return spriteList;
    }
}
