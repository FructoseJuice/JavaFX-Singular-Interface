import javafx.application.Application;
import javafx.event.Event;
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

    private static final InterfaceDisplay interfaceDisplay = new InterfaceDisplay();

    @Override
    public void start(Stage primaryStage) {
        //Construct stage
        Scene scene = new Scene(interfaceDisplay.getRoot());
        scene.setFill(Paint.valueOf("Black"));


        //Set event filter for Ctrl+C to compute
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleHotkeyEvents);

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(580);
        primaryStage.show();
    }

    private void handleHotkeyEvents(KeyEvent event) {
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

            case KeyCode.D -> {
                interfaceDisplay.flushIn();
            }
        }
    }
}
