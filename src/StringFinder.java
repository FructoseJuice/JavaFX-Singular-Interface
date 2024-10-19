import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StringFinder {

    private static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    public static void main(String[] args) {
        String directoryPath = "/home/fructose/Desktop/MCGB/";
        String searchString = "@RUA";

        try {
            searchFilesContainingString(Paths.get(directoryPath), searchString);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public static void searchFilesContainingString(Path path, String searchString) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        searchFilesContainingString(entry, searchString); // Recursive call for subdirectories
                    } else if (Files.isRegularFile(entry)) {
                        if (containsString(entry, searchString)) {
                            System.out.println("Found in file: " + entry);
                        }
                    }
                }
            }
        }
    }

    private static boolean containsString(Path filePath, String searchString) {
        try {
            List<String> lines = Files.readAllLines(filePath, CHARSET);
            for (String line : lines) {
                if (line.contains(searchString)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
        return false;
    }
}