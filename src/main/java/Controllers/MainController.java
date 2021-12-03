package Controllers;

import Models.Timeline;
import Models.User;

import Utils.Utils;
import Utils.Toast;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.stage.*;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    //constants
    public final double WINDOW_WIDTH = 1050.0;
    public final double WINDOW_HEIGHT = 650.0;

    private static User currentUser = null;
    private List<Timeline> timelineList;
//    ObservableList<Timeline> timelineList = FXCollections.observableArrayList();
    boolean editMode = false;
    boolean dropDownVisible = false;

    @FXML
    private StackPane main;

    @FXML
    private Button loginButton;

    @FXML
    private Button add_timeline_btn;

    @FXML
    private StackPane adminPanel_toggle;

    @FXML
    private Button admin_panel_button;

    @FXML
    private StackPane AvatarBox;

    @FXML
    private Label AvatarLetter;

    @FXML
    private Circle UserImageCircle;

    @FXML
    private ListView<Timeline> timelines_list;

    @FXML
    private VBox DropDownUserMenu;

    @FXML
    private Button EditAccountButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private StackPane timeline_page_box;

    @FXML
    private Label timeline_name;

    @FXML
    private Button edit_timeline_button;

    @FXML
    private Button add_event_button;

    @FXML
    private ScrollPane timeline_scroll_pane;

    @FXML
    private TextFlow timeline_description;

    @FXML
    private Label timeline_author;

    @FXML
    private StackPane view_edit_toggle;

    @FXML
    private Pane switch_head;

    @FXML
    private Label switch_edit_text;

    @FXML
    private Label switch_view_text;

    @FXML
    private Region opaqueRegion;

    @FXML
    private HBox timelineVisualizer;

    @FXML
    private TimelineController timelineVisualizerController;

    @FXML
    private Label timeLabelRight;

    @FXML
    private Label timeLabelLeft;


