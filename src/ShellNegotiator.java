import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ShellNegotiator {
    private Process process;
    private PrintWriter processWriter;
    private static final String SINGULAR_PATH = "/home/fructose/Desktop/singular/bin/Singular";

    private TextArea targetOutNode;

    private final HashSet<String> knownVariables = new HashSet<>();

    public ShellNegotiator(TextArea outNode) {
        targetOutNode = outNode;
        startProcess();
    }

    private void startProcess() {
        try {
            // Construct the command pipeline
            List<String> commands = new ArrayList<>();
            commands.add(SINGULAR_PATH); // Path to the binary


            // Execute the command
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.redirectErrorStream(true); // Combine stdout and stderr
            process = pb.start();

            //Printwriter to send input to cli
            processWriter = new PrintWriter(new OutputStreamWriter(process.getOutputStream()), true);

            // Create a thread to read the output
            Thread outputReaderThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        if (targetOutNode.getText().isBlank()) {
                            targetOutNode.appendText(line.trim());
                        } else {
                            targetOutNode.appendText("\n" + line.trim());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Start the output reader thread
            outputReaderThread.start();

            //Check status
            if (process.isAlive()) {
                processWriter.println("\"=> Awaiting Input\";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendInputToProcess(String input, TextArea outNode) {
        this.targetOutNode = outNode;

        if (processWriter != null) {
            processWriter.println(input); // Send the input to the process
        }
    }
}

