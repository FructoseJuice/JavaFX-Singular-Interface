import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Objects;

public class IONode {
    private VBox root = new VBox(2);
    private DynamicTextArea inNode = new DynamicTextArea();
    private DynamicTextArea outNode = new DynamicTextArea();
    private Button computeButton = new Button("Compute");
    private Button removeNodeButton = new Button("-");

    private ShellNegotiator negotiator = new ShellNegotiator();

    public IONode() {
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
        buttonsRootNode.getChildren().addAll(computeButton, spacer, removeNodeButton);

        HBox.setHgrow(inNode, Priority.ALWAYS);
        HBox.setHgrow(outNode, Priority.ALWAYS);
        HBox.setHgrow(buttonsRootNode, Priority.ALWAYS);

        root.getChildren().addAll(inRootNode, outRootNode, buttonsRootNode);
        root.setPadding(new Insets(3, 3, 3, 3));

        computeButton.setOnMouseClicked(event -> {
            try {
                compute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public VBox getRootNode() {
        return root;
    }

    public Button getRemoveNodeButton() {return removeNodeButton;}

    public void compute() throws IOException {
        String command = inNode.getText();

        if (!command.isEmpty() && !command.isBlank()) {
            String out = negotiator.executeCommand(command);

            outNode.setText(Objects.requireNonNullElse(out, "null"));
        }
    }
}
