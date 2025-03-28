package render;

public interface Renderable {

    void draw();
    void update();

    void registerSprites();

    String getAssetFolder();
}
