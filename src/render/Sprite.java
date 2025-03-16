package render;

import entity.Interactable;

import java.util.ArrayList;

public class Sprite implements Renderable {
    private String name;
    private String filePath;

    private static ArrayList<Sprite> spriteList = new ArrayList<>();

    public Sprite(String filePath) {
        this.filePath = filePath;

        this.name = spriteNameFromFilePath(filePath);

        spriteList.add(this);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Sprite getSpriteFromName(String name, Interactable interactable) {
        for (Renderable renderable : interactable.getSprites()) {
            if (renderable instanceof Sprite sprite
                    && sprite.name.equals(name)) {
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

    public void render(SpriteRenderer renderer) {
        renderer.renderSprite(this);
    }

}
