package edu.mirea.fxml;

import edu.mirea.Vars;
import edu.mirea.utils.GridUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Table {
    @FXML
    private GridPane gridPane;
    @FXML
    private Menu menu;

    @FXML
    private void initialize() {
        try {
            menu.getItems().addAll(Vars.menuItems);
            GridUtils.showTable(gridPane, Vars.getResultSet());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showWindow(String action) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(action + ".fxml"));
        Stage window = new Stage();
        window.setTitle(action);
        window.setScene(new Scene(parent));
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(Vars.stage);
        window.show();
    }

    @FXML
    private void buttonClick(ActionEvent actionEvent) throws IOException {
        showWindow(((Button) actionEvent.getSource()).getText());
    }

    @FXML
    private void mainPage() throws IOException {
        Vars.changePage(getClass().getResource("main.fxml"));
    }
}
