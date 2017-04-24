package sample;

import javafx.animation.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main extends Application {
        private final static String TRAJECTORY_FILE = "src/sample/trajectory.bmp";

        private Path fetchPath(Image image) {
            Path path = new Path();

            int maxHeightIdx = (int)image.getHeight() - 1;
            int maxWidthIdx = (int)image.getWidth() - 1;


            PixelReader pixelReader = image.getPixelReader();

            boolean isFirstPoint = true;

            for(int currY = maxHeightIdx; currY >= 0; currY--) {
                for(int currX = 0; currX <= maxWidthIdx; currX++) {
                    if(isBlack(pixelReader.getColor(currX, currY))) {
                        if(isFirstPoint) {
                            path.getElements().add(new MoveTo(currX, currY));
                            isFirstPoint = false;
                        } else {
                            path.getElements().add(new LineTo(currX, currY));
                        }
                        break;
                    }
                }
            }

            return path;
        }
        private boolean isBlack(Color color) {
            return color.equals(Color.BLACK);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            InputStream imageS = Files.newInputStream(Paths.get(TRAJECTORY_FILE));
            Image image = new Image(imageS);
            ImageView imageView = new ImageView(image);

            Group root = new Group();
            Group root2 = new Group();

            Scene scene = new Scene(root, image.getWidth(), image.getHeight());
            scene.setFill(Color.WHITE);

            root.getChildren().add(imageView);

            //Ремешок
            Polyline pol = new Polyline(
                    100, 245,
                    50,300,
                    60,310,
                    70,330,
                    70,450,
                    80,465,
                    100,470,
                    350,450,
                    380,440,
                    400,430,
                    450,300,
                    400,245);
            root.getChildren().add(pol);
            root2.getChildren().add(pol);
            pol.setStroke(Color.rgb(63,68,131));
            pol.setStrokeWidth(15.0);
            //Барабан
            Ellipse elips = new Ellipse(250,350,150,20);
            root2.getChildren().add(elips);
            elips.setFill(Color.rgb(200,52,57));

            Rectangle rectangle = new Rectangle(100,250, 300,100);
            root2.getChildren().add(rectangle);

            rectangle.setFill(Color.rgb(200,52,57));

            Circle c5 = new Circle(200,205,10);
            root2.getChildren().add(c5);

            c5.setFill(Color.GREY);

            Circle c6 = new Circle(300,205,10);
            root2.getChildren().add(c6);

            c6.setFill(Color.rgb(177,177,177));

            Ellipse elips2 = new Ellipse(250,240,150,40);
            root2.getChildren().add(elips2);

            elips2.setFill(Color.rgb(212,212,96));

            Circle c1 = new Circle(100,245,10);
            root2.getChildren().add(c1);

            c1.setFill(Color.rgb(177,177,177));
            Rectangle r1 = new Rectangle(95,245, 10,110);
            root2.getChildren().add(r1);

            r1.setFill(Color.rgb(177,177,177));

            Circle c2 = new Circle(400,245,10);
            root2.getChildren().add(c2);

            c2.setFill(Color.rgb(177,177,177));
            Rectangle r2 = new Rectangle(395,245, 10,110);
            root2.getChildren().add(r2);

            r2.setFill(Color.rgb(177,177,177));

            Circle c3 = new Circle(200,275,10);
            root2.getChildren().add(c3);

            c3.setFill(Color.rgb(177,177,177));
            Rectangle r3 = new Rectangle(195,270, 10,100);
            root2.getChildren().add(r3);

            r3.setFill(Color.rgb(177,177,177));

            Circle c4 = new Circle(300,275,10);
            root2.getChildren().add(c4);

            c4.setFill(Color.rgb(177,177,177));
            Rectangle r4 = new Rectangle(295,270, 10,100);
            root2.getChildren().add(r4);

            r4.setFill(Color.rgb(177,177,177));

            //Палочка #1
            Circle circle1 = new Circle(250,230,25);
            root2.getChildren().add(circle1);
            circle1.setFill(Color.rgb(206,155,110));
            Polygon polygon1 = new Polygon(250,220,420,50,440,70,240,250);
            root2.getChildren().add(polygon1);
            polygon1.setFill(Color.rgb(206,155,110));
            Circle cr1 = new Circle(430,60,14);
            root2.getChildren().add(cr1);
            cr1.setFill(Color.rgb(206,155,110));

            //Палочка #2
            Circle circle2 = new Circle(300,130,25);
            root2.getChildren().add(circle2);
            circle2.setFill(Color.rgb(206,155,110));
            Polygon polygon2 = new Polygon(300,120,300,140,40,69,40,40);
            root2.getChildren().add(polygon2);
            polygon2.setFill(Color.rgb(206,155,110));
            Circle cr2 = new Circle(40,55,14);
            root2.getChildren().add(cr2);
            cr2.setFill(Color.rgb(206,155,110));
            root.getChildren().add(root2);

            Path path = fetchPath(image);
            PathTransition pathTransition = new PathTransition();

            pathTransition.setDuration(Duration.millis(3000));
            pathTransition.setPath(path);
            pathTransition.setNode(root2);
            pathTransition.setCycleCount(Timeline.INDEFINITE);
            pathTransition.setAutoReverse(true);


            // Анимация
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), root2);
            fadeTransition.setFromValue(1.0f);
            fadeTransition.setToValue(0.0f);
            fadeTransition.setCycleCount(Timeline.INDEFINITE);
            fadeTransition.setAutoReverse(true);

            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), root2);
            translateTransition.setFromX(30);
            translateTransition.setToX(200);
            translateTransition.setCycleCount(Timeline.INDEFINITE);
            translateTransition.setAutoReverse(true);

            RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000),root2);
            rotateTransition.setByAngle(360f);
            rotateTransition.setCycleCount(Timeline.INDEFINITE);
            rotateTransition.setAutoReverse(true);

            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(2000), root2);
            scaleTransition.setToX(2f);
            scaleTransition.setToY(2f);
            scaleTransition.setCycleCount(Timeline.INDEFINITE);
            scaleTransition.setAutoReverse(true);
            ParallelTransition parallelTransition =new ParallelTransition();
            parallelTransition.getChildren().addAll(
                    pathTransition,
                    fadeTransition,
                    translateTransition,
                    rotateTransition,
                    scaleTransition);
            parallelTransition.play();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lysogor, KP-42");
            primaryStage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }
    }
