import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.Objects;

public class IONode {
    private final VBox root = new VBox(1);

    private final DynamicTextArea inNode = new DynamicTextArea();
    private final TextArea outNode = new TextArea();

    private final Button removeNodeButton = new Button("-");

    public IONode() {
        inNode.setPromptText("Input Command");
        outNode.setPromptText("Command Output");
        outNode.setEditable(false);

        inNode.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        outNode.setFont(Font.font("Arial", FontWeight.NORMAL, 20));

        setOutNodeProperties();

        //Set in and out nodes on root node
        HBox inRootNode = new HBox();
        HBox outRootNode = new HBox();
        Label inLabel = new Label("   in: ");
        Label outLabel = new Label("out: ");

        inRootNode.getChildren().addAll(inLabel, inNode);
        outRootNode.getChildren().addAll(outLabel, outNode);

        //Trailer for removal button
        HBox trailer = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        trailer.getChildren().addAll(spacer, removeNodeButton);


        //Set Horizontal and Vertical growth for nodes
        HBox.setHgrow(inNode, Priority.ALWAYS);
        HBox.setHgrow(outNode, Priority.ALWAYS);

        VBox.setVgrow(inRootNode, Priority.ALWAYS);
        VBox.setVgrow(outRootNode, Priority.ALWAYS);
        VBox.setVgrow(inNode, Priority.ALWAYS);
        VBox.setVgrow(outNode, Priority.ALWAYS);


        //Set nodes on root
        root.getChildren().addAll(inRootNode, outRootNode, trailer);
        root.setPadding(new Insets(1, 5, 3, 3));
        root.setStyle(CSS_Definitions.GRAY_BORDER_STYLE);
    }

    public VBox getRootNode() {
        return root;
    }

    public TextArea getInNode() {
        return inNode;
    }

    public TextArea getOutNode() {
        return outNode;
    }

    public Button getRemoveNodeButton() {return removeNodeButton;}


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
