package edu.mirea.fxml;

import edu.mirea.Vars;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Remove {
    @FXML
    private HBox hBox;
    private ArrayList<String> strings = new ArrayList<>();
    private String[] whereArray = {"LIKE", "<", "=", ">"};

    @FXML
    private void initialize() {
        try {
            ResultSetMetaData metaData = Vars.getResultSet().getMetaData();
            int count = metaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                strings.add(metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hBox.getChildren().add(createVBox());
    }

    @FXML
    private void backspace() {
        int size = hBox.getChildren().size();
        if (size > 2) {
            hBox.getChildren().remove(size - 2, size);
        }
    }

    @FXML
    private void plus(ActionEvent event) {
        hBox.getChildren().add(new Label(((Button) event.getSource()).getText()));
        hBox.getChildren().add(createVBox());
    }

    @FXML
    private void click() {
        try {
            Vars.statement.executeUpdate("DELETE FROM " + Vars.currentTable + " WHERE " + getConditions());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You shall not pass!");
            alert.show();
        }
    }

    private String getConditions() {
        StringBuilder res = new StringBuilder();
        for (Node node : hBox.getChildren()) {
            if (node instanceof Label) {
                res.append(" ").append(((Label) node).getText()).append(" ");
            } else {
                res.append(getCondition((VBox) node));
            }
        }
        return res.toString();
    }

    private String getCondition(VBox vBox) {
        String first = ((ComboBox<String>) vBox.getChildren().get(0)).getValue();
        String res = first +
                " " + ((ComboBox<String>) vBox.getChildren().get(1)).getValue() + " ";
        String st = ((ComboBox<String>) vBox.getChildren().get(2)).getValue();
        if (st.equals("CUSTOM")) {
            try {
                if (Vars.getResultSet().getMetaData().getColumnType(Vars.getResultSet().findColumn(first)) == 4) {
                    res += ((TextField) vBox.getChildren().get(3)).getText();
                } else {
                    res += "'" + ((TextField) vBox.getChildren().get(3)).getText() + "'";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            res += st;
        }
        return res;
    }

    private VBox createVBox() {
        VBox vBox = new VBox();
        ComboBox<String> comboBox1 = new ComboBox<>();
        comboBox1.getItems().addAll(strings);
        comboBox1.setValue(strings.get(0));
        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll(whereArray);
        comboBox2.setValue(whereArray[0]);
        ComboBox<String> comboBox3 = new ComboBox<>();
        comboBox3.getItems().addAll(strings);
        comboBox3.getItems().add("CUSTOM");
        comboBox3.setValue("CUSTOM");
        TextField field = new TextField();
        field.setAlignment(Pos.CENTER);
        field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.replaceAll("'", "")));

        ChangeListener<? super String> listener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
        try {
            if (Vars.getResultSet().getMetaData().getColumnType(Vars.getResultSet().findColumn(comboBox1.getValue())) == 4) {
                field.textProperty().addListener(listener);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        comboBox1.setOnAction(event -> {
            try {
                field.textProperty().removeListener(listener);
                if (Vars.getResultSet().getMetaData().getColumnType(Vars.getResultSet().findColumn(comboBox1.getValue())) == 4) {
                    field.textProperty().addListener(listener);
                    field.setText(field.getText().replaceAll("[^\\d]", ""));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        comboBox3.setOnAction((event -> {
            if (comboBox3.getValue().equals("CUSTOM")) {
                field.setDisable(false);
            } else {
                field.setDisable(true);
            }
        }));
        vBox.getChildren().addAll(comboBox1, comboBox2, comboBox3, field);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
