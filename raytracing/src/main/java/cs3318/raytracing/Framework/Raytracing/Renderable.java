package cs3318.raytracing.Framework.Raytracing;

import java.awt.*;
import java.util.List;

// An object must implement a Renderable interface in order to
// be ray traced. Using this interface it is straight forward
// to add new objects
public abstract interface Renderable {
    public abstract boolean intersect(Ray r);
    public abstract Color Shade(Ray r, java.util.List<Object> lights, List<Object> objects, Color bgnd);
    public String toString();
}
