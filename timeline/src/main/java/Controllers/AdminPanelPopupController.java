package Controllers;

import DB.DatabaseController;
import Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminPanelPopupController {

    @FXML
    private Button adminPanel_loadButton;

    @FXML
    private Label adminPanelLabel;

    @FXML
    private TextField adminPanelUsername;

    @FXML
    private TextField adminPanelFullName;

    @FXML
    private TextField adminPanelPassword;

    @FXML
    private TextField adminPanelPasswordHint;

    @FXML
    private Button buttonCreateAccount;

    @FXML
    private Button buttonDeleteAccount;

    @FXML
    private CheckBox checkBox_isAdmin;

    @FXML
    private ListView<User> admin_listView;

    @FXML
    void showEntries() {
        ObservableList<User> items = FXCollections.observableArrayList(DatabaseController.getAllUsers());

        admin_listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                admin_listView.setCellFactory(param -> new ListCell<User>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getUsername() == null) {
                            setText(null);
                        } else {
                            adminPanelUsername.setText(item.getUsername());
                            adminPanelFullName.setText(item.getFullname());
                            adminPanelPassword.setText(item.getPassword());
                            adminPanelPasswordHint.setText(item.getPasswordHint());
                            checkBox_isAdmin.setSelected(item.isAdmin());
                        }
                    }
                });
            }
        });

        admin_listView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getUsername() == null) {
                    setText(null);
                } else {
                    setText(item.getUsername());
                }
            }
        });

        admin_listView.setItems(items);
    }

    public void editAccount() {
        User banana = admin_listView.getSelectionModel().getSelectedItem();
        banana.editUser(adminPanelUsername.getText(), adminPanelFullName.getText(), adminPanelPassword.getText(), adminPanelPasswordHint.getText(), null , isAdmin());
        banana.update();
    }

    public void deleteAccount() {

    }

    public boolean isAdmin() {
        return checkBox_isAdmin.isSelected();
    }


}
