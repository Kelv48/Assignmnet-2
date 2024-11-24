package cs3318.raytracing.Framework.Objects;

import cs3318.raytracing.Framework.Math.Vector3D;
import cs3318.raytracing.Framework.Raytracing.Ray;
import cs3318.raytracing.Framework.Raytracing.Renderable;
import cs3318.raytracing.Framework.Material.Surface;

import java.awt.*;
import java.util.List;

// An example "Renderable" object
public class Sphere implements Renderable {
    static Surface surface;
    public static Vector3D center;
    public float radius;
    float radSqr;

    public Sphere(Surface s, Vector3D c, float r) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        surface = s;
        center = c;
        radius = r;
        radSqr = r*r;
    }

    public boolean intersect(Ray ray) {
        float dx = center.x - ray.origin.x;
        float dy = center.y - ray.origin.y;
        float dz = center.z - ray.origin.z;
        float v = ray.direction.dot(dx, dy, dz);

        // Do the following quick check to see if there is even a chance
        // that an intersection here might be closer than a previous one
        if (v - radius > ray.t)
            return false;

        // Test if the ray actually intersects the sphere
        float t = radSqr + v*v - dx*dx - dy*dy - dz*dz;
        if (t < 0)
            return false;

        // Test if the intersection is in the positive
        // ray direction and it is the closest so far
        t = v - ((float) Math.sqrt(t));
        if ((t > ray.t) || (t < 0))
            return false;

        ray.t = t;
        ray.object = this;
        return true;
    }

    public float getIntersectionDistance(Ray ray) {
        // This method calculates and returns the actual distance to the intersection point
        return computeIntersectionDistance(ray); // Returns the distance or -1 if no intersection
    }

    private float computeIntersectionDistance(Ray ray) {
        // Manually subtract the components
        Vector3D oc = new Vector3D(
                ray.origin.x - center.x,
                ray.origin.y - center.y,
                ray.origin.z - center.z
        );

        float a = ray.direction.dot(ray.direction);
        float b = 2.0f * oc.dot(ray.direction);
        float c = oc.dot(oc) - radius * radius;
        float discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return -1; // No intersection
        } else {
            // Return the nearest positive intersection distance
            float t1 = (-b - (float) Math.sqrt(discriminant)) / (2.0f * a);
            float t2 = (-b + (float) Math.sqrt(discriminant)) / (2.0f * a);

            if (t1 > 0) return t1; // Closest valid intersection
            if (t2 > 0) return t2; // Secondary valid intersection
            return -1; // No valid intersection
        }
    }

    public float intersects(Ray ray) {
        // Ray's origin and direction
        Vector3D O = ray.getOrigin();
        Vector3D D = ray.getDirection();

        // Compute A, B, and C for the quadratic equation
        Vector3D OC = O.subtract(center); // Vector from ray origin to sphere center
        float a = D.dot(D); // D · D
        float b = 2.0f * D.dot(OC); // 2 * (D · (O - C))
        float c = OC.dot(OC) - (radius * radius); // (O - C) · (O - C) - r^2

        // Discriminant
        float discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return -1;  // No intersection
        } else {
            // The ray intersects the sphere, return the nearest intersection
            float t1 = (-b - (float)Math.sqrt(discriminant)) / (2 * a);
            float t2 = (-b + (float)Math.sqrt(discriminant)) / (2 * a);

            // If t1 is the smaller value, it's the closest intersection
            return Math.min(t1, t2);
        }
    }

    public Color Shade(Ray ray, java.util.List<Object> lights, List<Object> objects, Color bgnd) {
        // An object shader doesn't really do too much other than
        // supply a few critical bits of geometric information
        // for a surface shader. It must must compute:
        //
        //   1. the point of intersection (p)
        //   2. a unit-length surface normal (n)
        //   3. a unit-length vector towards the ray's origin (v)
        //
        float px = ray.origin.x + ray.t*ray.direction.x;
        float py = ray.origin.y + ray.t*ray.direction.y;
        float pz = ray.origin.z + ray.t*ray.direction.z;

        Vector3D p = new Vector3D(px, py, pz);
        Vector3D v = new Vector3D(-ray.direction.x, -ray.direction.y, -ray.direction.z);
        Vector3D n = new Vector3D(px - center.x, py - center.y, pz - center.z);
        n.normalize();

        // The illumination model is applied
        // by the surface's Shade() method
        return surface.Shade(p, n, v, lights, objects, bgnd);
    }

    public String toString() {
        return ("sphere "+center+" "+radius);
    }
}