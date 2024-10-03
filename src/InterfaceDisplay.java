import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
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

        //Create file creation nodes
        Button saveSessionIOBUtton = new Button("Save Session I/O");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(addNewIONodeButton, spacer, saveSessionIOBUtton);

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

        saveSessionIOBUtton.setOnMouseClicked(event -> {
            try {
                flushSessionIO();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            } if (event.getCode() == KeyCode.F) {
                node.flushIn();
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
        String commandId = commandIDToString(node.getMyCommandID()) + "\n";

        //Clear output and update with command ID
        node.getOutNode().setText("COMMAND #: " + commandId);
        negotiator.sendInputToProcess(command, node.getOutNode(), commandId);

        incrementCommandId(node);
    }

    public void incrementCommandId(IONode node) {
        node.updateCommandID(commandIDToString(commandID), ++commandID);
    }

    public String commandIDToString(int num) {
        return String.format("[%03d]", num);
    }

    public boolean flushSessionIO() throws IOException {
        if (!Main.verifyExistenceOfOutputDirectory()) {
            System.out.println("Could not write files. \nDirectory does not Exist.");
            return false;
        }

        StringBuilder in = negotiator.getSessionInput();
        StringBuilder out = negotiator.getSessionOutput();

        if (in.isEmpty() && out.isEmpty()) {
            return true;
        }

        if (!Main.createIOFiles()) {
            System.out.println("Could not create files.");
            return false;
        }

        File inFile = Main.In_File;
        File outFile = Main.Out_File;

        //Flush out strings
        try (FileWriter fileWriter = new FileWriter(inFile, true)) { // 'true' to append
            fileWriter.write(in.toString());
            System.out.println("Contents appended to the file successfully.");
            //Flush string builder
            in.setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (FileWriter fileWriter = new FileWriter(outFile, true)) { // 'true' to append
            String[] lines = out.toString().split("\n");

            //Remove singular output
            if (lines.length != 0 && lines[1].contains("SINGULAR")) {
                //Remove the first 6 lines of input
                for (int i = 1; i < 12; i++) {
                    lines[i] = "";
                }
            }

            for (String line : lines) {
                if (!line.isEmpty()) {
                    fileWriter.write(line + "\n");
                }
            }

            out.setLength(0);
            System.out.println("Contents appended to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
