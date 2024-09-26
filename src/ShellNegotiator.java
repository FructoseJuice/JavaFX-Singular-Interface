import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ShellNegotiator {
    private Process process;
    private PrintWriter processWriter;
    private static final String SINGULAR_PATH = "/home/fructose/Desktop/singular/bin/Singular";

    private final TextArea OUT_NODE;

    private int commandID = 0;

    public ShellNegotiator(TextArea outNode) {
        OUT_NODE = outNode;
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
                        OUT_NODE.appendText("\n" + line.trim());
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


    public void sendInputToProcess(String input) {
        if (processWriter != null) {
            processWriter.println(input); // Send the input to the process
        }
    }
}

