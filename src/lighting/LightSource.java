package lighting;

import primitives.*;

/**
 * LightSource interface is the basic interface representing a light source
 * affecting a geometry.
 *
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public interface LightSource {
    /**
     * @param p the point of the geometry
     * @return the intensity of the light in the point
     */
    Color getIntensity(Point p);

    /**
     * @param p the point of the geometry
     * @return the vector from the light source to the point
     */
    Vector getL(Point p);

    double getDistance(Point point);
}
