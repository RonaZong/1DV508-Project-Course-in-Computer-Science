package Utils;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

public class KeyPress {

    public static void enterPress(Button button) {
        button.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
            }
        });
    }

    public static void escPress(Button button) {
        button.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
            }
        });
    }
}
