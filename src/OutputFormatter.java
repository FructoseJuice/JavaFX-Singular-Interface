import java.util.regex.Pattern;

public class OutputFormatter {
    private static final Pattern operatorPattern = Pattern.compile("(?<!\\s)([+=])(?!\\s)");
    private static final Pattern subtractionPattern = Pattern.compile("([a-zA-Z0-9]+)-([a-zA-Z0-9]+)");
    private static final Pattern commaPattern = Pattern.compile(",\\s*([a-zA-Z0-9(]+)");
    private static final Pattern exponentPattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
    private static final Pattern bracePattern = Pattern.compile("[{}]");
    private static final Pattern sentencePattern = Pattern.compile(
            "\\b([A-Za-z\\s0-9]+:|[A-Z][^.!?]*[.!?])"
    );
    private static final Pattern lineHeaderPattern = Pattern.compile( "[+-]{3,}");

    public static String formatMathString(String output) {
        // Use StringBuilder for efficient string manipulation
        StringBuilder result = new StringBuilder(output.trim());

        // Check if this line is blank
        if (output.isEmpty() || output.isBlank()) {
            return "\\begin{array}{c} \\\\ \\end{array}\\\\";
        }

        // Check if this line has the patterns:
        // ------------
        // ++++++++++++
        if (lineHeaderPattern.matcher(result).find()) {
            //result = new StringBuilder("\\\\text{" + result.toString() + "}");
            return "\\text{" + result.toString() + "}\\\\";
        }

        // Replace subtraction operator with spaces
        result = new StringBuilder(subtractionPattern.matcher(result).replaceAll("$1 - $2"));

        // Replace operators with spaces around them
        result = new StringBuilder(operatorPattern.matcher(result).replaceAll(" $1 "));

        // Add spaces after commas
        result = new StringBuilder(commaPattern.matcher(result).replaceAll(", $1"));

        // Replace caret notation
        //result = new StringBuilder(caratPattern.matcher(result).replaceAll("^($1)"));

        // Correctly format curly braces
        result = new StringBuilder(bracePattern.matcher(result).replaceAll("\\\\$0"));

        // Detect sentences and react accordingly
        result = new StringBuilder(sentencePattern.matcher(result).replaceAll("\\\\text{$0}"));

        // Replace exponent notation
        result = new StringBuilder(exponentPattern.matcher(result).replaceAll("$1^$2"));

        result.append("\\\\");
        return result.toString().trim(); // Trim to clean up leading/trailing spaces
    }
}
