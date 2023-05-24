package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class for ray tracer
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public abstract class RayTracerBase {
    protected Scene scene;
    /**
     * constructor
     * @param Sc
     */
    public  RayTracerBase(Scene Sc){ scene = Sc; }

    /**
     * calculate the color of the Point that closest to the beginning of the ray and intersect aim.
     * @param ray : pass through the view plane.
     * @return final color in the point.
     */
    public abstract Color traceRay(Ray ray);
}
