package edu.mirea.fxml;

import edu.mirea.Vars;
import edu.mirea.utils.GridUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;

public class Add {
    @FXML
    private GridPane gridPane;

    @FXML
    private void initialize() {
        try {
            GridUtils.addTable(gridPane, Vars.getResultSet().getMetaData());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void click() {
        try {
            Vars.statement.executeUpdate("INSERT INTO " + Vars.currentTable + " values(" + GridUtils.addValues(gridPane, Vars.getResultSet().getMetaData()) + ")");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You shall not pass!");
            alert.show();
        }
    }
}
