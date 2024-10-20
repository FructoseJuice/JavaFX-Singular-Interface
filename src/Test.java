import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a TeXFormula
        TeXFormula formula = new TeXFormula("x3-y=0");
        TeXIcon icon = formula.createTeXIcon(0, 20); // Use default style with size 40

        // Create a BufferedImage to draw the icon
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Paint the icon onto the BufferedImage
        icon.paintIcon(new JLabel(), g2d, 0, 0);
        g2d.dispose(); // Dispose of graphics context

        // Convert BufferedImage to Image
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

        // Create an ImageView for the rendered formula
        ImageView imageView = new ImageView(fxImage);

        // Create a layout and add the ImageView
        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        // Set up the Scene and Stage
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("JLaTeXMath with JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
