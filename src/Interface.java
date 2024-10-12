import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Interface extends Application {
    public static final String OUTPUT_PATH = System.getProperty("user.dir") + "/IO";
    public int Session_Number;
    public static File Out_File;
    public static File In_File;

    public static String[] SINGULAR_PATH;

    public static void main(String[] args) {
        //Parse input
        String command = args[0];
        SINGULAR_PATH = command.split("\\|");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        InterfaceDisplay interfaceDisplay = new InterfaceDisplay();

        verifyExistenceOfOutputDirectory();
        makeFilePaths();

        //Construct stage
        Scene scene = new Scene(interfaceDisplay.getRoot());
        scene.setFill(Paint.valueOf("Black"));

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(580);
        primaryStage.setTitle("Session #" + Session_Number);
        primaryStage.show();
    }

    public static boolean verifyExistenceOfOutputDirectory() {
        //Check if directory exists
        if (Files.notExists(Path.of(OUTPUT_PATH))) {
            File dir = new File(OUTPUT_PATH);

            if (!dir.mkdir()) {
                System.out.println("IO directory @" + OUTPUT_PATH + " Could not be created.");
                return false;
            } else {
                System.out.println("Created Directory @" + OUTPUT_PATH);
                return true;
            }
        }

        return true;
    }

    public void makeFilePaths() {
        //Create session # for this session
        Random random = new Random();
        int bound = 100;
        int numTries = 0;
        int randInt = random.nextInt(bound);

        //Check for existence of files
        String outPath = "_Out.txt";
        String inPath = "_In.txt";

        while (Files.exists(Path.of(OUTPUT_PATH + "/" + randInt + outPath)) ||
                Files.exists(Path.of(OUTPUT_PATH + "/" + randInt + inPath))) {
            numTries++;

            if (numTries >= 100) {
                bound *= 10;
            }

            randInt = random.nextInt(bound);
        }

        Session_Number = randInt;

        outPath = OUTPUT_PATH + "/" + "Session_#" + Session_Number + "_Out.txt";
        inPath = OUTPUT_PATH + "/" + "Session_#" + Session_Number + "_In.txt";

        Out_File = new File(outPath);
        In_File = new File(inPath);
    }

    public static boolean createIOFiles() throws IOException {
        //Check if files exist
        if (Out_File.exists() && In_File.exists()) {
            return true;
        }

        //Attempt to create files
        if (!Out_File.exists()) {
            return In_File.createNewFile();
        } else if (!In_File.exists()) {
            return Out_File.createNewFile();
        } else {
            return Out_File.createNewFile() && In_File.createNewFile();
        }
    }

    public int getSessionNumber() {
        return Session_Number;
    }
}
