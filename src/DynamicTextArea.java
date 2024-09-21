import javafx.scene.control.TextArea;

import java.util.Arrays;

public class DynamicTextArea extends TextArea {

    public DynamicTextArea() {
        super();
        setWrapText(false);
        setPrefRowCount(1);
        setScrollTop(0);

        // Adjust the height whenever text changes
        textProperty().addListener((observable, oldValue, newValue) -> {
            adjustHeight();
        });
    }

    private void adjustHeight() {
        String text = getText();
        // Count the number of newline characters
        int lineCount = text.length() - text.replace("\n", "").length();
        // Set the height based on the row count (you can adjust the row height as needed)
        setPrefRowCount(lineCount);
    }
}