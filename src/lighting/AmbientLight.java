package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class represents the ambient light in the scene
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class AmbientLight {
    /** the default ambient light */
    public  static  AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);
    /** the intensity of the ambient light */
    private  Color intensity;

    /**
     * constructor for AmbientLight
     * @param iA the intensity of the ambient light
     * @param kA the attenuation factor of the ambient light
     */
    public AmbientLight(Color iA, Double3 kA){ intensity = iA.scale(kA); }

    /**
     * constructor for AmbientLight
     * @param iA the intensity of the ambient light
     * @param kA the attenuation factor of the ambient light
     */
    public AmbientLight(Color iA, double kA){ intensity = iA.scale(kA); }

    /**
     * getter for the intensity of the ambient light
     * @return the intensity of the ambient light
     */
    public Color getIntensity(){ return intensity; }
}
