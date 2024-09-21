import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class InterfaceDisplay {
    private TextField ringInput = new TextField();

    private final VBox root = new VBox();
    private HBox header = new HBox();
    private final VBox ioRoot = new VBox(5);
    private Set<IONode> ioNodesSet = new HashSet<>();

    private Button addNewIONodeButton = new Button("   +   ");

    public InterfaceDisplay(Stage primaryStage) {
        //Set backgrounds
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("LightGray"), null, null);
        Background greyBack = new Background(backgroundFill);

        //Set background of header and IO section
        header.setBackground(greyBack);
        ioRoot.setBackground(greyBack);

        //Set height and width properties
        //root.prefWidthProperty().bind(primaryStage.widthProperty());
        header.setMinHeight(50);
        header.prefWidthProperty().bind(root.widthProperty());

        header.setStyle(CSS_Definitions.HBOX_STYLE);

        //Make header
        Label ringLabel = new Label("Ring = ");
        ringInput.setPromptText("Input Ring");
        header.getChildren().add(ringLabel);
        header.getChildren().add(ringInput);

        //Construct root
        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), null, null)));
        addNewIONodeButton.setPadding(new Insets(5, 0, 5, 0));
        root.getChildren().addAll(header, ioRoot, addNewIONodeButton);

        //Set event listener on button
        addNewIONodeButton.setOnMouseClicked(event -> {
            addIONodeToDisplay(primaryStage);
        });
    }

    public VBox getRoot() {
        return root;
    }

    public void addIONodeToDisplay(Stage stage) {
        IONode node = new IONode();

        ioNodesSet.add(node);
        ioRoot.getChildren().add(node.getRootNode());

        node.getRemoveNodeButton().setOnMouseClicked(event -> {
            removeIONodeToDisplay(stage, node);
        });

        setStageSize(stage, 1);
    }

    public void removeIONodeToDisplay(Stage stage, IONode nodeToRemove) {
        ioNodesSet.remove(nodeToRemove);
        ioRoot.getChildren().remove(nodeToRemove.getRootNode());

        setStageSize(stage, -1);
    }

    private void setStageSize(Stage stage, int operation) {
        stage.setWidth(stage.getWidth());
        stage.setHeight(stage.getHeight() + operation*93 + operation*addNewIONodeButton.getHeight());
        stage.setMinHeight(stage.getHeight());
        stage.setMaxHeight(stage.getHeight());
    }
}
