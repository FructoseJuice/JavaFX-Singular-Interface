import java.io.*;

public class Launcher {
    public static void main(String[] args) {
        String commandArgument = "";

        String singularPathFile = "singularPath.txt";

        try (InputStream inputStream = Launcher.class.getResourceAsStream(singularPathFile)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + singularPathFile);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    commandArgument = line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (commandArgument.isEmpty()) {
            System.out.println("Failed to read singular path.");
            System.exit(2);
        }

        Interface.main(new String[] {commandArgument});
    }
}