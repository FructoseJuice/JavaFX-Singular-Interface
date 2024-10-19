import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class StringReplacer {

    private static final Charset CHARSET = StandardCharsets.ISO_8859_1;
    private static final String OLD_DIRECTORY_PATH_STRING = "/Users/kapur/CGBSoftware/MCGB/";

    public static void main(String[] args) {
        String newDirectoryPathString = "C:/Users/Brandon/Desktop/CGBSoftware/MCGB/";

        try {
            walkDirAndReplaceString(Paths.get(newDirectoryPathString), OLD_DIRECTORY_PATH_STRING, newDirectoryPathString);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public static void startProcess(String newDirPathString) {
        try {
            walkDirAndReplaceString(Paths.get(newDirPathString), OLD_DIRECTORY_PATH_STRING, newDirPathString);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    public static void walkDirAndReplaceString(Path path, String oldString, String newString) throws IOException {
        //Short circuit if not targeting a directory
        if (!Files.isDirectory(path)) return;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                //Check if this entry is a directory
                if (Files.isDirectory(entry)) {
                    //Recursively call to search subdirectories
                    walkDirAndReplaceString(entry, oldString, newString);
                } else if (Files.isRegularFile(entry)) {
                    //Search and replace old string with new string
                    replaceStringInFile(entry, oldString, newString);
                }
            }
        }
    }

    private static void replaceStringInFile(Path filePath, String oldString, String newString) throws IOException {
        List<String> lines;

        // Try to read all lines, if not a text file we should exit
        try {
            lines = Files.readAllLines(filePath, CHARSET);
        } catch (MalformedInputException e) {
            System.err.println("Skipping non-text file: " + filePath);
            return;
        }

        boolean modified = false;

        //Do in-place substring replacement for each line containing the old string
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(oldString)) {
                System.out.println("Found line: " + lines.get(i));
                lines.set(i, lines.get(i).replace(oldString, newString));
                System.out.println("Proposed Replacement: " + lines.get(i) + "\n");

                modified = true;
            }
        }

        if (modified) {
            //Files.write(filePath, lines, CHARSET);
            //System.out.println("Updated file: " + filePath);
        }
    }
}