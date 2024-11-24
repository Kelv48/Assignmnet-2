package cs3318.raytracing.Framework.Raytracing;

import cs3318.raytracing.Framework.Math.Vector3D;
import cs3318.raytracing.Framework.Raytracing.Renderable;
import cs3318.raytracing.Framework.Objects.Sphere;

import java.awt.*;
import java.util.List;

public class Ray {
    public static final float MAX_T = Float.MAX_VALUE;
    public Vector3D origin;
    public Vector3D direction;
    public float t;
    public Renderable object;

    float closestDistance = Float.MAX_VALUE;
    public Object closestObject = null;

    public Ray(Vector3D eye, Vector3D dir) {
        this.origin = new Vector3D(eye);
        this.direction = Vector3D.normalize(dir);
        this.t = MAX_T;
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public Vector3D getDirection() {
        return direction;
    }

    public double intersect(Sphere sphere) {
        Vector3D oc = origin.subtract(sphere.center);  // Vector from ray origin to sphere center
        double a = direction.dot(direction);  // Dot product of direction with itself (should be 1 if normalized)
        double b = 2.0 * oc.dot(direction);  // Dot product of (origin - center) with direction
        double c = oc.dot(oc) - sphere.radius * sphere.radius;  // Distance squared from origin to center - radius squared

        double discriminant = b * b - 4 * a * c;  // Discriminant of the quadratic equation

        if (discriminant > 0) {
            // Two solutions (ray intersects the sphere in two points)
            double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
            double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);

            // Return the smallest positive t value (closest intersection point)
            if (t1 > 0 && t2 > 0) {
                return Math.min(t1, t2);
            } else if (t1 > 0) {
                return t1;
            } else if (t2 > 0) {
                return t2;
            }
        } else if (discriminant == 0) {
            // One solution (ray tangential to the sphere)
            double t = -b / (2 * a);
            if (t > 0) {
                return t;
            }
        }

        // No intersection
        return Double.POSITIVE_INFINITY;
    }

    public boolean trace(List<Object> objects) {
        for (Object obj : objects) {
            // Ensure the object is of type renderable
            if (obj instanceof Renderable renderable) {
                // Check if the ray intersects the object
                if (renderable.intersect(this)) {
                    // Only update if this is the closest intersection
                    if (this.t < MAX_T) {
                        object = renderable;
                    }
                }
            }
        }
        // Return true if an intersection was found
        return  object != null;
    }

    public boolean traceAlt(List<Object> objects) {
        float closestDistance = Float.MAX_VALUE;
        Object closestObject = null;

        for (Object obj : objects) {
            if (obj instanceof Sphere) {
                Sphere sphere = (Sphere) obj;
                float t = sphere.getIntersectionDistance(this); // Assume intersect returns the distance
                if (t > 0 && t < closestDistance) {
                    closestDistance = t;
                    closestObject = sphere;
                }
            }
        }

        if (closestObject != null) {
            this.closestObject = closestObject;
            return true;
        }
        return false;
    }


    public final java.awt.Color Shade(List<Object> lights, List<Object> objects, Color bgnd) {
        if (object == null) {
            // If no intersection, return the background color
            return bgnd;
        }

        // Call the object's Shade method as defined in the renderable interface
        return object.Shade(this, lights, objects, bgnd);
    }
}
