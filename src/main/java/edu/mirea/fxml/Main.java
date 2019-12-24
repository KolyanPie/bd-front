package edu.mirea.fxml;

import edu.mirea.Vars;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    private Connection connection;
    //FIXME:
    private final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test";
    private final String USER = "username";
    private final String PASS = "password";

    @FXML
    private Menu menu;
    @FXML
    private FlowPane flowPane;

    @FXML
    private void initialize() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Vars.statement = connection.createStatement();
            ResultSet resultSet = Vars.statement.executeQuery("SELECT table_name FROM information_schema.tables  where table_schema='public' ORDER BY table_name;");
            Vars.menuItems = new ArrayList<>();
            while (resultSet.next()) {
                MenuItem menuItem = new MenuItem(resultSet.getString(1));
                menuItem.setOnAction(this::table);
                menu.getItems().add(menuItem);
                Vars.menuItems.add(menuItem);
                Button button = new Button(resultSet.getString(1));
                button.setOnAction(this::table);
                flowPane.getChildren().add(button);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void table(ActionEvent event) {
        if (event.getSource() instanceof MenuItem) {
            Vars.currentTable = ((MenuItem) event.getSource()).getText();
        } else {
            Vars.currentTable = ((Button) event.getSource()).getText();
        }
        try {
            Vars.changePage(getClass().getResource("table.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
