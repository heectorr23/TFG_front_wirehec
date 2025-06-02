package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.AuthAPI.HTTP.Request.PostLoginUser;
import com.wirehec.front_wirehec.Constants.TokenConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;

    private final PostLoginUser postLoginUser = new PostLoginUser();
    private final TokenConstants tokenConstants  = new TokenConstants();

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String token = postLoginUser.sendPostLoginUserRequest(username, password);
        if (token != null) {
            TokenConstants.setToken(token);
            showAlert(Alert.AlertType.INFORMATION, "Login Exitoso", "Inicio de sesión exitoso.");
            goToHelloView();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error en el Login", "Credenciales incorrectas.");
        }
    }

    private void goToHelloView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/MainViews/hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}