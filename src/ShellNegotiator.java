import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ShellNegotiator {
    public ShellNegotiator() {}

    private static final String SINGULAR_PATH = "/home/fructose/Desktop/singular/bin/Singular";

    public static String executeCommand(String commandInput, int UUID) throws IOException, InterruptedException {
        // Construct the command pipeline
        List<String> commands = new ArrayList<>();
        commands.add("/bin/sh"); // invoke a shell
        commands.add("-c"); // command separator

        // Create a temporary input file
        File inputFile = File.createTempFile(UUID + "singular_input_", ".txt");
        File outputFile = File.createTempFile(UUID + "singular_output", ".txt");

        try (FileWriter writer = new FileWriter(inputFile)) {
            // Write command input to the temporary file
            writer.write(commandInput);
        }


        // Define the Singular command
        String singularCommand = SINGULAR_PATH + " < " + inputFile.getAbsolutePath() + " >> " + outputFile.getAbsolutePath();
        commands.add(singularCommand);

        // Execute the command
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true); // Combine stdout and stderr
        Process process = pb.start();

        // Wait for the process to finish
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            //Clean up temp files if error
            inputFile.delete();
            outputFile.delete();
            throw new IOException("Singular exited with code: " + exitCode);
        }

        // Read the output from the output file
        String output = new String(Files.readAllBytes(Path.of(outputFile.getAbsolutePath())));

        //Strip out singular output text

        String defaultSingularOutput = """
 SINGULAR                                 /  Development
 A Computer Algebra System for Polynomial Computations       /   version 4.4.0
                                                           0<
 by: W. Decker, G.-M. Greuel, G. Pfister, H. Schoenemann     \\   Apr 2024
FB Mathematik der Universitaet, D-67653 Kaiserslautern        \\""";

        output = output.replace(defaultSingularOutput, "");

        // Optionally delete the temporary input file
        inputFile.delete();
        outputFile.delete();

        return output.trim(); // Return the output
    }
}

