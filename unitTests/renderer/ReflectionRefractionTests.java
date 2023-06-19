package renderer;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Transparency.TRANSLUCENT;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
						.setMaterial(
								new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

		scene.lights
				.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
						.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
	
	/**
	 * my test for reflection and refraction
	 */
	@Test
	public void myShape() {
		Camera camera = new Camera(new Point(-330, 0, 5), new Vector(1, 0, 0), new Vector(0, 0, 1)).setVPSize(200d, 200)
				.setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255).reduce(6), new Double3(0.15))).lights
				.add(new SpotLight(new Color(RED), new Point(-300, 6, 10), new Vector(1, 0, 0)));

		double angle = 0;
		double height = 0;

		scene.geometries.add(new Plane(new Point(-4, 4, 0), new Vector(0, 0, 1))
				.setMaterial(new Material().setKd(0.8).setKs(0.6).setShininess(100).setKt(0.7).setKr(0.5)));

		java.awt.Color[] colors = { YELLOW, RED, ORANGE, BLUE };

		for (int i = 25; i < 200; ++i) {
			int colorIndex = i % colors.length;

			scene.geometries
					.add(new Sphere(new Point(i / 25.0 * Math.cos(angle), i / 25.0 * Math.sin(angle), height), 0.5)
							.setEmission(new Color(colors[colorIndex]).reduce(2.2))
							.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

			angle += Math.PI / 15.0;
			height += 0.15;
		}

		java.awt.Color[] colors2 = { BLUE, GREEN, PINK, BLACK, RED, GRAY };

		height = 10;
		for (int i = 25; i < 100; ++i) {
			int colorIndex = i % colors2.length;

			scene.geometries.add(new Sphere(new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height), 0.3)
					.setEmission(new Color(colors2[colorIndex]).reduce(2.2))
					.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(80).setKt(0.3)));

			angle += Math.PI / 30.0;
			height += 0.5;
		}

		height = 10;
		for (int i = 25; i < 300; ++i) {

			scene.geometries.add(new Sphere(new Point(i / 25.0 * Math.cos(angle), i * Math.sin(angle), height), 0.05)
					.setEmission(new Color(WHITE))
					.setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(1)));

			angle += Math.PI / 60.0;
			height += 0.1 % 50;
		}

		scene.lights
				.add(new SpotLight(new Color(255, 255, 255).reduce(2), new Point(-150, 0, 5), new Vector(1, 0, 0)));

		scene.lights.add(new SpotLight(new Color(GREEN).reduce(2), new Point(50, 0, 5), new Vector(1, 0, 0)));

		scene.setBackground(new Color(BLUE).reduce(TRANSLUCENT));

		ImageWriter imageWriter = new ImageWriter("myShape", 500, 500);
		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).renderImage().writeToImage();
	}


}
