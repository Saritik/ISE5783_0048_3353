package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * class for the spot light
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class SpotLight extends PointLight{

    //direction of the spot light
    private final Vector direction;

    /**
     * constructor
     * @param intensity color of the light
     * @param position position of the light
     * @param direction direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction){
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) throws IllegalArgumentException
    {
        double pl = alignZero(direction.dotProduct(getL(p)));
        //if the point is behind the light
        if(getL(p) == null)
            return Color.BLACK;
        //if the point is not in the direction of the light
        if (pl <= 0)
            return Color.BLACK;

        return super.getIntensity(p).scale(pl);
    }
}
