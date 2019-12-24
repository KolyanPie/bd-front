package edu.mirea.fxml;

import edu.mirea.Vars;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

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
        try {
            ResultSet resultSet = Vars.statement.executeQuery("SELECT passbase64sha256 FROM admin WHERE login = '" + login + "';");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                if (resultSet.getString(1).equals(base64sha256(pass + login))) {
                    Vars.isAdmin = true;
                    return true;
                }
            }
            resultSet = Vars.statement.executeQuery("SELECT passbase64sha256 FROM streamer WHERE login = '" + login + "';");
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(base64sha256(pass + login))) {
                    Vars.isAdmin = false;
                    return true;
                }
            }
            return false;
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String base64sha256(String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
