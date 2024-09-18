import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        InterfaceDisplay interfaceDisplay = new InterfaceDisplay(primaryStage);

        //Construct stage
        Scene scene = new Scene(interfaceDisplay.getRoot());
        scene.setFill(Paint.valueOf("Black"));
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }
}
