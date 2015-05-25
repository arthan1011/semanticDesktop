package org.arthan.semantic.desktop.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {


    public static final String FXML_PATH = "/sample.fxml";
    private Controller controller;
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        initLoader();

        setupStage(primaryStage);

        initController();
        setup();

    }

    private void setupStage(Stage primaryStage) throws java.io.IOException {
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Simple app");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void setup() {
        String firstParam = getParameters().getUnnamed().get(0);
        controller.setFileField(firstParam);
        controller.determineFileType();
    }

    private void initController() {
        controller = fxmlLoader.getController();
    }

    private void initLoader() {
        URL fxmlUrl = getClass().getResource(FXML_PATH);
        fxmlLoader = new FXMLLoader(fxmlUrl);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
