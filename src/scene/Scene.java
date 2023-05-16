package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Class Scene is the class representing a scene in the 3D space.
 * It contains the name of the scene, the background color, the ambient light and the geometries.
 * The class is immutable.
 */
public class Scene {
    /**
     * The name of the scene.
     */
    public String name;
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
    public Geometries geometries = new Geometries();

    /**
     * Constructor for the Scene class receiving the name of the scene.
     * @param Name The name of the scene.
     */
    public  Scene(String Name){
        name = Name;
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
}
