package edu.mirea;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Vars {
    public static Stage stage;
    public static boolean isAdmin;
    public static String currentTable;
    public static Statement statement;
    public static ArrayList<MenuItem> menuItems;

    public static ResultSet getResultSet() throws SQLException {
        return statement.executeQuery("SELECT * FROM " + currentTable + ";");
    }

    public static void changePage(URL location) throws IOException {
        Parent root = FXMLLoader.load(location);
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
    }
}
