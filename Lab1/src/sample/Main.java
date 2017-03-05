package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;

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


        Polyline polyline1 = new Polyline();
        Polyline polyline2 = new Polyline();


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

        circle.setCenterX(150.0f);
        circle.setCenterY(148.0f);
        circle.setRadius(38.0f);

        root.getChildren().add(polyline1);
        root.getChildren().add(polyline2);

        root.getChildren().add(circle);
        root.getChildren().add(l1);
        root.getChildren().add(l2);
        root.getChildren().add(l3);
        root.getChildren().add(l4);
        root.getChildren().add(l5);

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
