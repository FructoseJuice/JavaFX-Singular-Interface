import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceDisplay {
    private final VBox root = new VBox();
    private final IONode ioNode;

    public InterfaceDisplay() {
        //Set backgrounds
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("LightGray"), null, null);
        Background greyBack = new Background(backgroundFill);

        VBox ioRoot = new VBox();
        ioRoot.setBackground(greyBack);

        //Construct root
        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), null, null)));

        root.getChildren().addAll(ioRoot);

        //Ensure IO nodes grow with window resize
        VBox.setVgrow(ioRoot, Priority.ALWAYS);

        //Create ionode
        ioNode = new IONode();
        ioRoot.getChildren().add(ioNode.getRootNode());

        VBox.setVgrow(ioNode.getRootNode(), Priority.ALWAYS);
    }

    public VBox getRoot() {
        return root;
    }

    public void compute() throws IOException, InterruptedException {
        ioNode.compute();
    }


    public void flushOut() {
        ioNode.flushOut();
    }
}
