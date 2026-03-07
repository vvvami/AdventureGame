package world;

public class World {
    private static float gravity = 0.1f;
    public static final float maxFallSpeed = 15f;
    private static float time;

    public static float getGravity() {
        return gravity;
    }

    public static void setGravity(float gravity) {
        World.gravity = gravity;
    }
}
