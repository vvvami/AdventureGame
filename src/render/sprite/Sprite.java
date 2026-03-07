package render.sprite;

import main.Game;

public class Sprite extends SpriteType {
    private String filePath;
    private float scale = 1f;

    public Sprite(String filePath) {
        super();
        this.filePath = filePath;
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public static Sprite getSpriteFromName(String name) {
        for (SpriteType spriteType : SpriteType.getList()) {
            if (spriteType instanceof Sprite sprite
                    && sprite.getName().equals(name)) {
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

    public float getWidth() {
        return SpriteRenderer.browse().get(filePath).getWidth() * scale * Game.scale;
    }

    public float getHeight() {
        return SpriteRenderer.browse().get(filePath).getHeight() * scale * Game.scale;
    }

    @Override
    public Sprite get() {
        return this;
    }
}
