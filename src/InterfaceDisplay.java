import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class InterfaceDisplay {
    private final TextField ringInput = new TextField();

    private final VBox root = new VBox();
    private final VBox ioRoot = new VBox();

    public InterfaceDisplay() {
        HBox header = new HBox();

        //Set backgrounds
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("LightGray"), null, null);
        Background greyBack = new Background(backgroundFill);

        header.setBackground(greyBack);
        ioRoot.setBackground(greyBack);


        //Set height and width properties
        header.setMinHeight(50);
        header.prefWidthProperty().bind(root.widthProperty());
        header.setStyle(CSS_Definitions.GRAY_BORDER_STYLE);


        Button addNewIONodeButton = new Button("   +   ");
        addNewIONodeButton.setPadding(new Insets(5, 0, 5, 0));

        //Populate header with components
        Label ringLabel = new Label("Ring = ");
        ringInput.setPromptText("Input Ring. Default = 0.");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(ringLabel, ringInput, spacer, addNewIONodeButton);


        //Construct root
        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), null, null)));

        root.getChildren().addAll(header, ioRoot);

        //Ensure IO nodes grow with window resize
        VBox.setVgrow(ioRoot, Priority.ALWAYS);

        //Set event listener on button
        addNewIONodeButton.setOnMouseClicked(event -> {
            addIONodeToDisplay();
        });
    }

    public VBox getRoot() {
        return root;
    }

    public void addIONodeToDisplay() {
        IONode node = new IONode(ringInput);

        VBox.setVgrow(node.getRootNode(), Priority.ALWAYS);
        ioRoot.getChildren().add(node.getRootNode());

        node.getRemoveNodeButton().setOnMouseClicked(event -> {
            removeIONodeToDisplay(node);
        });

        resizeStage();
    }

    public void removeIONodeToDisplay(IONode nodeToRemove) {
        ioRoot.getChildren().remove(nodeToRemove.getRootNode());
        resizeStage();
    }

    private void resizeStage() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.sizeToScene();
        stage.setMinHeight(stage.getHeight());
    }
}
