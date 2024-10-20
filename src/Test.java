import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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
    private static String testString = """
            
                              +++++++++++++++++++++
                              The given ring is:
                              (0,a,b,c),(x,y,z),(Dp(3),C)
                              
                              F = {
                              (a)*x2+(-b2)*y,\s
                              y3+(-c)*xy,\s
                              z3+(-c)*x2
                              }.
                              
                              E = {0}.
                              
                              N = {}.
                              
                              RGB = {
                              (a)*x2+(-b2)*y,\s
                              z3+(-c)*x2,\s
                              y3+(-c)*xy
                              }.
                              
                              --------- mcgbMain -------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- M_simpl ---------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------- M_onestep ------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------------ M_completion ----------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- KSW --------
                              
                              
                              Module Comprehensive Groebner Systems:\s
                              
                              Branch 1:
                              constraints: a!=0,
                              Module Groebner basis:\s
                              [(a)*x^2+(-b^2)*y, 0],\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ x^2 ]
                              [ z^3 ]
                              [ y^3 ]
                              
                              Branch 2:
                              constraints: a=0,b!=0,
                              Module Groebner basis:\s
                              [(b^2)*y, (-a)*x^2],\s
                              [z^3+(-c)*x^2 , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ y ]
                              [ z^3 ]
                              
                              Branch 3:
                              constraints: b=0,a=0,
                              Module Groebner basis:\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ z^3 ]
                              [ y^3 ]
                              
                              
                              
                              
                              Comprehensive Groebner Basis:\s
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              
                              
                              Time of Groebner Cover: 70.
                              ---------------- Groebner Cover ---------------
                              
                              x2,z3,y3,(a)*x2+(-b2)*y,(a)*z3+(-b2c)*y,y3+(-c)*xy,0,(a),x^2,z^3,y^3.
                              
                              +++++++++++++++++++++
                              The given ring is:
                              (0,a,b,c),(x,y,z),(Dp(3),C)
                              
                              F = {
                              (a)*x2+(-b2)*y,\s
                              y3+(-c)*xy,\s
                              z3+(-c)*x2
                              }.
                              
                              E = {0}.
                              
                              N = {}.
                              
                              RGB = {
                              (a)*x2+(-b2)*y,\s
                              z3+(-c)*x2,\s
                              y3+(-c)*xy
                              }.
                              
                              --------- mcgbMain -------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- M_simpl ---------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------- M_onestep ------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------------ M_completion ----------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- KSW --------
                              
                              
                              Module Comprehensive Groebner Systems:\s
                              
                              Branch 1:
                              constraints: a!=0,
                              Module Groebner basis:\s
                              [(a)*x^2+(-b^2)*y, 0],\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ x^2 ]
                              [ z^3 ]
                              [ y^3 ]
                              
                              Branch 2:
                              constraints: a=0,b!=0,
                              Module Groebner basis:\s
                              [(b^2)*y, (-a)*x^2],\s
                              [z^3+(-c)*x^2 , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ y ]
                              [ z^3 ]
                              
                              Branch 3:
                              constraints: b=0,a=0,
                              Module Groebner basis:\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ z^3 ]
                              [ y^3 ]
                              
                              
                              
                              
                              Comprehensive Groebner Basis:\s
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              
                              
                              Time of Groebner Cover: 60.
                              ---------------- Groebner Cover ---------------
                              
                              x2,z3,y3,(a)*x2+(-b2)*y,(a)*z3+(-b2c)*y,y3+(-c)*xy,0,(a),x^2,z^3,y^3.
                              
                              +++++++++++++++++++++
                              The given ring is:
                              (0,a,b,c),(x,y,z),(Dp(3),C)
                              
                              F = {
                              (a)*x2+(-b2)*y,\s
                              y3+(-c)*xy,\s
                              z3+(-c)*x2
                              }.
                              
                              E = {0}.
                              
                              N = {}.
                              
                              RGB = {
                              (a)*x2+(-b2)*y,\s
                              z3+(-c)*x2,\s
                              y3+(-c)*xy
                              }.
                              
                              --------- mcgbMain -------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- M_simpl ---------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------- M_onestep ------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------------ M_completion ----------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- KSW --------
                              
                              
                              Module Comprehensive Groebner Systems:\s
                              
                              Branch 1:
                              constraints: a!=0,
                              Module Groebner basis:\s
                              [(a)*x^2+(-b^2)*y, 0],\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ x^2 ]
                              [ z^3 ]
                              [ y^3 ]
                              
                              Branch 2:
                              constraints: a=0,b!=0,
                              Module Groebner basis:\s
                              [(b^2)*y, (-a)*x^2],\s
                              [z^3+(-c)*x^2 , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ y ]
                              [ z^3 ]
                              
                              Branch 3:
                              constraints: b=0,a=0,
                              Module Groebner basis:\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ z^3 ]
                              [ y^3 ]
                              
                              
                              
                              
                              Comprehensive Groebner Basis:\s
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              
                              
                              Time of Groebner Cover: 70.
                              ---------------- Groebner Cover ---------------
                              
                              x2,z3,y3,(a)*x2+(-b2)*y,(a)*z3+(-b2c)*y,y3+(-c)*xy,0,(a),x^2,z^3,y^3.
                              
                              +++++++++++++++++++++
                              The given ring is:
                              (0,a,b,c),(x,y,z),(Dp(3),C)
                              
                              F = {
                              (a)*x2+(-b2)*y,\s
                              y3+(-c)*xy,\s
                              z3+(-c)*x2
                              }.
                              
                              E = {0}.
                              
                              N = {}.
                              
                              RGB = {
                              (a)*x2+(-b2)*y,\s
                              z3+(-c)*x2,\s
                              y3+(-c)*xy
                              }.
                              
                              --------- mcgbMain -------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- M_simpl ---------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------- M_onestep ------------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ------------------ M_completion ----------
                              
                              C = [
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              ].
                              
                              It is comprehensive and minimal.
                              ---------- KSW --------
                              
                              
                              Module Comprehensive Groebner Systems:\s
                              
                              Branch 1:
                              constraints: a!=0,
                              Module Groebner basis:\s
                              [(a)*x^2+(-b^2)*y, 0],\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ x^2 ]
                              [ z^3 ]
                              [ y^3 ]
                              
                              Branch 2:
                              constraints: a=0,b!=0,
                              Module Groebner basis:\s
                              [(b^2)*y, (-a)*x^2],\s
                              [z^3+(-c)*x^2 , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ y ]
                              [ z^3 ]
                              
                              Branch 3:
                              constraints: b=0,a=0,
                              Module Groebner basis:\s
                              [z^3+(-c)*x^2, 0],\s
                              [y^3+(-c)*x*y , 0].
                              
                              LPP Set of Module Groebner Basis under specialization:
                              [ z^3 ]
                              [ y^3 ]
                              
                              
                              
                              
                              Comprehensive Groebner Basis:\s
                              
                              /* f_1 = */ (a)*x2+(-b2)*y,
                              /* f_2 = */ z3+(-c)*x2,
                              /* f_3 = */ y3+(-c)*xy;
                              
                              
                              
                              Time of Groebner Cover: 70.
                              ---------------- Groebner Cover ---------------
                              
                              x2,z3,y3,(a)*x2+(-b2)*y,(a)*z3+(-b2c)*y,y3+(-c)*xy,0,(a),x^2,z^3,y^3.
                              
            """;

    @Override
    public void start(Stage primaryStage) {
        StringBuilder formatted = new StringBuilder();

        for (String string : testString.split("\n")) {
            formatted.append(OutputFormatter.formatMathString(string));
        }

        // Create a TeXFormula
        TeXFormula formula = new TeXFormula(formatted.toString());
        TeXIcon icon = formula.createTeXIcon(0, 25); // Use default style with size 40

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

        // Optionally set the fit width and height
        imageView.setPreserveRatio(true); // Preserve aspect ratio
        //imageView.setFitWidth(1000); // Set preferred width (adjust as needed)
        //imageView.setFitHeight(1000); // Set preferred height (adjust as needed)

        // Create a ScrollPane and add the ImageView to it
        ScrollPane scrollPane = new ScrollPane(imageView);
        scrollPane.setPrefSize(400, 300); // Set preferred size for the scroll pane

        // Create a layout and add the scroll pane
        StackPane root = new StackPane(scrollPane);
        Scene scene = new Scene(root, 400, 300);

        // Set up the stage
        primaryStage.setTitle("Image Scroll Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
