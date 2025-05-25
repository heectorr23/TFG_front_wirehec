package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.AuthAPI.HTTP.Request.PostRegisterUser;
import com.wirehec.front_wirehec.DTO.RegisterUserDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextField beneficioField;
    @FXML
    private TextField salarioField;
    @FXML
    private TextField telefonoField;
    @FXML
    private DatePicker fechaEntradaPicker;
    @FXML
    private TextField horaEntradaField;
    @FXML
    private DatePicker fechaSalidaPicker;
    @FXML
    private TextField horaSalidaField;
    @FXML
    private TextField nifField;
    @FXML
    private TextField nombreField;
    @FXML
    private Button registerButton;

    private final PostRegisterUser postRegisterUser = new PostRegisterUser();

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("BOSS", "COMMERCIAL", "USER");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String role = roleComboBox.getValue();
            int beneficio = Integer.parseInt(beneficioField.getText());
            BigDecimal salario = new BigDecimal(salarioField.getText());
            int telefono = Integer.parseInt(telefonoField.getText());
            String horaEntradaTexto = horaEntradaField.getText();
            String horaSalidaTexto = horaSalidaField.getText();
            LocalDate fechaEntrada = fechaEntradaPicker.getValue();
            LocalDate fechaSalida = fechaSalidaPicker.getValue();

            if (fechaEntrada == null || fechaSalida == null || horaEntradaTexto.isEmpty() || horaSalidaTexto.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error en el Registro", "Debes completar las fechas y horas.");
                return;
            }

            LocalTime horaEntrada = LocalTime.parse(horaEntradaTexto);
            LocalTime horaSalida = LocalTime.parse(horaSalidaTexto);

            LocalDateTime fechaHoraEntrada = LocalDateTime.of(fechaEntrada, horaEntrada);
            LocalDateTime fechaHoraSalida = LocalDateTime.of(fechaSalida, horaSalida);

            String nif = nifField.getText();
            String nombre = nombreField.getText();

            RoleDTO roleDTO = RoleDTO.builder()
                    .name(role)
                    .build();

            RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
                    .username(username)
                    .passwordEmpleado(password)
                    .email(email)
                    .roles(Collections.singletonList(roleDTO))
                    .beneficioEmpleado(beneficio)
                    .salario(salario)
                    .telefonoEmpleado(telefono)
                    .horaEntrada(fechaHoraEntrada)
                    .horaSalida(fechaHoraSalida)
                    .nifEmpleado(nif)
                    .nombreEmpleado(nombre)
                    .build();

            postRegisterUser.sendPostRegisterUserRequest(registerUserDTO);

            showAlert(Alert.AlertType.INFORMATION, "Registro Exitoso", "El usuario ha sido registrado correctamente.");
            goToLoginView();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error en el Registro", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void goToLoginView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/AuthViews/Login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void navigateToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/AuthViews/Login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de login.");
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