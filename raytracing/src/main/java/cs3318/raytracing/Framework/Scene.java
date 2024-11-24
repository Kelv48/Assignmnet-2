package cs3318.raytracing.Framework;

import cs3318.raytracing.Framework.Objects.Sphere;
import cs3318.raytracing.Framework.Raytracing.Light;
import cs3318.raytracing.Framework.Material.Surface;
import cs3318.raytracing.Framework.Math.Vector3D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    public List<Object> spheres;
    public List<Object> lights;
    public Vector3D eye, lookat, up;
    public float fov;
    public Color background;

    public Scene() {
        // Default values
        spheres = new ArrayList<>();
        lights = new ArrayList<>();
        this.eye = new Vector3D(0, 0, 10);
        this.lookat = new Vector3D(0, 0, 0);
        this.up = new Vector3D(0, 1, 0);
        this.fov = 30;
        this.background = Color.BLACK;
    }

    public void addSphere(Surface surface, Vector3D position, float radius) {
        Sphere sphere = new Sphere(surface, position, radius);
        spheres.add(sphere);  // Make sure you're adding it to the object list
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public List<Object> getSpheres() {return spheres; }
    public List<Object> getLights() {return lights; }
//    public void setCamera(Camera camera) {this.camera = camera; }
//    public Camera getCamera() {return camera; }
}

