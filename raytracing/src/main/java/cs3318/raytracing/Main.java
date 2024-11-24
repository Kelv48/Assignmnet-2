package cs3318.raytracing;

import cs3318.raytracing.Controller.Controller;
import cs3318.raytracing.Framework.Material.Surface;
import cs3318.raytracing.Framework.Math.Vector3D;
import cs3318.raytracing.Framework.Raytracing.Light;
import cs3318.raytracing.Framework.Scene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set scene
        Scene scene = new Scene();

        // Add objects to the scene
        Surface surface = new Surface(0.2f, 0.3f, 0.8f, 0.5f, 0.9f, 0.4f, 10.0f, 0f, 0f, 1f);
        scene.addSphere(surface, new Vector3D(-0.8f, 1.575f, -0.8f), 0.125f);

        Surface surface2 = new Surface(0.2f, 0.8f, 0.2f, 0.5f, 0.9f, 0.4f, 10.0f, 0, 0, 1);
        scene.addSphere(surface2, new Vector3D(-0.4f, 0.375f, -0.4f), 0.375f);

        Surface surface3 = new Surface(0.7f, 0.3f, 0.2f, 0.5f, 0.9f, 0.4f, 6.0f, 0f, 0f, 1f);
        scene.addSphere(surface3, new Vector3D(-0.6f, 1.05f, -0.6f), 0.3f);

        // Add lights to the scene
        scene.addLight(new Light(Light.AMBIENT, null, 1.0f, 1.0f, 0.981f));
        scene.addLight(new Light(Light.AMBIENT, null, 0.9f, 0.9f, 0.9f));
        scene.addLight(new Light(Light.AMBIENT, null, 0.745f, 0.859f, 0.224f));
        Vector3D lightDirection = new Vector3D(-1, -1, -1);
        scene.addLight(new Light(Light.DIRECTIONAL, lightDirection, 0.6f, 0.6f, 0.6f) );
        scene.addLight(new Light(Light.POINT, new Vector3D(10, 10, 10), 1.0f, 1.0f, 1.0f));

        // Set camera and scene parameters
        scene.eye = new Vector3D(1.5f, 10.5f, -1.5f);
        scene.lookat = new Vector3D(-0.5f, 0f, -0.5f);
        scene.up = new Vector3D(0f, 1f, 0f);
        scene.background = Color.BLACK;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/render.fxml"));
        Parent root = loader.load();
        Controller controller = (Controller) loader.getController();

        // set scene in controller
        controller.setScene(scene);

        primaryStage.setTitle("Simple Ray Tracing");
        primaryStage.setScene(new javafx.scene.Scene(root, 860, 640));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
