import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ShellNegotiator {
    private Process process;
    private PrintWriter processWriter;
    private static final String SINGULAR_PATH = "/home/fructose/Desktop/singular/bin/Singular";
    private Thread outputReaderThread;

    private final DynamicTextArea OUT_NODE;

    private final String defaultSingularOutput = """
             SINGULAR                                 /  Development
             A Computer Algebra System for Polynomial Computations       /   version 4.4.0
                                                                       0<
             by: W. Decker, G.-M. Greuel, G. Pfister, H. Schoenemann     \\   Apr 2024
            FB Mathematik der Universitaet, D-67653 Kaiserslautern        \\""";

    public ShellNegotiator(DynamicTextArea outNode) {
        OUT_NODE = outNode;
        startProcess();
    }

    private void startProcess() {
        try {
            // Construct the command pipeline
            List<String> commands = new ArrayList<>();
            commands.add("/home/fructose/Desktop/singular/bin/Singular"); // Path to the binary


            // Execute the command
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.redirectErrorStream(true); // Combine stdout and stderr
            process = pb.start();


            //Printwriter to send input to cli
            processWriter = new PrintWriter(new OutputStreamWriter(process.getOutputStream()), true);

            // Create a thread to read the output
            outputReaderThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    String output;

                    while ((line = reader.readLine()) != null) {
                        output = OUT_NODE.getText();
                        output += "\n" + line;
                        output = output.trim();

                        OUT_NODE.setText(output);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Start the output reader thread
            outputReaderThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendInputToProcess(String input) {
        if (processWriter != null) {
            processWriter.println(input); // Send the input to the process
        }
    }
}

