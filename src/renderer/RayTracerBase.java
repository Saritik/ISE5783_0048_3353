package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/** A class that represent a ray tracer*/
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
