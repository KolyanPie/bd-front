package edu.mirea;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {
    //FIXME:
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test";
    private static final String USER = "username";
    private static final String PASS = "password";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Vars.statement = connection.createStatement();
            launch(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Vars.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("unidb");
        primaryStage.show();
    }
}
