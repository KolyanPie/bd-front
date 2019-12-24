package edu.mirea.utils;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GridUtils {
    public static void showTable(GridPane gridPane, ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        addHeaders(gridPane, resultSet.getMetaData());
        int i = 1;
        while (resultSet.next()) {
            gridPane.addRow(i);
            for (int j = 1; j <= count; j++) {
                gridPane.add(createLabel(resultSet.getObject(j).toString()), j - 1, i);
            }
            i++;
        }
        gridPane.autosize();
    }

    public static void addTable(GridPane gridPane, ResultSetMetaData resultSetMetaData) throws SQLException {
        addHeaders(gridPane, resultSetMetaData);
        gridPane.addRow(1);
        int count = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            TextField field = new TextField();
            if (resultSetMetaData.getColumnType(i) == 4) {
                field.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        field.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
            } else {
                field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.replaceAll("'", "")));
            }
            gridPane.addColumn(i - 1, field);
        }
        gridPane.autosize();
    }

    public static String addValues(GridPane gridPane, ResultSetMetaData resultSetMetaData) throws SQLException {
        StringBuilder res = new StringBuilder();
        int count = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            if (resultSetMetaData.getColumnType(i) == 4) {
                res.append(((TextField) gridPane.getChildren().get(count + i)).getText()).append(", ");
            } else {
                res.append("'").append(((TextField) gridPane.getChildren().get(count + i)).getText()).append("', ");
            }
        }
        int size = res.length();
        if (size > 2) {
            res = new StringBuilder(res.substring(0, size - 2));
        }
        return res.toString();
    }

    private static void addHeaders(GridPane gridPane, ResultSetMetaData resultSetMetaData) throws SQLException {
        gridPane.addRow(0);
        int count = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            Label label = createLabel(resultSetMetaData.getColumnName(i));
            label.setStyle("-fx-font-weight: bold");
            gridPane.addColumn(i - 1, label);
        }
        gridPane.autosize();
    }

    private static Label createLabel(String string) {
        Label label = new Label(string);
        label.setPadding(new Insets(3, 10, 3, 10));
        return label;
    }
}
