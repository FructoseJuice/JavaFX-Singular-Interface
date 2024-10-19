import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class InstallerDisplayManager {
    private static final Label filePathLabel = new Label("");
    private static Button installerButton = null;

    public static void launchDisplay(Stage primaryStage) {
        VBox displayRoot = new VBox();

        Label fileChooserLabel = new Label("Please choose Singular Executable file path.");
        Button fileChooserButton = new Button("Choose File");


        fileChooserButton.setOnMouseClicked(event -> {
            showSingularFileChooserDialogue(primaryStage, displayRoot);
        });


        //Set graphical alignment of nodes
        fileChooserLabel.setAlignment(Pos.CENTER);
        fileChooserButton.setAlignment(Pos.CENTER);
        filePathLabel.setAlignment(Pos.CENTER);

        displayRoot.getChildren().addAll(fileChooserLabel, fileChooserButton, filePathLabel);
        displayRoot.setAlignment(Pos.CENTER);
        displayRoot.setSpacing(10);

        Scene root = new Scene(displayRoot);
        primaryStage.setScene(root);
        primaryStage.setTitle("JavaFX Singular Interface Installer");
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(300);
        primaryStage.show();
    }

    private static void createInstallerButton(Stage primaryStage, VBox displayRoot, String[] singularExecPath) {
        installerButton = new Button("Install");
        installerButton.setOnMouseClicked(event -> {
            try {
                if (Installer.createJar(singularExecPath)) {
                    filePathLabel.setText("Successful install.");
                    installerButton.setDisable(true);

                    createNextButton(primaryStage, displayRoot);
                } else {
                    filePathLabel.setText("Error creating jar.");
                }
            } catch (IOException e) {
                filePathLabel.setText("Error.");
                throw new RuntimeException(e);
            }
        });

        displayRoot.getChildren().add(installerButton);
    }

    private static void createNextButton(Stage primaryStage, VBox displayRoot) {
        Button nextButton = new Button("Next");

        nextButton.setOnMouseClicked(event -> {
            //Purge all nodes from display
            while (!displayRoot.getChildren().isEmpty()) displayRoot.getChildren().removeFirst();

            //Add new nodes to root
            displayRoot.getChildren().add(new Label("Select MCGB Directory (Optional):"));
            createMCGBDirChooser(primaryStage, displayRoot);
        });

        displayRoot.getChildren().add(nextButton);
    }

    private static void createMCGBDirChooser(Stage primaryStage, VBox displayRoot) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setTitle("Choose MCGB Directory");
        Director
    }


    private static void showSingularFileChooserDialogue(Stage primaryStage, VBox displayRoot) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose Singular Executable File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        String chosenPath = selectedFile.getAbsoluteFile().getPath();
        String[] singularExecPath = null;

        // Check if this file is indeed singular
        String[] splitPath = chosenPath.toLowerCase().split("/");
        if (!splitPath[splitPath.length-1].contains("singular")) {
            filePathLabel.setText("Invalid File Path. Not a Singular executable.");
            return;
        }

        // If windows linux subsystem split up
        if (chosenPath.contains("wsl")) {
            singularExecPath = Installer.parseWSLPath(chosenPath);
            filePathLabel.setText("File Location: " + singularExecPath[1]);
        } else {
            // Raw file location on Linux
            singularExecPath = new String[]{chosenPath};
            filePathLabel.setText("File Location: " + chosenPath);
        }

        //Create installer button if it doesn't exist
        if (installerButton == null) {
            createInstallerButton(primaryStage, displayRoot, singularExecPath);
        }
    }
}
