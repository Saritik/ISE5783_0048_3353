package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class represents the directional light in the scene
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class DirectionalLight extends Light implements LightSource{
    // the direction of the light
    private Vector direction;

    /**
     * constructor for DirectionalLight
     * @param intensity the intensity of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction){
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p){ return super.getIntensity(); }

    @Override
    public Vector getL(Point p){ return direction; }
}
