package render.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteManager {
    private final Map<String, BufferedImage> cache = new HashMap<>();

    @SuppressWarnings(value = "all")
    public BufferedImage get(String path) {
        return cache.computeIfAbsent(path, p -> {
            try {
                return ImageIO.read(getClass().getResourceAsStream(p));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load image: " + p, e);
            }
        });
    }
}
