package cs3318.raytracing.Controller;

import cs3318.raytracing.Framework.*;
import cs3318.raytracing.Framework.Math.Vector3D;
import cs3318.raytracing.Framework.Objects.Sphere;
import cs3318.raytracing.Framework.Raytracing.Light;
import cs3318.raytracing.Framework.Raytracing.Ray;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;

public class SceneDriver {
    final static int CHUNKSIZE = 100;
    List<Object> objectList;
    List<Object> lightList;

    Canvas canvas;
    GraphicsContext gc;

    Vector3D eye, lookat, up;
    Vector3D Du, Dv, Vp;
    float fov;

    Color background;

    int width, height;

    public SceneDriver(int width, int height, Scene scene) {
        this.width = width;
        this.height = height;

        canvas = new Canvas(this.width, this.height);
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        // Initialize properties from the Scene
        objectList = scene.spheres;
        lightList = scene.lights;
        eye = scene.eye;
        lookat = scene.lookat;
        up = scene.up;
        fov = scene.fov;
        background = scene.background;

        // Compute viewing matrix
        initializeViewingMatrix();
    }


    private void initializeViewingMatrix() {
        Vector3D look = new Vector3D(lookat.x - eye.x, lookat.y - eye.y, lookat.z - eye.z);
        Du = Vector3D.normalize(look.cross(up));
        Dv = Vector3D.normalize(look.cross(Du));
        float fl = (float) (width / (2 * Math.tan((0.5 * fov) * Math.PI / 180)));
        Vp = Vector3D.normalize(look);
        Vp.x = Vp.x * fl - 0.5f * (width * Du.x + height * Dv.x);
        Vp.y = Vp.y * fl - 0.5f * (width * Du.y + height * Dv.y);
        Vp.z = Vp.z * fl - 0.5f * (width * Du.z + height * Dv.z);
    }

    public Image getRenderedImage() {
        return canvas.snapshot(null, null);
    }

    public void renderPixel(int i, int j) {
        Vector3D dir = new Vector3D(
                i * Du.x + j * Dv.x + Vp.x,
                i * Du.y + j * Dv.y + Vp.y,
                i * Du.z + j * Dv.z + Vp.z);
        Ray ray = new Ray(eye, dir);
        java.awt.Color bg = toAWTColor(background);

        Sphere closestIntersected = null;
        double closestDist = Double.MAX_VALUE;

        // Check for intersection with all spheres in the object list
        for (Object obj : objectList) {
            if (obj instanceof Sphere) {
                Sphere sphere = (Sphere) obj;
                double dist = ray.intersect(sphere);
                if (dist < closestDist) {
                    closestDist = dist;
                    closestIntersected = sphere;
                }
            }
        }

        if (closestIntersected != null) {
            java.awt.Color shadedColor = closestIntersected.Shade(ray, lightList, objectList, bg);
            gc.setFill(toFXColor(shadedColor));
        } else {
            gc.setFill(background);
        }

        gc.fillOval(i, j, 1, 1);
    }

    private java.awt.Color toAWTColor(Color c) {
        return new java.awt.Color((float) c.getRed(),
                (float) c.getGreen(),
                (float) c.getBlue(),
                (float) c.getOpacity());
    }

    private Color toFXColor(java.awt.Color c) {
        return Color.rgb(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha() / 255.0);
    }
}
