import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        InterfaceDisplay interfaceDisplay = new InterfaceDisplay();

        //Construct stage
        Scene scene = new Scene(interfaceDisplay.getRoot());
        scene.setFill(Paint.valueOf("Black"));


        //Set event filter for Ctrl+C to compute
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (!event.isControlDown()) return;

            switch(event.getCode()) {
                case KeyCode.C -> {
                    try {
                        interfaceDisplay.compute();
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                case KeyCode.F -> {
                    interfaceDisplay.flushOut();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(150);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }
}
