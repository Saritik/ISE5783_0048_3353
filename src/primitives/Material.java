package primitives;

/**
 * Material class represents the material of a geometry
 * @author Sarit Tik 213230048 saritik16@gmail.com
 * @author Hadas Zehevi 325543353 h0548510062@gmail.com
 */
public class Material {
    // the specular coefficient of the material
    public Double3 kS = Double3.ZERO;
    // the diffuse coefficient of the material
    public Double3 kD = Double3.ZERO;
    public Double3 kT = Double3.ZERO;
    public Double3 kR = Double3.ZERO;
    // the shininess of the material
    public int nShininess;
    

    //blur glass
    //all parameters for the beam
    public int numOfRays=1;
    public double blurGlassDistance=1,blurGlassRadius=1;



    /**
     * set the specular coefficient of the material
     * @param kS the specular coefficient of the material
     * @return the material
     */
    public Material setKs(Double3 kS){
        this.kS = kS;
        return this;
    }

    /**
     * set the specular coefficient of the material
     * @param kS the specular coefficient of the material
     * @return the material
     */
    public Material setKs(double kS){
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * set the diffuse coefficient of the material
     * @param kD the diffuse coefficient of the material
     * @return the material
     */
    public Material setKd(Double3 kD){
        this.kD = kD;
        return this;
    }

    /**
     * set the diffuse coefficient of the material
     * @param kD the diffuse coefficient of the material
     * @return the material
     */
    public Material setKd(double kD){
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * set the transparency coefficient of the material
     * @param kT the transparency coefficient of the material
     * @return the material
     */
    public Material setKt(Double3 kT){
        this.kT = kT;
        return this;
    }

    /**
     * set the transparency coefficient of the material
     * @param kT the transparency coefficient of the material
     * @return the material
     */
    public Material setKt(double kT){
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * set the reflection coefficient of the material
     * @param kR the reflection coefficient of the material
     * @return the material
     */
    public Material setKr(Double3 kR){
        this.kR = kR;
        return this;
    }

    /**
     * set the reflection coefficient of the material
     * @param kR the reflection coefficient of the material
     * @return the material
     */
    public Material setKr(double kR){
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * set the shininess of the material
     * @param nShininess the shininess of the material
     * @return the material
     */
    public Material setShininess(int nShininess){
        this.nShininess = nShininess;
        return this;
    }

    /*
    public Material setBlurGlass(int numOfRays, double distance ,double radius) {

        if(numOfRays<1 || distance<=0 ||  radius<=0)
            throw new IllegalArgumentException("illegal argument in setBlurGlass ");

        this.numOfRays = numOfRays;
        this.blurGlassDistance=distance;
        this.blurGlassRadius = radius;

        return this;
    }
     */
}