//    @FXML
//    public void showTimelistDebug() {
//        System.out.println(timelines_list.getSelectionModel().getSelectedItem());
//        TimelineVisualizerController.generateTimeline(timelines_list.getSelectionModel().getSelectedItem());
//    }

    @FXML
    protected void login() throws IOException {
        Scene login = new Scene(FXMLLoader.load(getClass().getResource("/Views/AccountView.fxml")));
        Main.getPrimaryStage().setScene(login);
    }

    @FXML
    protected void toggleViewEditMode() throws IOException {
        if (currentUser == null) {
            Toast.makeText(Main.getPrimaryStage(), "Please log in", 400, 30, 20);
            login();
        } else {
           toggleEditButton();
        }
    }

    @FXML
    protected void showDropDownMenu() {
        dropDownVisible = !dropDownVisible;
        DropDownUserMenu.setVisible(dropDownVisible);
    }

    @FXML
    protected void DropDownEditAccount() throws IOException {
        FXMLLoader f = new FXMLLoader((getClass().getResource("/Views/AccountView.fxml")));
        Parent login = f.load();
        AccountViewController acv = f.getController();
        acv.editAccount(getCurrentUser());
        Scene loginScene = new Scene(login);
        Main.getPrimaryStage().setScene(loginScene);
    }

    @FXML
    protected void DropDownLogout() {
        setCurrentUser(null);
        checkLoggedUser();
        showDropDownMenu();
    }

    /** check*/
    @FXML
    protected void showAdminPanel() throws IOException {
        FXMLLoader f = new FXMLLoader(getClass().getResource("/Views/AdminPanelPopup.fxml"));
        Stage popup = new Stage();
        makePopup(f, popup);

        AdminPanelPopupController appc = f.getController();

//        if (!getCurrentUser().isAdmin())
//            GraphicUtils.makeToast(Main.getPrimaryStage(), "You must be an admin", ToastType.ERROR);
//        else if(currentUser == null)
//            GraphicUtils.makeToast(Main.getPrimaryStage(), "Log in first", ToastType.ERROR);
//        else
//            showPopupForm(FormType.ADMINPANEL, null);

        turnOpaque();
        popup.showAndWait();
        turnNotOpaque();
    }

    @FXML
    protected void addTimeline() throws IOException {
        if (getCurrentUser() == null) {
            Toast.makeText(Main.getPrimaryStage(), "You must be logged in", 300, 100, 100);
        }
        else {
            FXMLLoader f = new FXMLLoader(getClass().getResource("/Views/TimelinePopup.fxml"));
            Stage popup = new Stage();
            makePopup(f, popup);

            TimelinePopupController tpc = f.getController();
            tpc.setStage(popup);
            tpc.setUser(getCurrentUser());

            //listener for out of focus close command
            Utils.addConfirmationPopup(popup, "Leave the timeline", "You will lose all data");

            //cool beans opaque function for added style bonus points
            turnOpaque();

            //wait for stuff to happen
            popup.showAndWait();

            populateTimelist();
            turnNotOpaque();
        }
    }

    @FXML
    protected void editTimeline() throws IOException {
        FXMLLoader f = new FXMLLoader(getClass().getResource("/Views/TimelinePopup.fxml"));

        Stage popup = new Stage();
        makePopup(f, popup);

        TimelinePopupController tpc = f.getController();
        tpc.setStage(popup);
        tpc.setUser(getCurrentUser());
        tpc.editTimeline(timelines_list.getSelectionModel().getSelectedItem());

        //listener for out of focus close command /-/- IT HAS A LISTENER FOR OUT OF FOCUS PRESS
        Utils.addConfirmationPopup(popup, "Leave the timeline", "You will lose all data");

        //cool beans opaque function for added style bonus points
        turnOpaque();

        //wait for stuff to happen
        popup.showAndWait();

        populateTimelist();
        turnNotOpaque();
//        main.Main.getPrimaryStage().setScene(addTimeline);
    }

    @FXML
    void createEvent() throws IOException {
        FXMLLoader f = new FXMLLoader(getClass().getResource("/Views/EventCreationPopup.fxml"));

        Stage popup = new Stage();
        makePopup(f, popup);

        EventCreationPopupController ecpc = f.getController();
        ecpc.setTimeline(timelines_list.getSelectionModel().getSelectedItem());

        //listener for out of focus close command /-/- IT HAS A LISTENER FOR OUT OF FOCUS PRESS
        Utils.addConfirmationPopup(popup, "Leave the event?", "You will lose all data");

        turnOpaque();
        popup.showAndWait();
        turnNotOpaque();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //logged users cant really function, we pass it right after load
        checkLoggedUser();
        populateTimelist();


        //timelines_list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Timeline se>));
    }


    ///////////////////////////////// GETTERS SETTERS
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    /////////////////////////////////


    ///////////////////////////////// UTILS

    void checkLoggedUser() {
        if (currentUser != null) {
//            timelines_list.setVisible(true);
            timeline_page_box.setVisible(true);
            add_timeline_btn.setVisible(true);
            AvatarBox.setVisible(true);
            loginButton.setVisible(false);

            if (currentUser.getImage() == null) {
                AvatarLetter.setText(String.valueOf(currentUser.getFullname().charAt(0)).toUpperCase());
                UserImageCircle.setVisible(false);
            }
            else {
                AvatarLetter.setVisible(false);
                UserImageCircle.setFill(new ImagePattern(new Image(new File(currentUser.getImage()).toURI().toString())));
            }
        }
        else {
//            timelines_list.setVisible(false);
            timeline_page_box.setVisible(false);
            add_timeline_btn.setVisible(false);
            AvatarBox.setVisible(false);
            loginButton.setVisible(true);
        }
    }

    void toggleEditButton() {
        editMode = !editMode;
        if (editMode) {
            populateTimelineScroll();
            edit_timeline_button.setVisible(true);
            edit_timeline_button.setDisable(false);
            add_event_button.setVisible(true);
            add_event_button.setDisable(false);
            view_edit_toggle.setMargin(switch_head, new Insets(0, 46, 0, 0));
            switch_edit_text.setStyle("-fx-text-fill: #ffffff");
            switch_view_text.setStyle("-fx-text-fill: #888888");
        } else {
            populateTimelineScroll();
            edit_timeline_button.setVisible(false);
            edit_timeline_button.setDisable(true);
            add_event_button.setVisible(false);
            add_event_button.setDisable(true);
            view_edit_toggle.setMargin(switch_head, new Insets(0, 2, 0, 0));
            switch_edit_text.setStyle("-fx-text-fill: #888888");
            switch_view_text.setStyle("-fx-text-fill: #ffffff");
        }
    }

    void populateTimelineScroll() {
        Timeline timeline = timelines_list.getSelectionModel().getSelectedItem();
        timeline_page_box.setVisible(true);
        timeline_author.setText("by "+ timeline.getCreatedBy().getFullname());
        timeline_description.getChildren().clear();
        timeline_description.getChildren().add(new Text(timeline.getDescription()));
        timeline_name.setText(timeline.getTitle());

            //create scroll listener
//            timeline_scroll_pane.hvalueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//                timelineVisualizerController.timelineScroll(newValue.doubleValue());
//            });
//
//            timeline_scroll_pane.viewportBoundsProperty()

    }

    void populateTimelist() {
//        if (currentUser.equals(timeline_author)) {
//            timelineList = currentUser.getTimelines();
//            ObservableList<Timeline> items = FXCollections.observableArrayList(timelineList.stream().collect(Collectors.toList()));
//            timelines_list.setItems(items);
//            Utils.generateTimeList(timelines_list);
//            timelines_list.refresh();
//        }
//        else {
            timelineList = Timeline.loadAll();
            ObservableList<Timeline> items = FXCollections.observableArrayList(timelineList.stream().collect(Collectors.toList()));
            timelines_list.setItems(items);
            Utils.generateTimeList(timelines_list);
            timelines_list.refresh();
//        }

//        populateTimelineScroll();
//        timeline_scroll_pane.setHvalue(0.0);

    }

    void makePopup(FXMLLoader f, Stage s) throws IOException {
        Parent ownerWindow = f.load();

        s.setScene(new Scene(ownerWindow));
        s.initOwner(Main.getPrimaryStage());
        s.initModality(Modality.NONE);
        s.initStyle(StageStyle.TRANSPARENT);
    }

    public void turnOpaque() {
        opaqueRegion.setStyle("-fx-background-color: #00000044;");
        opaqueRegion.setVisible(true);
    }

    public void turnNotOpaque() {
        opaqueRegion.setVisible(false);
    }
}
