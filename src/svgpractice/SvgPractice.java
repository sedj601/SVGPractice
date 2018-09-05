/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svgpractice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author blj0011
 */
public class SvgPractice extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        StackPane root = new StackPane();

        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
//                SVGPath sVGPath = new SVGPath();
//                sVGPath.setContent("M 33.263,291.048 C 33.263,425.148 121.894,533.903 231.226,533.903 C 340.523,533.903 429.155,425.147 429.155,291.048 C 429.155,156.947 340.523,48.192 231.226,48.192 C 121.895,48.191 33.263,156.947 33.263,291.048 L 33.263,291.048 z");
//                //sVGPath.setContent("M 33.263,291.048 C 33.263,425.148 121.894,533.903 231.226,533.903 C 340.523,533.903 429.155,425.147 429.155,291.048 C 429.155,156.947 340.523,48.192 231.226,48.192 C 121.895,48.191 33.263,156.947 33.263,291.048 L 33.263,291.048 z M 431.999,260.123 C 434.087,261.959 438.695,269.664 440.279,274.775 C 441.863,279.851 442.835,286.296 441.251,290.363 C 439.343,294.288 434.123,298.139 429.767,298.319 C 425.339,298.283 419.543,290.939 415.44,291.011 C 411.263,291.155 406.944,299.219 405.252,298.967 C 403.488,298.571 403.668,291.515 405.252,288.779 C 406.908,285.935 410.508,282.011 414.792,282.407 C 419.004,282.84 426.42,290.76 430.092,291.011 C 433.584,290.975 436.14,287.627 435.816,283.055 C 435.312,278.339 428.113,267.467 427.537,263.615 C 426.887,259.691 429.803,258.251 431.999,260.123 L 431.999,260.123 z ");
//                sVGPath.setStyle("fill-rule:eventodd");
//                sVGPath.setStroke(Color.BLACK);
//                sVGPath.setStrokeWidth(0.036);
//                sVGPath.setStrokeMiterLimit(10);

                root.getChildren().addAll(processSVGPath());
            }
        });

        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    public List<SVGPath> processSVGPath()
    {
        Pattern pattern = Pattern.compile("<path d=\"M(.*?)z");
        List<SVGPath> sVGPaths = new ArrayList();
        System.out.println("in");
        File file = new File("Gerald_G_Boy_Face_Cartoon_5.xml");
        if (file.exists()) {
            System.out.println("file exists");
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String currentLine;

                while ((currentLine = bufferedReader.readLine()) != null) {
                    if (currentLine.trim().startsWith("<path")) {
                        SVGPath tempSVGPath = new SVGPath();

                        tempSVGPath.setContent("M " + StringUtils.substringBetween(currentLine, "M", "z") + " z");

                        if (currentLine.trim().contains("style=\"")) {
                            String style = StringUtils.substringBetween(currentLine, "style=\"", "\" ");
                            String[] splitOnSemicolon = style.split(";");

                            for (String item : splitOnSemicolon) {
                                String[] individualStyple = item.split(":");
                                switch (individualStyple[0].trim().toUpperCase()) {

                                    case "FILL-RULE":
                                        switch (individualStyple[1].toUpperCase().trim()) {
                                            case "EVENODD":
                                                tempSVGPath.setFillRule(FillRule.EVEN_ODD);
                                                System.out.println(individualStyple[0] + " -- " + individualStyple[1]);
                                                break;
                                            case "NONZERO":
                                                tempSVGPath.setFillRule(FillRule.NON_ZERO);
                                                break;
                                        }

                                        //System.out.println(individualStyple[0] + " -- " + FillRule.valueOf(individualStyple[1]));
                                        break;
                                    case "STROKE":
                                        tempSVGPath.setStroke(Paint.valueOf(individualStyple[1]));
                                        System.out.println(individualStyple[0] + " -- " + individualStyple[1]);
                                        break;
                                    case "STROKE-WIDTH":
                                        tempSVGPath.setStrokeWidth(Double.valueOf(individualStyple[1]));
                                        System.out.println(individualStyple[0] + " -- " + Double.valueOf(individualStyple[1]));
                                        break;
                                    case "STROKE-MITERLIMIT":
                                        tempSVGPath.setStrokeMiterLimit(Double.valueOf(individualStyple[1]));
                                        System.out.println(individualStyple[0] + " -- " + Double.valueOf(individualStyple[1]));
                                        break;
                                    case "FILL":
                                        tempSVGPath.setFill(Paint.valueOf(individualStyple[1]));
                                        System.out.println(individualStyple[0] + " -- " + Paint.valueOf(individualStyple[1]));
                                        break;
                                }
                            }

                        }

                        if (currentLine.trim().contains("id=\"")) {
                            String id = StringUtils.substringBetween(currentLine, "id=\"", "\"/>");
                            tempSVGPath.setId(id);
                            System.out.println(id);
                        }
                        sVGPaths.add(tempSVGPath);
                    }
                }
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return sVGPaths;
    }
}
