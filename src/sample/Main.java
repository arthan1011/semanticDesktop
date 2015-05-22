package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Simple app");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        String firstParam = getParameters().getUnnamed().get(0);
        Controller controller = fxmlLoader.getController();
        controller.setFileField(firstParam);
//        fineName_field.setText(firstParam);
        System.out.println(firstParam);
        System.out.println(getParameters().getNamed().get("param1"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
