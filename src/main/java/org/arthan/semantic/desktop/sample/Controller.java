package org.arthan.semantic.desktop.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {
    public static final String USER_HOME = System.getProperty("user.home");

    @FXML
    private Text welcome_text;
    @FXML
    private TextField fineName_field;
    @FXML
    private Text fileType;

    @FXML
    protected void handleAddButtonEvent(ActionEvent event) {
        if (!validInHome()) {
            showNotInHomeAlert();
            return;
        }

        addAsMusic();
        welcome_text.setText("Ресурс добавлен");
    }

    private void addAsMusic() {
        String answer = HttpUtils.post("http://localhost:8080/semantic/restful/music", "id=" + fineName_field.getText());
        System.out.println(answer);
    }

    private boolean validInHome() {
        return fineName_field.getText().startsWith(USER_HOME);
    }

    private void showNotInHomeAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Можно добавлять файлы только из домашнего каталога");

        alert.showAndWait();
    }

    @FXML
    protected void anotherActionEvent(ActionEvent event) {
        System.out.println("event happened");
    }


    public void setFileField(String firstParam) {
        fineName_field.setText(firstParam);
    }

    public void determineFileType() {
        FILE_TYPE type = FileUtils.defineType(fineName_field.getText());
        fileType.setText(type.getTitle());
    }
}
