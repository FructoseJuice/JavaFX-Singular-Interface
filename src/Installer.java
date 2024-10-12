import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//TODO: MAKE THIS INTO JAR, IMPROVE CLEANUP

public class Installer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static final Label filePathLabel = new Label("");
    private static String[] singularExecutablePath = null;
    private static Button installerButton = null;

    @Override
    public void start(Stage primaryStage) {
        VBox displayRoot = new VBox();

        Label fileChooserLabel = new Label("Please choose Singular Executable file path.");
        Button fileButton = new Button("Choose File");

        fileButton.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Choose Singular Executable File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            String path = selectedFile.getAbsoluteFile().getPath();

            if (!path.contains("Singular")) {
                filePathLabel.setText("Invalid File Path. Not a Singular executable.");
            }

            // If windows split up
            if (path.contains("wsl")) {
                ArrayList<String> split = new ArrayList<>(List.of(path.split("\\\\")));
                String combinedPath = String.join("/", split.subList(4, split.size()));
                singularExecutablePath = new String[]{"wsl", "/" + combinedPath};
                filePathLabel.setText("File Location: " + combinedPath);
                System.out.println();
            } else {
                singularExecutablePath = new String[]{path};
            }

            //Create installer button
            if (installerButton == null) {
                installerButton = new Button("Install");
                installerButton.setOnMouseClicked(event1 -> {
                    try {
                        if (createJar(singularExecutablePath)) {
                            filePathLabel.setText("Successful install.");
                        }
                    } catch (IOException e) {
                        filePathLabel.setText("Error.");
                        throw new RuntimeException(e);
                    }
                });

                displayRoot.getChildren().add(installerButton);
            }
        });


        fileChooserLabel.setAlignment(Pos.CENTER);
        fileButton.setAlignment(Pos.CENTER);
        filePathLabel.setAlignment(Pos.CENTER);

        displayRoot.getChildren().addAll(fileChooserLabel, fileButton, filePathLabel);
        displayRoot.setAlignment(Pos.CENTER);
        displayRoot.setSpacing(10);

        Scene root = new Scene(displayRoot);
        primaryStage.setScene(root);
        primaryStage.setTitle("JavaFX Singular Interface Installer");
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(300);
        primaryStage.show();
    }

    private static boolean createJar(String[] singularExecutablePath) throws IOException {
        String jarFilePath = getCurrentJarPath();

        String outputJarPath = new File(new File(jarFilePath).getParent(), "SingularInterface.jar").getAbsolutePath();

        // Extract .java files from the JAR
        List<File> javaFiles = extractJavaFilesFromJar(jarFilePath);

        // Compile the .java files
        compileJavaFiles(javaFiles);

        // Create a new file using createSingularPathFile
        File singularPathFile = createSingularPathFile(singularExecutablePath);

        // Create a new JAR with compiled classes and the new file
        createNewJar(outputJarPath, javaFiles, singularPathFile);

        // Clean up copied java files
        cleanUpJavaFiles(javaFiles);

        if (new File(outputJarPath).exists()) {
            System.out.println("Created Jar " + new File(outputJarPath).getPath());
        } else {
            System.out.println("Failed to create Jar.");
            return false;
        }

        return true;
    }

    private static String getCurrentJarPath() {
        String path = Installer.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        return new File(path).getAbsolutePath();
    }

    private static List<File> extractJavaFilesFromJar(String jarFilePath) throws IOException {
        List<File> javaFiles = new ArrayList<>();

        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.getName().endsWith(".java")) {
                    // Create a temporary file with the same name as the original
                    File tempFile = new File(System.getProperty("java.io.tmpdir"), entry.getName());
                    tempFile.deleteOnExit(); // Ensure it gets deleted on exit

                    try (InputStream is = jarFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }

                    javaFiles.add(tempFile);

                    System.out.println("Created " + tempFile.getPath());
                }
            }
        }
        return javaFiles;
    }

    private static void compileJavaFiles(List<File> javaFiles) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        for (File javaFile : javaFiles) {
            compiler.run(null, null, null, javaFile.getPath());
        }
    }

    private static void createNewJar(String outputJarPath, List<File> javaFiles, File singularPathFile) throws IOException {
        // Create a manifest file
        Manifest manifest = createManifest();

        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputJarPath), manifest)) {
            // Add compiled classes to the JAR
            for (File javaFile : javaFiles) {
                String className = javaFile.getPath().replace(".java", ".class");
                File classFile = new File(className);

                if (classFile.exists()) {
                    JarEntry entry = new JarEntry(classFile.getName());
                    jos.putNextEntry(entry);

                    try (FileInputStream fis = new FileInputStream(classFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            jos.write(buffer, 0, bytesRead);
                        }
                    }
                    jos.closeEntry();
                }
            }

            // Add the new file to the JAR
            JarEntry newFileEntry = new JarEntry("singularPath.txt");
            jos.putNextEntry(newFileEntry);
            try (FileInputStream fis = new FileInputStream(singularPathFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    jos.write(buffer, 0, bytesRead);
                }
            }
            jos.closeEntry();
        }
    }

    private static Manifest createManifest() throws IOException {
        // Create a temporary file for the manifest
        File manifestFile = File.createTempFile("MANIFEST", ".MF");
        manifestFile.deleteOnExit(); // Ensure it gets deleted on exit

        // Write the correct contents to the manifest file
        try (FileWriter writer = new FileWriter(manifestFile)) {
            writer.write("Manifest-Version: 1.0\n");
            writer.write("Main-Class: Launcher\n");
            writer.write("\n"); // Necessary for the manifest format
        }

        // Load the manifest from the file
        try (FileInputStream fis = new FileInputStream(manifestFile)) {
            return new Manifest(fis);
        }
    }

    private static void cleanUpJavaFiles(List<File> javaFiles) {
        //Remove created .java files

        for (File javaFile : javaFiles) {
            if (javaFile.exists() && !javaFile.delete()) {
                System.err.println("Failed to delete file: " + javaFile.getAbsolutePath());
            } else {
                System.out.println("Cleaned up " + javaFile.getPath());
            }
        }
    }

    private static File createSingularPathFile(String[] contents) {
        File tempFile = null;

        try {
            tempFile = File.createTempFile("singularPath", ".txt");

            // Delete on exit
            tempFile.deleteOnExit();
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                for (int i = 0; i < contents.length; i++) {
                    fos.write((contents[i] + (i != contents.length - 1 ? "|" : "")).getBytes());
                }
            }

            System.out.println("Created " + tempFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile;
    }
}