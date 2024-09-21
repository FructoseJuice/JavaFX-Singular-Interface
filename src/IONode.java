import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class IONode {
    private final VBox root = new VBox(2);
    private final DynamicTextArea inNode = new DynamicTextArea();
    private final DynamicTextArea outNode = new DynamicTextArea();
    private final Button removeNodeButton = new Button("-");

    private final ShellNegotiator negotiator = new ShellNegotiator();
    private final TextField ringTextField;

    public IONode(TextField ringTextField) {
        this.ringTextField = ringTextField;

        inNode.setPromptText("Input Command");
        outNode.setPromptText("Command Output");
        outNode.setEditable(false);

        //Set in and out nodes on root node
        HBox inRootNode = new HBox();
        HBox outRootNode = new HBox();
        HBox buttonsRootNode = new HBox();
        Label inLabel = new Label("   in: ");
        Label outLabel = new Label("out: ");

        inRootNode.getChildren().addAll(inLabel, inNode);
        outRootNode.getChildren().addAll(outLabel, outNode);

        //Create spacer between buttons and construct root hbox
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button computeButton = new Button("Compute");
        buttonsRootNode.getChildren().addAll(computeButton, spacer, removeNodeButton);

        //Set Horizontal and Vertical growth for nodes
        HBox.setHgrow(inNode, Priority.ALWAYS);
        HBox.setHgrow(outNode, Priority.ALWAYS);
        HBox.setHgrow(buttonsRootNode, Priority.ALWAYS);

        VBox.setVgrow(inRootNode, Priority.ALWAYS);
        VBox.setVgrow(outRootNode, Priority.ALWAYS);
        VBox.setVgrow(inNode, Priority.ALWAYS);
        VBox.setVgrow(outNode, Priority.ALWAYS);


        //Set nodes on root
        root.getChildren().addAll(inRootNode, outRootNode, buttonsRootNode);
        root.setPadding(new Insets(3, 3, 3, 3));
        root.setStyle(CSS_Definitions.GRAY_BORDER_STYLE);

        computeButton.setOnMouseClicked(event -> {
            try {
                compute();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public VBox getRootNode() {
        return root;
    }

    public Button getRemoveNodeButton() {return removeNodeButton;}

    public void compute() throws IOException, InterruptedException {
        String command = inNode.getText();

        //Process command
        if (!command.isEmpty() && !command.isBlank()) {
            String ring = ringTextField.getText();

            //Execute command in terminal
            //If ring field is empty, populate it with default 0
            String out = negotiator.executeCommand(command, (ring.isBlank()) ? "0" : ring);

            outNode.setText(Objects.requireNonNullElse(out, "null"));
        }
    }
}
