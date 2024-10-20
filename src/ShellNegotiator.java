import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.*;

public class ShellNegotiator {
    private Process process;
    private PrintWriter processWriter;

    private StringBuilder sessionOutput = new StringBuilder();
    private StringBuilder sessionInput = new StringBuilder();

    private TextArea targetOutNode;

    public ShellNegotiator(String[] SINGULAR_PATH, TextArea outNode) {
        targetOutNode = outNode;
        startProcess(SINGULAR_PATH);
    }

    private void startProcess(String[] SINGULAR_PATH) {
        try {
            // Execute the command
            ProcessBuilder pb = new ProcessBuilder(SINGULAR_PATH);
            pb.redirectErrorStream(true); // Combine stdout and stderr
            process = pb.start();

            //Printwriter to send input to cli
            processWriter = new PrintWriter(new OutputStreamWriter(process.getOutputStream()), true);

            // Create a thread to read the output
            Thread outputReaderThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        // Prettify this line first
                        line = OutputFormatter.formatMathString(line);

                        // Send line to target out node
                        String finalLine = line;
                        Platform.runLater(() -> targetOutNode.appendText("\n" + finalLine.trim()));

                        // Load output buffer with this new text
                        sessionOutput.append("\n").append(line.trim()).append("\n");
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


    public void sendInputToProcess(String input, TextArea outNode, String commandId) {
        this.targetOutNode = outNode;

        if (processWriter != null) {
            sessionInput.append("\t\t").append(commandId);
            sessionInput.append(input).append("\n");
            sessionOutput.append("\t\t").append(commandId);

            processWriter.println(input); // Send the input to the process
        }
    }

    public StringBuilder getSessionInput() {
        return sessionInput;
    }

    public StringBuilder getSessionOutput() {
        return sessionOutput;
    }
}

