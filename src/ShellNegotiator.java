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
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();

        int tries = 0;
        while (tries < 500 && line == null) {
            line = reader.readLine();
            tries++;
        }

        reader.close();

        //Return output
        return line;
    }
}

