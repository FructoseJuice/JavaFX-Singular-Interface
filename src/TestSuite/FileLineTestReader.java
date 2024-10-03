package TestSuite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileLineTestReader {
    private static String filePath = "/home/fructose/Desktop/hashemi1.mp";



    // Method to read the file line by line
    public static void readLines() {

        TestOutputPrettifier prettifier = new TestOutputPrettifier();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String paddedInput = String.format("%-" + 30 + "s", line); // Left pad original input
                    String paddedArrow = String.format("%" + (30 + 5) + "s", "====>        "); // Right pad arrow
                    if (!line.equals(prettifier.prettifyOutput(line))) {
                        System.out.println(paddedInput + paddedArrow + prettifier.prettifyOutput(line)); // Print each line (or process it as needed)
                    } else {
                        System.out.println(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        readLines();
    }

    private static class TestOutputPrettifier {
        private final Pattern operatorPattern = Pattern.compile("(?<!\\s)([+=])(?!\\s)");
        private final Pattern subtractionPattern = Pattern.compile("([a-zA-Z0-9]+)-([a-zA-Z0-9]+)");
        private final Pattern commaPattern = Pattern.compile(",\\s*([a-zA-Z0-9(]+)");
        private final Pattern exponentPattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
        private final Pattern caratPattern = Pattern.compile("\\^(\\d+)");

        public String prettifyOutput(String output) {
            // Use StringBuilder for efficient string manipulation
            StringBuilder result = new StringBuilder(output);

            // Replace subtraction operator with spaces
            result = new StringBuilder(subtractionPattern.matcher(result).replaceAll("$1 - $2"));

            // Replace operators with spaces around them
            result = new StringBuilder(operatorPattern.matcher(result).replaceAll(" $1 "));

            // Add spaces after commas
            result = new StringBuilder(commaPattern.matcher(result).replaceAll(", $1"));

            // Replace caret notation
            result = new StringBuilder(caratPattern.matcher(result).replaceAll("^($1)"));

            // Replace exponent notation
            result = new StringBuilder(exponentPattern.matcher(result).replaceAll("$1^($2)"));

            return result.toString().trim(); // Trim to clean up leading/trailing spaces
        }
    }
}