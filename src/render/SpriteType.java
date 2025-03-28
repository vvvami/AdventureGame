package render;

import java.util.ArrayList;

public abstract class SpriteType {
    private static ArrayList<SpriteType> spriteList = new ArrayList<>();

    public SpriteType() {
        spriteList.add(this);
    }

    public static ArrayList<SpriteType> getList() {
        return spriteList;
    }
}
