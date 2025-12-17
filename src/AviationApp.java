import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Translate;
import javafx.scene.PerspectiveCamera;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AviationApp extends Application {
    private PerspectiveCamera camera;
    private double initialMouseY;
    private World world;
    private static final String ACCESS_KEY = "9935378ccc86219961f13c777b47d311"; // ✅ NOUVELLE CLÉ

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("3D Carte du Monde - Aviation Stack");

        world = new World("data/airport-codes_no_comma.csv");
        System.out.println("Aéroports chargés: " + world.getList().size());

        Earth earth = new Earth();
        Scene theScene = new Scene(earth, 1000, 800, true);

        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-900);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(45);

        theScene.setCamera(camera);

        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                initialMouseY = event.getSceneY();
            }

            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                double deltaY = event.getSceneY() - initialMouseY;
                Translate translate = new Translate();
                translate.setZ(deltaY * 0.5);
                camera.getTransforms().add(translate);
                initialMouseY = event.getSceneY();
            }

            if (event.getButton() == MouseButton.SECONDARY &&
                    event.getEventType() == MouseEvent.MOUSE_CLICKED) {

                System.out.println("\n=== Clic droit détecté ===");

                if (event.getPickResult().getIntersectedNode() != null) {
                    if (event.getPickResult().getIntersectedTexCoord() != null) {
                        double textureX = event.getPickResult().getIntersectedTexCoord().getX();
                        double textureY = event.getPickResult().getIntersectedTexCoord().getY();

                        double latitude = 180 * (0.5 - textureY);
                        double longitude = 360 * (textureX - 0.5);

                        System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);

                        Aeroport nearest = world.findNearestAirport(longitude, latitude);
                        if (nearest != null) {
                            System.out.println("Aéroport trouvé: " + nearest);
                            earth.displayRedSphere(nearest);

                            // Récupérer les vols depuis l'API
                            recupererVols(nearest, earth, world);
                        }
                    }
                }
            }
        });

        primaryStage.setScene(theScene);
        primaryStage.show();
    }

    private void recupererVols(Aeroport aeroport, Earth earth, World world) {
        // Lancer dans un thread séparé pour ne pas bloquer l'interface
        new Thread(() -> {
            try {
                String apiUrl = "http://api.aviationstack.com/v1/flights?access_key=" + ACCESS_KEY + "&arr_iata=" + aeroport.getIATA();

                System.out.println("Appel API: " + apiUrl);

                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

                String jsonResponse = sb.toString();
                System.out.println("Réponse reçue: " + jsonResponse.length() + " caractères");

                // Parser les vols
                JsonFlightFiller filler = new JsonFlightFiller(jsonResponse, world);
                System.out.println("Nombre de vols trouvés: " + filler.getList().size());

                // Afficher les sphères jaunes pour les aéroports de départ
                // ✅ Utiliser Platform.runLater() pour revenir au thread FX
                for (Flight f : filler.getList()) {
                    if (f.getDeparture() != null) {
                        System.out.println("Affichage sphère jaune pour: " + f.getDeparture().getIATA());
                        // Revenir au thread JavaFX pour modifier l'UI
                        Platform.runLater(() -> {
                            earth.displayYellowSphere(f.getDeparture());
                        });
                    }
                }

            } catch (Exception e) {
                System.out.println("Erreur lors de l'appel API: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}