package org.arthan.semantic.desktop.sample.utils;

import javafx.scene.control.Alert;

/**
 * Created by artur.shamsiev on 29.05.2015
 */
public class AlertUtils {
    private static void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showSuccessAdditionAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно");
        alert.setHeaderText(null);
        alert.setContentText("Файл успешно добавлен в семантический граф");

        alert.showAndWait();
    }

    public static void showNotInHomeAlert() {
        error("Можно добавлять файлы только из домашнего каталога");
    }

    public static void showNoServerConnection() {
        error("Не могу подключиться к серверу");
    }

    public static void showNotSupportedFileType() {
        error("Тип файла не поддерживается");
    }
}
