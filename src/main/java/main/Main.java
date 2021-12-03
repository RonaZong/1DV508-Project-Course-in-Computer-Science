package main;

import Controllers.MainController;
import DB.Database;
import DB.DatabaseController;
import ch.vorburger.exec.ManagedProcessException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    //final private Persistance persistance = Persistance.DB;
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    static public void setPrimaryStage(Stage primaryStage) {
        Main.primaryStage = primaryStage;
    }


    @Override
    public void start(Stage primaryStage) throws IOException, ManagedProcessException {
        //db start
        try {
            Database.startDB();
//            DatabaseController.startDB();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // set stage
        Main.primaryStage = primaryStage;

        String mainFxmlPathString = "/Views/Main.fxml";
        MainController controller = new MainController();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(mainFxmlPathString));

//        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Views/Main.fxml")));
//
//        MainController controller = loader.getController();

        primaryStage.setTitle("Sparkling new Timeline Manager");
        StackPane main = loader.load();
        main.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(main, controller.WINDOW_WIDTH, controller.WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

