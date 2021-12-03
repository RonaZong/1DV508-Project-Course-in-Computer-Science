package Controllers;

import Models.Timeline;
import Models.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class TimelinePopupController implements Initializable {

    User user = null;
    Timeline timeline = null;
    Stage stage = null;

    @FXML
    private VBox createNewTimeline;

    @FXML
    private Label WindowTitle;

    @FXML
    private TextField timelineName;

    @FXML
    private TextArea timelineDescription;

    @FXML
    private Label timeUnitLabel;

    @FXML
    private ComboBox timeUnitBox;

    @FXML
    private Label timeFrameLabel;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    @FXML
    public void saveTimeline() {
        String title = timelineName.getText();
        String description = timelineDescription.getText();
        User owner = getUser();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        String timeUnit = timeUnitBox.getSelectionModel().getSelectedItem().toString();
        Date startDate = Date.from(startDatePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
        Date endDate =  Date.from(endDatePicker.getValue().atStartOfDay(defaultZoneId).toInstant());

        if (this.timeline == null) {
            Timeline newTimeline = new Timeline(title, description, timeUnit, startDate, endDate, owner);
            newTimeline.save();
        }
        else {
            this.timeline.editTimeline(title, description, timeUnit, startDate, endDate, owner);
            timeline.update();
        }
        timeline = null;
        closePopup();
    }


    @FXML
    public void editTimeline(Timeline timeline) {
        WindowTitle.setText("Edit Timeline");
        createButton.setText("Save");
        deleteButton.setVisible(true);

        this.timeline = timeline;

        timelineName.setText(timeline.getTitle());
        timelineDescription.setText(timeline.getDescription());
        timeUnitBox.getSelectionModel().select(timeline.getTimeUnit());

        Instant instant = timeline.getStartDate().toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        startDatePicker.setValue(localDate);

        Instant instant2 = timeline.getEndDate().toInstant();
        ZoneId defaultZoneId2 = ZoneId.systemDefault();
        localDate =  instant.atZone(defaultZoneId2).toLocalDate();
        endDatePicker.setValue(localDate);
    }

    @FXML
    public void deleteTimeline() {
        timeline.delete();
        closePopup();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Callback<DatePicker, DateCell> callB = new Callback<>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                        setDisable(empty || item.compareTo(startDatePicker.getValue()) < 0);
                    }
                };
            }
        };
        endDatePicker.setDayCellFactory(callB);
    }

    ///////////////////////////////// GETTERS SETTERS
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /////////////////////////////////

    ///////////////////////////////// UTILS

    public void closePopup() {
        createButton.getScene().getWindow().hide();
        cancelButton.getScene().getWindow().hide();
    }

    private void errMsg() {
        if (timelineName.getText().isEmpty())
           timelineName.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        else if(timeUnitBox.getSelectionModel().isEmpty())
            timeUnitBox.setStyle("-fx-border-color: #ff0000 ; -fx-border-width: 1px ;");
        else if(startDatePicker.getValue()==null)
            startDatePicker.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
        else if(endDatePicker.getValue()==null)
            endDatePicker.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
    }
    /////////////////////////////////
}
