package Controllers;

import Models.Event;
import Models.Timeline;
import Models.User;

import Utils.FileController;

import Utils.KeyPress;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.converter.LocalTimeStringConverter;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EventCreationPopupController implements Initializable {

    Timeline timeline;
    Event event;

    private File imageFile;
    private SpinnerValueFactory<LocalTime> startValue;
    private SpinnerValueFactory<LocalTime> endValue;

    @FXML
    private ImageView eventImage;

    @FXML
    public Button selectImageButton;

    @FXML
    private TextField eventName;

    @FXML
    private DatePicker startingDate;

    @FXML
    private DatePicker endingDate;

    @FXML
    private Spinner startTimeSpinner;

    @FXML
    private Spinner endTimeSpinner;

    @FXML
    private TextArea eventDesc;

    @FXML
    private Button deleteButton;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void saveEvent() {
//        event = new Event();
//        try {
//            event.setName(eventName.getText());
//            event.setDescription(eventDesc.getText());
//            event.setTimeline(timeline);
//            event.setStartDate(startingDate.getValue().atStartOfDay());
//            event.setEndDate(endingDate.getValue().atStartOfDay());
//            event.setImage(imageFile.getName());
//            event.setCreatedBy(this.timeline.getCreatedBy());
//            SpinnerValueFactory start = (SpinnerValueFactory)startTimeSpinner.getValue();
////            event.setStart(start.toString());
//            SpinnerValueFactory end = (SpinnerValueFactory)endTimeSpinner.getValue();
////            event.setEnd(end.toString());
//            //what if the user create an event doesnt happen at current time???
//
//            event.save();
//            closePopup();
//        } catch (Exception e) {
//            //e.printStackTrace();
//            errMsg();
//        }
        String name = eventName.getText();
        String description = eventDesc.getText();
        String image = imageFile.getName();
        LocalDateTime startDate = startingDate.getValue().atStartOfDay();
        LocalDateTime endDate = endingDate.getValue().atStartOfDay();
        LocalTime start = startValue.getValue();
        LocalTime end = endValue.getValue();

        Timeline timeline = getTimeline();
        User createdBy = timeline.getCreatedBy();

        if (this.event == null) {
            Event newEvent = new Event(name, description, image, startDate, endDate, start, end, timeline, createdBy);
            newEvent.save();
        }
        else {
            this.event.editEvent(name, description, image, startDate, endDate, start, end, timeline, createdBy);
            event.update();
        }

        event = null;
        closePopup();
    }

    @FXML
    public void editEvent(Event event) {//a button should be made in  Event.fxml (edit button) and set action on (e-> this.eventController.editEvent);
        createButton.setText("Save");
        deleteButton.setVisible(true);
        this.event = event;

        eventName.setText(event.getName());
        eventDesc.setText(event.getDescription());
        startingDate.setValue(event.getStartDate().toLocalDate());
        endingDate.setValue(event.getEndDate().toLocalDate());
//        startTimeSpinner.setPromptText(event.getStart().get());
//        endTimeSpinner.setValueFactory(event.getEnd().get());

//       createButton.setOnAction(e -> {
//            event.setName(eventName.getText());
//            event.setDescription(eventDesc.getText());
//            event.setStartDate(startingDate.getValue().atStartOfDay());
//            event.setEndDate(endingDate.getValue().atStartOfDay());
//            event.setImage(imageFile.getName());
//
//            event.update();
//            closePopup();
//        });
    }

    @FXML
    public void deleteEvent() {
        event.delete();
        closePopup();
    }

    @FXML
    public void fileChooser() throws IOException {
//        FileChooser filePicChooser = new FileChooser();
//        filePicChooser.setTitle ("Select  Image");
//
//        filePicChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("PNG files", "*.png")
//                ,new FileChooser.ExtensionFilter("JPG files", "*.jpg")
//        );
//        imageFile = filePicChooser.showOpenDialog(new Stage());
//        //this.imageFile = selectedFile.getName();
//        eventImage.setImage(new Image(imageFile.toURI().toString()));

        imageFile = FileController.fileChooser();

        eventImage.setImage(new Image(imageFile.toURI().toURL().toExternalForm()));

    }



    ///////////////////////////////// GETTERS SETTERS

    public Timeline getTimeline() {
        return this.timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /////////////////////////////////

    ///////////////////////////////// UTILS

    @FXML
    void closePopup() {
        createButton.getScene().getWindow().hide();
        cancelButton.getScene().getWindow().hide();
    }

    private void errMsg() {//it should be like this???
        if (eventName.getText().isEmpty())
            eventName.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        else if(startingDate.getValue()==null)
            startingDate.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        else if(endingDate.getValue()==null)
            endingDate.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");


    }



    private SpinnerValueFactory spinnerValue() {
        SpinnerValueFactory value = new SpinnerValueFactory<LocalTime>() {
            {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                setConverter(new LocalTimeStringConverter(formatter, null));
            }

            @Override
            public void increment(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(steps));
                }
            }

            @Override
            public void decrement(int steps) {
                if (getValue() == null) {
                    setValue(LocalTime.now());
                }
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(steps));
                }
            }

        };
        LocalTime time = LocalTime.now();
        value.setValue(time);
        return value;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        SpinnerValueFactory<LocalTime> startValue = spinnerValue();
        LocalTime time = LocalTime.now();

        startValue = spinnerValue();
        startValue.setValue(time);
//        startTimeSpinner.setValueFactory(startValue);
        startTimeSpinner.setEditable(true);

        endValue = spinnerValue();
        endValue.setValue(time);
//        endTimeSpinner.setValueFactory(endValue);
        endTimeSpinner.setEditable(true);

        //endDate can not be before start date now
        Callback<DatePicker, DateCell> callB = new Callback<>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        setDisable(empty || item.compareTo(startingDate.getValue()) < 0);
                    }
                };
            }
        };
        endingDate.setDayCellFactory(callB);
    }

}
