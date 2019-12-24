package edu.mirea.fxml;

import edu.mirea.Vars;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {
    private Alert wrongData;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;

    {
        wrongData = new Alert(Alert.AlertType.ERROR);
    }

    @FXML
    private void clickExit() {
        System.exit(0);
    }

    @FXML
    private void clickLogin() throws IOException {
        if (login(loginField.getText(), passField.getText())) {
            Vars.changePage(getClass().getResource("main.fxml"));
        } else {
            wrongData.setContentText("wrong login or pass");
            wrongData.show();
        }
    }

    private boolean login(String login, String pass) {
        switch (login) {
            case "admin":
            case "root":
            case "master":
                Vars.isAdmin = true;
                break;
            case "user":
            case "misha":
            case "zhenya":
                Vars.isAdmin = false;
                break;
            default:
                return false;
        }
        return pass.equals(login + "123");
    }
}
