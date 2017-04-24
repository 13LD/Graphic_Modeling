package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Circle circle = new Circle();
        Scene scene = new Scene(root, 700, 400);
        Line l1 = new Line(130, 135, 400, 135);
        Line l2 = new Line(130, 140, 400, 140);
        Line l3 = new Line(130, 145, 400, 145);
        Line l4 = new Line(130, 150, 400, 150);
        Line l5 = new Line(130, 155, 400, 155);


        Ellipse ellipse = new Ellipse(); {
            ellipse.setCenterX(50.0f);
            ellipse.setCenterY(50.0f);
            ellipse.setRadiusX(50.0f);
            ellipse.setRadiusY(25.0f);
        }

        Polyline polyline1 = new Polyline();
        Polyline polyline2 = new Polyline();
        Rectangle p = new Rectangle(50,50,100,200);



        primaryStage.setTitle("Lab1");


        scene.setFill(Color.YELLOW);
        polyline1.setFill(Color.BLUE);
        circle.setFill(Color.RED);
        l1.setStroke(Color.BLACK);
        l2.setStroke(Color.BLACK);
        l3.setStroke(Color.BLACK);
        l4.setStroke(Color.BLACK);
        l5.setStroke(Color.BLACK);
        polyline2.setFill(Color.BLUE);
        ellipse.setFill(Color.RED);




        polyline1.getPoints().addAll(64.0, 44.0,
                224.0, 148.0,
                64.0, 251.0,
                104.0, 148.0,
                64.0, 44.0);
        polyline2.getPoints().addAll(370.0, 145.0,
                430.0, 115.0,
                404.0, 145.0,
                430.0, 178.0,
                370.0, 145.0);



        p.setFill(Color.GREEN);

        circle.setCenterX(150.0f);
        circle.setCenterY(148.0f);
        circle.setRadius(38.0f);
//        root.getChildren().add(ellipse);
        root.getChildren().add(p);
        root.getChildren().add(polyline1);
        root.getChildren().add(polyline2);
        final Label label = new Label("dasda"
        );
        label.setTranslateY(200);
        label.setTranslateX(350);
        label.setAlignment(Pos.CENTER);


        root.getChildren().add(circle);
        root.getChildren().add(l1);
        root.getChildren().add(l2);
        root.getChildren().add(l3);
        root.getChildren().add(l4);
        root.getChildren().add(l5);
        root.getChildren().add(label);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
