package render.light;

import java.awt.*;
import java.util.ArrayList;

public final class Light {
    public double x;
    public double y;
    public int radius;      // in world/view pixels at your virtual resolution
    public float strength;  // 0..1
    public boolean enabled = true;
    public Color color;
    private static ArrayList<Light> lights = new ArrayList<>();

    public Light(double x, double y, int radius, float strength, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.strength = strength;
        this.color = color;
        lights.add(this);
    }

    public static ArrayList<Light> list() {
        return lights;
    }
}