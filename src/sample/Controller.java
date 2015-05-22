package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private Text welcome_text;
    @FXML
    private TextField fineName_field;

    @FXML
    protected void handleAddButtonEvent(ActionEvent event) {
        welcome_text.setText("Ресурс добавлен");
    }

    @FXML
    protected void anotherActionEvent(ActionEvent event) {
        System.out.println("event happened");
    }


    public void setFileField(String firstParam) {
        fineName_field.setText(firstParam);
    }
}
