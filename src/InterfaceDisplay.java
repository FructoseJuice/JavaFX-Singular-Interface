import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceDisplay {
    private final VBox root = new VBox();
    private final VBox ioRoot = new VBox();

    private final ShellNegotiator negotiator;

    private static int commandID = 0;

    public InterfaceDisplay() {
        //Background for nodes
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("LightGray"), null, null);
        Background greyBack = new Background(backgroundFill);


        //Make header
        HBox header = new HBox();

        //Set height and width properties
        header.setMinHeight(40);
        header.prefWidthProperty().bind(root.widthProperty());
        header.setStyle(CSS_Definitions.GRAY_BORDER_STYLE);

        //Create header nodes
        Button addNewIONodeButton = new Button("   +   ");
        addNewIONodeButton.setPadding(new Insets(5, 0, 5, 0));

        header.getChildren().add(addNewIONodeButton);

        //Construct root
        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("Black"), null, null)));
        root.getChildren().addAll(header, ioRoot);

        //Ensure IO nodes grow with window resize
        VBox.setVgrow(ioRoot, Priority.ALWAYS);

        //Create first io node
        IONode ioNode = addIONodeToDisplay();


        //Set backgrounds
        header.setBackground(greyBack);
        ioRoot.setBackground(greyBack);

        //Set button event listeners
        addNewIONodeButton.setOnMouseClicked(event -> {
            addIONodeToDisplay();
        });

        //Start process
        negotiator = new ShellNegotiator(ioNode.getOutNode());
    }


    public IONode addIONodeToDisplay() {
        IONode node = new IONode(commandIDToString(commandID), commandID++);

        VBox.setVgrow(node.getRootNode(), Priority.ALWAYS);
        ioRoot.getChildren().add(node.getRootNode());

        //Create event listeners
        node.getRemoveNodeButton().setOnMouseClicked(event -> removeIONodeToDisplay(node));

        node.getInNode().setOnKeyPressed(event -> {
            if (!event.isControlDown()) return;

            if (event.getCode() == KeyCode.C) {
                try {
                    compute(node);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return node;
    }


    public void removeIONodeToDisplay(IONode nodeToRemove) {
        ioRoot.getChildren().remove(nodeToRemove.getRootNode());
    }

    public VBox getRoot() {
        return root;
    }

    public void compute(IONode node) throws IOException, InterruptedException {
        String command = node.getInNode().getText();

        //Clear output and update with command ID
        node.getOutNode().setText(commandIDToString(node.getMyCommandID()));
        negotiator.sendInputToProcess(command, node.getOutNode());

        incrementCommandId(node);
    }

    public void incrementCommandId(IONode node) {
        node.updateCommandID(commandIDToString(commandID), ++commandID);
    }

    public String commandIDToString(int num) {
        return String.format("[%03d]", num);
    }
}
