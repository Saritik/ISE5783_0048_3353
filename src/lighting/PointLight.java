package lighting;

import primitives.*;

/**
 * PointLight class represents a point light source
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class PointLight extends Light implements LightSource{
    // Point light source position
    private final Point position;

    // Factors for attenuation
    private double kQ = 0;
    private double kL = 0;
    private double kC = 1;

    /**
     * Constructor for PointLight class
     * @param intensity Color intensity
     * @param position Point light source position
     */
    public PointLight(Color intensity, Point position){
        super(intensity);
        this.position = position;
    }

    /**
     * Set the attenuation factors
     * @param kQ Constant factor
     * @return The PointLight object itself
     */
    public PointLight setKq(double kQ){
        this.kQ = kQ;
        return this;
    }

    /**
     * Set the attenuation factors
     * @param kL Constant factor
     * @return The PointLight object itself
     */
    public PointLight setKl(double kL){
        this.kL = kL;
        return this;
    }

    /**
     * Set the attenuation factors
     * @param kC Constant factor
     * @return The PointLight object itself
     */
    public PointLight setKc(double kC){
        this.kC = kC;
        return this;
    }

    @Override
    public Color getIntensity(Point p){
        return getIntensity().reduce((kC + kL * (p.distance(position)) + kQ * p.distanceSquared(position)));
    }

    @Override
    public Vector getL(Point p) throws IllegalArgumentException
    {
        // Check if the point is the same as the light source position
        if (p.equals(position)) return null;

        return p.subtract(position).normalize();
    }
}
