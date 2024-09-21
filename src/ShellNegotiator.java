import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShellNegotiator {
    public ShellNegotiator() {}

    public String executeCommand(String command) throws IOException {
        // Construct the command pipeline
        List<String> commands = new ArrayList<>();
        commands.add("/bin/sh"); // invoke a shell
        commands.add("-c"); // command separator

        commands.add(command); // command pipeline

        // Execute the command pipeline
        ProcessBuilder pb = new ProcessBuilder(commands);
        Process process = pb.start();

        // Capture the output
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        // Wait for the process to finish and check for errors
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Command exited with code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            throw new IOException("Command execution was interrupted", e);
        }

        //Return output
        return output.toString().trim();
    }
}

