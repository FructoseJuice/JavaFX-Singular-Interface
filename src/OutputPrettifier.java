import java.util.regex.Pattern;

public class OutputPrettifier {
    private final Pattern operatorPattern = Pattern.compile("(?<!\\s)([+=])(?!\\s)");
    private final Pattern subtractionPattern = Pattern.compile("([a-zA-Z0-9]+)-([a-zA-Z0-9]+)");
    private final Pattern commaPattern = Pattern.compile(",\\s*([a-zA-Z0-9(]+)");
    private final Pattern exponentPattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
    private final Pattern caratPattern = Pattern.compile("\\^(\\d+)");

    public String formatMathString(String output) {
        // Use StringBuilder for efficient string manipulation
        StringBuilder result = new StringBuilder(output);

        // Replace subtraction operator with spaces
        result = new StringBuilder(subtractionPattern.matcher(result).replaceAll("$1 - $2"));

        // Replace operators with spaces around them
        result = new StringBuilder(operatorPattern.matcher(result).replaceAll(" $1 "));

        // Add spaces after commas
        result = new StringBuilder(commaPattern.matcher(result).replaceAll(", $1"));

        // Replace caret notation
        //result = new StringBuilder(caratPattern.matcher(result).replaceAll("^($1)"));

        // Replace exponent notation
        result = new StringBuilder(exponentPattern.matcher(result).replaceAll("$1^$2"));

        return result.toString().trim(); // Trim to clean up leading/trailing spaces
    }
}
