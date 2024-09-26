import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class IONode {
    private final VBox root = new VBox(5);

    private final DynamicTextArea inNode = new DynamicTextArea();
    private final TextArea outNode = new TextArea();

    private final ShellNegotiator negotiator;

    public IONode() {
        negotiator = new ShellNegotiator(outNode);

        inNode.setPromptText("Input Command");
        outNode.setPromptText("Command Output");
        outNode.setEditable(false);

        setOutNodeProperties();

        //Set in and out nodes on root node
        HBox inRootNode = new HBox();
        HBox outRootNode = new HBox();
        HBox buttonsRootNode = new HBox(25);
        Label inLabel = new Label("   in: ");
        Label outLabel = new Label("out: ");

        inRootNode.getChildren().addAll(inLabel, inNode);
        outRootNode.getChildren().addAll(outLabel, outNode);


        Button computeButton = new Button("Compute");
        Button clearIn = new Button("Clear In");
        Button clearOut = new Button("Clear Out");
        buttonsRootNode.getChildren().addAll(computeButton, clearIn);

        //Set Horizontal and Vertical growth for nodes
        HBox.setHgrow(inNode, Priority.ALWAYS);
        HBox.setHgrow(outNode, Priority.ALWAYS);
        HBox.setHgrow(buttonsRootNode, Priority.ALWAYS);

        VBox.setVgrow(inRootNode, Priority.ALWAYS);
        VBox.setVgrow(outRootNode, Priority.ALWAYS);
        VBox.setVgrow(inNode, Priority.ALWAYS);
        VBox.setVgrow(outNode, Priority.ALWAYS);


        //Set nodes on root
        root.getChildren().addAll(buttonsRootNode, inRootNode, outRootNode, clearOut);
        root.setPadding(new Insets(3, 3, 3, 3));
        root.setStyle(CSS_Definitions.GRAY_BORDER_STYLE);


        //BUTTON HANDLERS
        computeButton.setOnMouseClicked(event -> {
            try {
                compute();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        clearIn.setOnMouseClicked(event -> {
            flushIn();
        });

        clearOut.setOnMouseClicked(event -> {
            flushOut();
        });
    }

    public VBox getRootNode() {
        return root;
    }

    public void compute() throws IOException, InterruptedException {
        String command = inNode.getText();

        negotiator.sendInputToProcess(command);
    }

    public void flushOut() {
        outNode.setText("");
    }

    public void flushIn() {
        inNode.setText("");
    }

    private void setOutNodeProperties() {
        outNode.setWrapText(false);

        // Scroll to bottom whenever text changes
        outNode.textProperty().addListener((observable, oldValue, newValue) -> {
            outNode.setScrollTop(Double.MAX_VALUE);
        });
    }
}
