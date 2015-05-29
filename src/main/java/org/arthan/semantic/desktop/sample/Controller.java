package org.arthan.semantic.desktop.sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.arthan.semantic.desktop.sample.model.GraphItem;
import org.arthan.semantic.desktop.sample.utils.AlertUtils;
import org.arthan.semantic.desktop.sample.utils.JsonUtils;

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
            AlertUtils.showNotInHomeAlert();
            return;
        }

        String answer = addFile();

        processAnswer(answer);
    }

    private String addFile() {
        return HttpUtils.addFile(
                fineName_field.getText(),
                predicateCombobox.getValue().getUri(),
                anotherResourceCombobox.getValue().getUri()
        );
    }

    private void processAnswer(String answer) {
        String status = JsonUtils.parseAnswer(answer);

        if (status.equals("success") || status.equals("added")) {
            AlertUtils.showSuccessAdditionAlert();
        }
    }

    private boolean validInHome() {
        return fineName_field.getText().startsWith(USER_HOME);
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
        List<GraphItem> predicates = GraphUtils.findPredicatesForType(type);
        ObservableList<GraphItem> viewPredicates = FXCollections.observableArrayList(predicates);

        predicateCombobox.setItems(viewPredicates);
        predicateCombobox.getSelectionModel().selectFirst();
    }

    public void assignFilePath(String firstParam) {
        setFileField(firstParam);
        determineFileType();
    }
}
