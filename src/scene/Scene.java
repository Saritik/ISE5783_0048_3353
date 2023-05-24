package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Class Scene is the class representing a scene in the 3D space.
 * It contains the name of the scene, the background color, the ambient light and the geometries.
 * The class is immutable.
 *
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public final String name;
    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;
    /**
     * The ambient light of the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * The geometries of the scene.
     */
    public Geometries geometries;

    /**
     * The lights of the scene.
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructor for the Scene class receiving the name of the scene.
     * @param Name The name of the scene.
     */
    public  Scene(String Name){
        name = Name;
        geometries = new Geometries();
    }

    /**
     * Constructor for the Scene class receiving the background color.
     * @param back The background color of the scene.
     */
    public Scene setBackground(Color back){
        background = back;
        return this;
    }

    /**
     * Function setAmbientLight sets the ambient light of the scene.
     * @param ambient The ambient light of the scene.
     * @return The scene.
     */
    public Scene setAmbientLight(AmbientLight ambient){
        ambientLight = ambient;
        return this;
    }

    /**
     * Function setGeometries sets the geometries of the scene.
     * @param geo The geometries of the scene.
     * @return The scene.
     */
    public  Scene setGeometries(Geometries geo){
        geometries = geo;
        return this;
    }

    /**
     * Function setLights sets the lights of the scene.
     * @param lights The lights of the scene.
     * @return The scene.
     */
    public Scene setLights(List<LightSource> lights){
        this.lights = lights;
        return this;
    }
}
