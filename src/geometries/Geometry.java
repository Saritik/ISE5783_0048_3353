package geometries;

import primitives.*;

/**
 * An abstract class for all the geometries
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public abstract class Geometry extends Intersectable {
    // The emission color of the geometry
    protected Color emission = Color.BLACK;
    // The material of the geometry
    private Material material = new Material();

    /**
     * Get the normal of the geometry at a given point
     * @param point the point
     * @return the normal vector
     */
    public abstract Vector getNormal(Point point);

    /**
     * Get the emission of the geometry
     * @return the emission color
     */
    public Color getEmission(){ return emission; }

    /**
     * Get the material of the geometry
     * @return the material
     */
    public Material getMaterial(){ return material; }

    /**
     * Set the emission of the geometry
     * @param emission the emission color
     * @return the geometry
     */
    public Geometry setEmission(Color emission){
        this.emission = emission;
        return this;
    }

    /**
     * Set the material of the geometry
     * @param material the material
     * @return the geometry
     */
    public Geometry setMaterial(Material material){
        this.material = material;
        return this;
    }
}
