package org.arthan.semantic.desktop.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.arthan.semantic.desktop.sample.model.GraphItem;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class Controller {
    public static final String USER_HOME = System.getProperty("user.home");

    @FXML
    private Text welcome_text;
    @FXML
    private TextField fineName_field;
    @FXML
    private Text fileType;
    @FXML
    private ComboBox<GraphItem> predicateCombobox;
    @FXML
    private ComboBox<GraphItem> anotherResourceCombobox;

    @FXML
    protected void handleAddButtonEvent(ActionEvent event) {
        if (!validInHome()) {
            showNotInHomeAlert();
            return;
        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Добавить");
        alert.setHeaderText(null);
        String contentText = new StringBuilder()
                .append("Имя файла: ").append(fineName_field.getText()).append("\n")
                .append("Предикат:  ").append(predicateCombobox.getValue().getUri()).append("\n")
                .append("Объект:    ").append(anotherResourceCombobox.getValue().getUri()).append("\n")
                .toString();
        alert.setContentText(contentText);

        alert.showAndWait();

        String answer = HttpUtils.addFile(
                fineName_field.getText(),
                predicateCombobox.getValue().getUri(),
                anotherResourceCombobox.getValue().getUri()
        );

        processAnswer(answer);

        welcome_text.setText("Ресурс добавлен");
    }

    private void processAnswer(String answer) {
        JSONParser parser = new JSONParser();
        JSONObject jsonAnswer;
        try {
            jsonAnswer = (JSONObject) parser.parse(answer);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jo = (JSONObject) jsonAnswer.get("answer");
        String status = (String) jo.get("status");

        if (status.equals("success") || status.equals("added")) {
            showSuccessAdditionAlert();
        }
    }

    private void showSuccessAdditionAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно");
        alert.setHeaderText(null);
        alert.setContentText("Файл успешно добавлен в семантический граф");

        alert.showAndWait();
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


    public void setFileField(String firstParam) {
        fineName_field.setText(firstParam);
    }

    public void determineFileType() {
        FILE_TYPE type = findType();
        fileType.setText(type.getTitle());

        setPredicatesForType(type);
        setAnotherResourceForType(type);
    }

    private void setAnotherResourceForType(FILE_TYPE type) {
        List<String> objectClassesUri = GraphUtils.findObjectClassesFor(
                type,
                predicateCombobox.getValue().getUri()
        );
        List<GraphItem> anotherResItems = HttpUtils.resourcesForClasses(objectClassesUri);

        ObservableList<GraphItem> anotherResources = FXCollections.observableArrayList(anotherResItems);
        anotherResourceCombobox.setItems(anotherResources);
        anotherResourceCombobox.getSelectionModel().selectFirst();
    }

    private FILE_TYPE findType() {
        return FileUtils.defineType(fineName_field.getText());
    }

    private void setPredicatesForType(FILE_TYPE type) {

        List<GraphItem> items = GraphUtils.findPredicatesForType(type);

        ObservableList<GraphItem> predicates = FXCollections.observableArrayList(items);

        predicateCombobox.setItems(predicates);
        predicateCombobox.getSelectionModel().selectFirst();
    }
}
