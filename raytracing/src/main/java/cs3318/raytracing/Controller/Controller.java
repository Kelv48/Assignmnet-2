package cs3318.raytracing.Controller;

import cs3318.raytracing.Framework.Scene;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Controller {
    private int toggleValue = 0;  // Integer to store toggle state (0 or 1)
    private Scene scene;
    public ImageView renderedImage;
    private Stage stage;
    private Driver sceneToRender;
    private SceneDriver sceneToRender2;
    boolean finished = false;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void run() {
        if (sceneToRender == null && sceneToRender2 == null) {
            System.err.println("Error: sceneToRender is not initialized.");
            return;
        }
        if (sceneToRender != null) {
            long time = System.currentTimeMillis();
            for (int j = 0; j < sceneToRender.height; j += 1) {
                for (int i = 0; i < sceneToRender.width; i += 1) {
                    sceneToRender.renderPixel(i, j);
                }
            }
            renderedImage.setImage(sceneToRender.getRenderedImage());
            time = System.currentTimeMillis() - time;
            System.err.println("Rendered in "+(time/60000)+":"+((time%60000)*0.001));
            finished = true;
        } else if (sceneToRender2 != null) {
            long time = System.currentTimeMillis();
            for (int j = 0; j < sceneToRender2.height; j += 1) {
                for (int i = 0; i < sceneToRender2.width; i += 1) {
                    sceneToRender2.renderPixel(i, j);
                }
            }
            renderedImage.setImage(sceneToRender2.getRenderedImage());
            time = System.currentTimeMillis() - time;
            System.err.println("Rendered in "+(time/60000)+":"+((time%60000)*0.001));
            finished = true;
        }
    }

    public void toggleValue() {
        toggleValue = (toggleValue == 0) ? 1 : 0;
        System.out.println("Toggled value: " + toggleValue);
    }


    public void startRayTrace(ActionEvent actionEvent) {
         // Sets whether to read from a file or from the scene class
        if (toggleValue == 0) {
            sceneToRender = new Driver((int) renderedImage.getFitWidth(),
                    (int) renderedImage.getFitHeight(),
                    "resources/SceneToRender.txt");
            this.run();
        } else if (toggleValue == 1) {
            if (scene != null) {
                // Create the Driver with the Scene
                sceneToRender2 = new SceneDriver((int) renderedImage.getFitWidth(),
                        (int) renderedImage.getFitHeight(),
                        scene);

                // Run the rendering process
                this.run();
            }
        }
    }
}
