package Utils;

import Controllers.ConfirmationMessageController;
import Models.Timeline;
import Models.User;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

import java.io.IOException;

public class Utils {

    private String imageFile;
    private ImageView eventImageView;

    public static void addConfirmationPopup(Stage parent, String message, String description) {
        parent.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean unfocused, Boolean focused) {
                if (unfocused){
                    //confirmation popup
                    try {
                        makeText(parent, message, description);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static void makeText(Stage ownerStage, String TitleText, String DescriptionText) throws IOException {
        Stage confirmationStage = new Stage();
        confirmationStage.initOwner(ownerStage);
        confirmationStage.setResizable(false);
        confirmationStage.initStyle(StageStyle.TRANSPARENT);

        FXMLLoader f = new FXMLLoader(Utils.class.getResource("/Views/ConfirmationMessage.fxml"));
        Parent root = f.load();
        ConfirmationMessageController cmc = f.getController();

        cmc.setParent(ownerStage);
        cmc.setText(TitleText, DescriptionText);

//        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 50px;");
//        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        confirmationStage.setScene(scene);
        confirmationStage.show();
    }

    public static void generateTimeList(ListView<Timeline> lsv) {
        lsv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lsv.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Timeline> ov, Timeline old_val, Timeline new_val) -> {
        });
        lsv.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Timeline t, boolean empty) {
                super.updateItem(t, empty);
                if (empty || t == null) {
                    setText(null);
                    setStyle("");
                } else {
                    VBox container = new VBox();
                    container.setPadding(new Insets(10,10,10,10));
                    Text title = new Text();
                    if (t.getTitle()!=null) {
                        title.setText(t.getTitle());
                    }
                    Font font = Font.font(23.0);
                    title.setFont(font);
                    Label authorName = new Label();
                    authorName.setStyle("-fx-label-padding: 0 10 0 0");
                    //protect against null pointers
                    authorName.setText("Created by " + t.getCreatedBy().getUsername());
                    container.getChildren().addAll(title,authorName);
                    setGraphic(container);
                }
            }
        });
    }

}
