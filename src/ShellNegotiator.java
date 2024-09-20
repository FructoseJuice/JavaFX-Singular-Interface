import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShellNegotiator {
    public static String executeCommand(String command) throws IOException {
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

        while (line == null) {
            line = reader.readLine();
        }

        reader.close();

        //Return output
        return line;
    }
}

