package render;

import java.util.ArrayList;

public class Sprite implements SpriteType {
    private String filePath;
    private int scale = 1;

    private static ArrayList<Sprite> spriteList = new ArrayList<>();

    public Sprite(String filePath) {
        this.filePath = filePath;


        spriteList.add(this);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return spriteNameFromFilePath(filePath);
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }

    public static Sprite getSpriteFromName(String name) {
        for (Sprite sprite : spriteList) {
            if (sprite.getName().equals(name)) {
                return sprite;
            }
        }
        return null;
    }

    public static String spriteNameFromFilePath(String filePath) {
        int nameIndex = filePath.lastIndexOf("/") + 1;
        int extensionIndex = filePath.lastIndexOf(".");

        return filePath.substring(nameIndex, extensionIndex);
    }

}
