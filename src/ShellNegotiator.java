import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ShellNegotiator {
    private Process process;
    private PrintWriter processWriter;

    private StringBuilder sessionOutput = new StringBuilder();
    private StringBuilder sessionInput = new StringBuilder();

    private TextArea targetOutNode;

    private OutputPrettifier prettifier = new OutputPrettifier();

    public ShellNegotiator(TextArea outNode) {
        targetOutNode = outNode;
        startProcess();
    }

    private void startProcess() {
        try {
            // Construct the command pipeline
            //List<String> commands = new ArrayList<>();
            //commands.add(SINGULAR_PATH); // Path to the binary


            // Execute the command
            ProcessBuilder pb = new ProcessBuilder(Main.SINGULAR_PATH);
            pb.redirectErrorStream(true); // Combine stdout and stderr
            process = pb.start();

            //Printwriter to send input to cli
            processWriter = new PrintWriter(new OutputStreamWriter(process.getOutputStream()), true);

            // Create a thread to read the output
            Thread outputReaderThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        //try (BufferedWriter outputWriter = new BufferedWriter())
                        line = prettifier.prettifyOutput(line);

                        targetOutNode.appendText("\n" + line.trim());
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

