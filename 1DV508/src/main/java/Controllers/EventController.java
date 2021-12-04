package Controllers;

import Models.Event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class EventController {

    @FXML
    private ImageView eventPicture;

    @FXML
    private Label eventTitle;

    @FXML
    private Label eventDate;

    @FXML
    private TextFlow eventDescription;

    @FXML
    private ImageView userPicture;

    @FXML
    private Label userName;


    void Event(Event e) {
        eventPicture.setImage(new Image(e.getImage()));
        eventTitle.setText(e.getName());
        eventDate.setText(e.getStart().toString());
        eventDescription.getChildren().add(new Text(e.getDescription()));
        userName.setText(e.getCreatedBy().getUsername());
        userPicture.setImage(new Image(e.getCreatedBy().getImage()));
    }

}
