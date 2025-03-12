package render;

import java.util.HashMap;

public class SpriteSet {
    private HashMap<String, String> spriteMap = new HashMap<>();

    public SpriteSet() {

    }

    public SpriteSet addFromPath(String name, String filePath) {
       spriteMap.put(name, SpriteRenderer.fromPath(filePath));
       return this;
    }

    public String getPathFromName(String name) {
        return spriteMap.get(name);
    }

    public boolean isEmpty() {
        return spriteMap.isEmpty();
    }
}
