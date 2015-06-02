package org.arthan.semantic.desktop.sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.arthan.semantic.desktop.sample.model.FileResourceGuiData;
import org.arthan.semantic.desktop.sample.model.FileTriple;
import org.arthan.semantic.desktop.sample.model.GraphItem;
import org.arthan.semantic.desktop.sample.service.UserResourceService;
import org.arthan.semantic.desktop.sample.utils.AlertUtils;
import org.arthan.semantic.desktop.sample.utils.JsonUtils;

import javax.inject.Inject;

public class Controller {
    public static final String USER_HOME = System.getProperty("user.home");

    UserResourceService userResourceService;

    @Inject
    public Controller(UserResourceService userResourceService) {
        this.userResourceService = userResourceService;
    }

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
        return userResourceService.addFile(getFileTriple());
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

    private FileTriple getFileTriple() {
        return new FileTriple(
                fineName_field.getText(),
                predicateCombobox.getValue(),
                anotherResourceCombobox.getValue()
        );
    }

    public void setup(String filePath) {
        FileResourceGuiData guiData = userResourceService.initGuiData(filePath);
        assignGuiData(guiData);
    }

    private void assignGuiData(FileResourceGuiData data) {
        fineName_field.setText(data.getTriple().getFilePath());
        fileType.setText(data.getFileType().getTitle());
        predicateCombobox.setItems(FXCollections.observableArrayList(data.getPredicates()));
        predicateCombobox.getSelectionModel().select(data.getTriple().getPredicate());
        anotherResourceCombobox.setItems(FXCollections.observableArrayList(data.getObjects()));
        anotherResourceCombobox.getSelectionModel().select(data.getTriple().getObject());
    }
}
