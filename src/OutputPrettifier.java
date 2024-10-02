public class OutputPrettifier {
    private final String operatorRegex = "([+\\-*=])";
    private final String exponentRegex = "([a-zA-Z]+)(\\d+)";

    public String prettifyOutput(String output) {
        output = output.replaceAll(operatorRegex, " $1 ");
        output = output.replaceAll(exponentRegex, "$1^($2)");

        return output;
    }
}
