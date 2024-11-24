package cs3318.raytracing.Framework.Raytracing;

import cs3318.raytracing.Framework.Math.Vector3D;

// All the public variables here are ugly, but I
// wanted Lights and Surfaces to be "friends"
public class Light {
    public static final int AMBIENT = 0;
    public static final int DIRECTIONAL = 1;
    public static final int POINT = 2;

    public int lightType;
    public Vector3D lvec;           // the position of a point light or
    // the direction to a directional light
    public float ir, ig, ib;        // intensity of the light source

    public Light(int type, Vector3D v, float r, float g, float b) {
        this.lightType = type;
        this.ir = r;
        this.ig = g;
        this.ib = b;
        if (type != AMBIENT) {
            lvec = v;
            if (type == DIRECTIONAL) {
                lvec.normalize();
            }
        }
    }

    public int getLightType () { return lightType; }
    public float getRed() { return ir; }
    public float getGreen() { return ig; }
    public float getBlue() { return ib; }

    public void setRed(float r) { this.ir = r; }
    public void setGreen(float g) { this.ig = g; }
    public void setBlue(float b) { this.ib = b; }

    public Vector3D getLvec() { return lvec; }

    // Methods for type checks
    public boolean isAmbient() { return lightType == AMBIENT; }
    public boolean isDirectional() { return lightType == DIRECTIONAL; }
    public boolean isPoint() { return lightType == POINT; }
}
