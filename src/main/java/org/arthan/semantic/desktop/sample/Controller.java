package org.arthan.semantic.desktop.sample;

import com.hp.hpl.jena.vocabulary.DC;
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

        switch (findType()) {
            case MUSIC:
                addAsMusic();
                break;
        }
        welcome_text.setText("Ресурс добавлен");
    }

    private void addAsMusic() {
        String answer = HttpUtils.post("http://localhost:8080/semantic/restful/music", "id=" + fineName_field.getText());
        processAnswer(answer);
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

        if (status.equals("success")) {
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

    @FXML
    protected void anotherActionEvent(ActionEvent event) {
        System.out.println("event happened");
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
        // TODO Онтология должна определить и список третьих элементов триплета
        if (type == FILE_TYPE.DOCUMENT) {
            List<GraphItem> anotherResItems = HttpUtils.anotherResourcesFor(
                    type,
                    predicateCombobox.getValue().getUri()
            );
            ObservableList<GraphItem> anotherResources = FXCollections.observableArrayList(anotherResItems);
            anotherResourceCombobox.setItems(anotherResources);
            anotherResourceCombobox.getSelectionModel().selectFirst();
        }
    }

    private FILE_TYPE findType() {
        return FileUtils.defineType(fineName_field.getText());
    }

    private void setPredicatesForType(FILE_TYPE type) {
        // TODO Онтология должна определять список предикатов, ага.
        if (type == FILE_TYPE.DOCUMENT) {
            ObservableList<GraphItem> predicates = FXCollections.observableArrayList(
                    new GraphItem("Кем создан", DC.creator.getURI())
            );

            predicateCombobox.setItems(predicates);
            predicateCombobox.getSelectionModel().selectFirst();
        }
    }
}
