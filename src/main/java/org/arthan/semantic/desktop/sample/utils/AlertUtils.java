package org.arthan.semantic.desktop.sample.utils;

import javafx.scene.control.Alert;

/**
 * Created by artur.shamsiev on 29.05.2015
 */
public class AlertUtils {
    public static void showNotInHomeAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Можно добавлять файлы только из домашнего каталога");

        alert.showAndWait();
    }

    public static void showSuccessAdditionAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно");
        alert.setHeaderText(null);
        alert.setContentText("Файл успешно добавлен в семантический граф");

        alert.showAndWait();
    }
}
