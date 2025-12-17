import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Earth extends Group {
    private ArrayList<Sphere> yellowSphere;
    private Rotate ry = new Rotate();
    private Sphere sph;
    private static final double RADIUS = 300;
    private double currentAngle = 0;

    public Sphere getEarth() {
        return sph;
    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    public Earth() {
        yellowSphere = new ArrayList<>();
        sph = new Sphere(RADIUS);

        PhongMaterial skin = new PhongMaterial();
        try {
            skin.setDiffuseMap(new Image("file:data/earth_lights_4800.png"));
            skin.setSelfIlluminationMap(new Image("file:data/earth_lights_4800.png"));
        } catch (Exception e) {
            System.out.println("Texture non trouvée, utilisation de couleur par défaut");
            skin.setDiffuseColor(Color.LIGHTBLUE);
        }

        sph.setMaterial(skin);
        this.getChildren().add(sph);
        this.getTransforms().add(ry);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ry.setAxis(new Point3D(0, 1, 0));
                currentAngle = l / 50000000.0;
                ry.setAngle(currentAngle);
            }
        };
        animationTimer.start();
    }

    public Sphere createSphere(Aeroport a, Color color) {
        return createSphere(a.getLatitude(), a.getLongitude(), color);
    }

    public Sphere createSphere(double latitude, double longitude, Color color) {
        PhongMaterial col = new PhongMaterial();
        col.setSpecularColor(color);
        col.setDiffuseColor(color);

        // Rayon 2 selon le sujet page 9
        Sphere coloredSphere = new Sphere(2);
        coloredSphere.setMaterial(col);

        double theta = Math.toRadians(latitude);
        double phi = Math.toRadians(longitude);

        double r = RADIUS;

        double x = r * Math.cos(theta) * Math.sin(phi);
        double y = -r * Math.sin(theta);
        double z = -r * Math.cos(theta) * Math.cos(phi);

        coloredSphere.setTranslateX(x);
        coloredSphere.setTranslateY(y);
        coloredSphere.setTranslateZ(z);

        System.out.println("Sphère créée à latitude=" + latitude + ", longitude=" + longitude);

        return coloredSphere;
    }

    public void displayRedSphere(Aeroport a) {
        Sphere redSphere = createSphere(a, Color.RED);
        this.getChildren().add(redSphere);
        System.out.println("Sphère rouge ajoutée pour: " + a.getIATA());
    }

    public void displayYellowSphere(Aeroport a) {
        Sphere yellowSphereObj = createSphere(a, Color.YELLOW);
        this.getChildren().add(yellowSphereObj);
        System.out.println("Sphère jaune ajoutée pour: " + a.getIATA());
    }

    public void displayYellowSphere(ArrayList<Flight> list) {
        yellowSphere.clear();

        for (Flight f : list) {
            if (f.getDeparture() != null) {
                Sphere current = createSphere(f.getDeparture(), Color.YELLOW);
                this.getChildren().add(current);
            }
        }
    }

    public void displayBlueSphere() {
        yellowSphere.clear();

        for (int latitude = -90; latitude < 90; latitude += 20) {
            for (int longitude = -180; longitude < 180; longitude += 20) {
                Sphere current = createSphere(latitude, longitude, Color.BLUE);
                this.getChildren().add(current);
            }
        }
    }
}