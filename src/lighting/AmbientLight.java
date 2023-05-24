package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class represents the ambient light in the scene
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class AmbientLight extends Light{
    /** the default ambient light */
    public  static  AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * constructor for AmbientLight
     * @param iA the intensity of the ambient light
     * @param kA the attenuation factor of the ambient light
     */
    public AmbientLight(Color iA, Double3 kA){ super(iA.scale(kA)); }

    /**
     * constructor for AmbientLight
     * @param iA the intensity of the ambient light
     * @param kA the attenuation factor of the ambient light
     */
    public AmbientLight(Color iA, double kA){ super(iA.scale(kA)); }

    /**
     * default constructor for AmbientLight
     */
    public AmbientLight() { super(Color.BLACK); }
}
