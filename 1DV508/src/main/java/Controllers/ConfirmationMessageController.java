package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class ConfirmationMessageController {

    private Window ownerWindow;

    @FXML
    private Text ConfirmationTitle;

    @FXML
    private Label OptionalId;

    @FXML
    private Button ButtonNo;

    @FXML
    private Button ButtonYes;

    @FXML
    protected void close() {
        ButtonNo.getScene().getWindow().hide();
        ownerWindow.getScene().getWindow().hide();
    }

    @FXML
    protected void doNotClose() {
       ButtonYes.getScene().getWindow().hide();
    }

    public void setText(String title, String desc) {
        ConfirmationTitle.setText(title);
        OptionalId.setText(desc);
    }

    public Window getParent() {
        return ownerWindow;
    }

    public void setParent(Window parent) {
        this.ownerWindow = parent;
    }

    public Text getConfirmationTitle() {
        return ConfirmationTitle;
    }

    public void setConfirmationTitle(Text confirmationTitle) {
        ConfirmationTitle = confirmationTitle;
    }

    public Label getOptionalId() {
        return OptionalId;
    }

    public void setOptionalId(Label optionalId) {
        OptionalId = optionalId;
    }
}
