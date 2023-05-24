package lighting;

import primitives.Color;

/**
 * abstract class for all the lights in the scene
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
abstract class Light {
    /** the intensity of the ambient light */
    private Color intensity;

    /**
     * constructor for the light
     * @param intensity the intensity of the ambient light
     */
    protected Light(Color intensity){ this.intensity = intensity; }

    /**
     * getter for the intensity of the ambient light
     * @return the intensity of the ambient light
     */
    public  Color getIntensity(){ return intensity; }
}
