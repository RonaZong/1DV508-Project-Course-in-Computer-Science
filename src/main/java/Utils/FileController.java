package Utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileController {

    static String mainFolderName = "time_manager";
    public static String selectedFilePath;
    static File customDir  = new File(System.getProperty("user.home"), mainFolderName);

    public static File fileChooser() throws IOException {
//        Utils.addConfirmationPopup(true);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle ("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files","*.png", "*.jpg", "*.gif", "*.pdf"));

        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
        Path copy = Paths.get("src/main/resources/Images", file.getName());

        if (hasHomeDirectory()) {
            selectedFilePath = copy.toString();
        }
        else {
            selectedFilePath = copy.toString();
            Files.copy(file.toPath(), copy);
        }

        return copy.toFile();
    }

    public static String getAbsolutePath (File file) {
        return file.getAbsolutePath();
    }

    public static boolean hasHomeDirectory() {
        return customDir.exists();
    }





//    public static void moveFileToDir(File file) throws IOException {
//        if (!hasHomeDirectory()) {
//            customDir.mkdirs();
//        }
//        FileUtils.copyFileToDirectory(file, customDir);
//    }

//    public static void copy(String from, String to) throws IOException {
//        Image image = new Image(new FileInputStream(from));
//        Image copiedImage = new Image(to);
//
//        int width = (int)image.getWidth();
//        int height = (int)image.getHeight();
//
//        WritableImage wImage = new WritableImage(width, height);
//
//        PixelReader pixelReader = image.getPixelReader();
//
//        PixelWriter writer = wImage.getPixelWriter();
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                Color color = pixelReader.getColor(x, y);
//
//                writer.setColor(x, y, color);
//            }
//        }
//    }

//    public static void fileChooser(Node node){
//        FileChooser filePicChooser = new FileChooser();
//        filePicChooser.setTitle ("Select Image");
//
//        filePicChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("PNG files", "*.png")
//                ,new FileChooser.ExtensionFilter("JPG files", "*.jpg")
//        );
//        File selectedFile = filePicChooser.showOpenDialog(new Stage());
//        this.imageFile = selectedFile.getName();
//        this.eventImageView = new ImageView(new Image(getClass().getResourceAsStream(selectedFile.getAbsolutePath())));
//    }
}
