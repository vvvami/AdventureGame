package render.light;

import java.awt.*;
import java.util.ArrayList;

public class Light {
    private static ArrayList<Light> lights = new ArrayList<>();

    public double x, y;          // world position
    public double radius;        // world scale
    public Color color;          // usually with alpha 255
    public float intensity;      // 0..1

    public Light(double x, double y, double radius, Color color, float intensity) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.intensity = intensity;
        lights.add(this);
    }

    public static ArrayList<Light> getLights() {
        return lights;
    }

    public static void setLights(ArrayList<Light> lights) {
        Light.lights = lights;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

}
