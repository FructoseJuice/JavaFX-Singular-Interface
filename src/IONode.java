import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class IONode {
    private VBox root = new VBox();
    private TextField inNode = new TextField("input");
    private TextFlow outNode = new TextFlow();
    private Button computeButton = new Button("Compute");

    public IONode() {
        root.getChildren().add(inNode);
        root.getChildren().add(outNode);
        root.getChildren().add(computeButton);
    }

    public VBox getRootNode() {
        return root;
    }
}
