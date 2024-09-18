import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class InterfaceDisplay {
    private VBox root = new VBox();
    private HBox header = new HBox();
    private VBox ioRoot = new VBox();

    public InterfaceDisplay(Stage primaryStage) {
        //Set backgrounds
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("Gray"), null, null);
        Background greyBack = new Background(backgroundFill);

        //Set background of header and IO section
        header.setBackground(greyBack);
        ioRoot.setBackground(greyBack);

        //Set height and width properties
        root.prefHeightProperty().bind(primaryStage.heightProperty());
        root.prefWidthProperty().bind(primaryStage.widthProperty());
        header.setMinHeight(50);
        header.prefWidthProperty().bind(root.widthProperty());
        VBox.setVgrow(root, Priority.ALWAYS);


        //Construct root
        root.getChildren().add(header);
        root.getChildren().add(ioRoot);
    }

    public VBox getRoot() {
        return root;
    }
}
