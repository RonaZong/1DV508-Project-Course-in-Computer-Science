package Controllers;

import Models.Event;
import Models.Timeline;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimelineController {

    Timeline timeline;

    @FXML
    private HBox TimelineContainer;

    @FXML
    private HBox BackgroundHolder;

    @FXML
    private Region leftSpacer;


    ArrayList<Rectangle> rectangleList = new ArrayList<Rectangle>();

    protected void calculateTimeUnit(Timeline timeline) throws Exception {
//        for (Event e:
//             tl.getEventsBetween(tl.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), tl.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
//            TimelineContainer.getChildren().add(makeRectangle(200, 200, 0, 0));
//
//        }
    }

    protected void generateTimeline(Timeline tl) {
        Date firstDate = tl.getStartDate();
        Date secondDate = tl.getEndDate();

        long diffInMillis = Math.abs(secondDate.getTime() - firstDate.getTime());
        long differenceDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        rectangleList.add(makeRectangle(20, 200, 0, 0));

        while (differenceDays > 0) {
            double x = rectangleList.get(rectangleList.size() - 1).getX() + rectangleList.get(rectangleList.size() - 1).getWidth() + 20;
            rectangleList.add(makeRectangle(20, 200, x, 0));
            differenceDays--;
        }

        int i = 0;
        while (i != rectangleList.size()) {
            TimelineContainer.getChildren().add(rectangleList.get(i));
            for (Event e: tl.getListOfEvents()) {
                TimeUnit.DAYS.convert(e.getCreationDate().getTime(), TimeUnit.MILLISECONDS);

            }
            TimelineContainer.getChildren().get(i).addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
            i++;
        }
    }


    protected Rectangle makeRectangle(double width, double height, double x, double y) {
        Rectangle recktangle = new Rectangle();
        recktangle.setWidth(width);
        recktangle.setHeight(height);
        //recktangle.setStyle(timeUnit.getStyle());
        recktangle.setX(x);
        recktangle.setY(y);
        recktangle.setLayoutX(x + 20);
        recktangle.setLayoutY(y);
        recktangle.setStroke(Color.BLACK);
        recktangle.setFill(Color.BLUE);
        recktangle.setId("rectangle " + x);

        return recktangle;
    }
}
