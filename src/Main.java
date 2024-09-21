import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        InterfaceDisplay interfaceDisplay = new InterfaceDisplay(primaryStage);

        //Construct stage
        Scene scene = new Scene(interfaceDisplay.getRoot());
        scene.setFill(Paint.valueOf("Black"));

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(110);
        primaryStage.setMaxHeight(110);
        primaryStage.setMinWidth(575);
        primaryStage.show();
    }
}
