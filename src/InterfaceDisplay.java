import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class InterfaceDisplay {
    private VBox root = new VBox();
    private HBox header = new HBox();
    private VBox ioRoot = new VBox();

    private TextField ringInput = new TextField();

    public InterfaceDisplay(Stage primaryStage) {
        //Set backgrounds
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("LightGray"), null, null);
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

        header.setStyle(CSS_Definitions.HBOX_STYLE);

        //Make header
        Label ringLabel = new Label("Ring = ");
        header.getChildren().add(ringLabel);
        header.getChildren().add(ringInput);

        //Construct root
        root.getChildren().add(header);
        root.getChildren().add(ioRoot);
    }

    public VBox getRoot() {
        return root;
    }
}
