package Controllers;

import Models.User;

import Utils.FileController;
import Utils.KeyPress;
import Utils.Toast;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;

import java.io.File;
import java.io.IOException;


import static DB.DatabaseController.usernameTaken;

public class AccountViewController  {

    private Parent parent;
    private User user;
    private File selectedFile;

    @FXML
    private StackPane LoginBox;

    @FXML
    private TextField UserNameTextField;

    @FXML
    private PasswordField UserNamePasswordField;

    @FXML
    private Button LogInButton;

    @FXML
    private Button RegisterButton;

    @FXML
    private Button goBackButton;

    @FXML
    private StackPane createAccountStackPane;

    @FXML
    private VBox createAccountVBox;

    @FXML
    private Label createAccountLabel;

    @FXML
    private TextField createAccountUsername;

    @FXML
    private TextField createAccountFullName;

    @FXML
    private PasswordField createAccountPassword;

    @FXML
    private TextField createAccountPasswordHint;

    @FXML
    private HBox createAccountHBox;

    @FXML
    private Button selectFileButton;

    @FXML
    private Label fileNotSelectedLabel;

    @FXML
    private Button buttonCreateAccount;

    @FXML
    private ImageView imageViewCreateAccount;

    @FXML
    private Button backToLoginButton;

    @FXML
    public void showRegistrationForm() {
        createAccountStackPane.setVisible(true);
        LoginBox.setVisible(false);
    }

    @FXML
    public void showLoginForm() {
        createAccountStackPane.setVisible(false);
        LoginBox.setVisible(true);
    }

    @FXML
    public void goBack() throws IOException {
//        KeyPress.escPress(goBackButton);

        if (user != null) {
            backToMain(this.user);
        } else {
            Scene login = new Scene(FXMLLoader.load(getClass().getResource("/Views/Main.fxml")));
            Main.getPrimaryStage().setScene(login);
        }
    }

    @FXML
    public void createAccount() throws IOException {
        String username = createAccountUsername.getText();
        String fullName = createAccountFullName.getText();
        String pwd = createAccountPassword.getText();
        String pwdHint = createAccountPasswordHint.getText();
        String userimage;

//        KeyPress.enterPress(buttonCreateAccount);

        if (this.user == null) {
//            path = FileController.getAbsolutePath(selectedFile);
//            if (!fileNotSelectedLabel.getText().equals("File not selected")) {
//                FileController.moveFileToDir(selectedFile);
//                path = FileController.getAbsolutePath(selectedFile);
//            }

            if (usernameTaken(username)) {
                Toast.makeText(Main.getPrimaryStage(), "This username is taken", 50, 120, 120);
            }
            else {
                if (fileNotSelectedLabel.getText().equals("File not selected")) {
                    userimage = "src/main/resources/Images/EditAccount.png";
                }
                else {
                    userimage = FileController.selectedFilePath;
                }

                User newUser = new User(username, fullName, pwd, pwdHint, userimage, false);

                newUser.save();
                backToMain(newUser);
            }
        }
        else {
            if (fileNotSelectedLabel.getText().equals("File not selected") || fileNotSelectedLabel.getText() == null) {
                userimage = "src/main/resources/Images/EditAccount.png";
            }
            else {
                userimage = FileController.selectedFilePath;
            }

            this.user.editUser(username, fullName, pwd, pwdHint, userimage, false);
            this.user.update();
            backToMain(this.user);
        }
    }


    @FXML
    public void editAccount(User user) throws IOException {
        showRegistrationForm();
        createAccountLabel.setText("Edit Account");
        buttonCreateAccount.setText("Save");
        backToLoginButton.setVisible(false);

        this.user = user;

        createAccountUsername.setText(user.getUsername());
        createAccountFullName.setText(user.getFullname());
        createAccountPassword.setText(user.getPassword());
        createAccountPasswordHint.setText(user.getPasswordHint());
        fileNotSelectedLabel.setText(user.getImage());

        if (user.getImage() != null) {
            imageViewCreateAccount.setImage(new Image(new File(user.getImage()).toURI().toString()));
        }
    }

    @FXML
    public void loginPress() throws IOException {
        String userName = UserNameTextField.getText();
        String password = UserNamePasswordField.getText();

//        KeyPress.enterPress(LogInButton);

        if (userName.length() == 0 || password.length() == 0) {
            Toast.makeText(Main.getPrimaryStage(), "No input",3500, 500, 500);
        }
        else {
            User user = User.login(userName, password);
            if(user == null){
                Toast.makeText(Main.getPrimaryStage(), "Not found",3500, 500, 500);
            }
            else{
                backToMain(user);
            }
        }
    }


    @FXML
    public void fileChooser() throws IOException {
        selectedFile = FileController.fileChooser();

        imageViewCreateAccount.setImage(new Image(selectedFile.toURI().toURL().toExternalForm()));

        fileNotSelectedLabel.setText(selectedFile.getName());
    }
    ///////////////////////////////// GETTERS SETTERS

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /////////////////////////////////

    ///////////////////////////////// UTILS
    void backToMain(User user) throws IOException {
        FXMLLoader f = new FXMLLoader(getClass().getResource("/Views/Main.fxml"));
        Parent mainScreen = f.load();
        MainController mc = f.getController();

        Scene mainScene = new Scene(mainScreen);

        mc.setCurrentUser(user);
        mc.checkLoggedUser();

        Main.getPrimaryStage().setScene(mainScene);
    }
    /////////////////////////////////
}
