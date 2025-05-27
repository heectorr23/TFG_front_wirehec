package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.AuthAPI.HTTP.Request.PostRegisterUser;
import com.wirehec.front_wirehec.DTO.RegisterUserDTO;
import com.wirehec.front_wirehec.DTO.RoleDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

public class AddEmployeeController {

    @FXML private TextField nameField, nifField, phoneField, emailField, usernameField, benefitField, salaryField, entryTimeField, exitTimeField;
    @FXML private PasswordField passwordField;
    @FXML private DatePicker entryDatePicker, exitDatePicker;
    @FXML private ComboBox<String> roleComboBox;

    private final PostRegisterUser postRegisterUser = new PostRegisterUser();

    @FXML
    public void initialize() {
        // Inicializar el ComboBox con los roles disponibles
        roleComboBox.getItems().addAll("BOSS", "COMMERCIAL", "USER");
    }

    @FXML
    private void saveEmployee() {
        try {
            // Obtener datos del formulario
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String role = roleComboBox.getValue();
            int benefit = Integer.parseInt(benefitField.getText());
            BigDecimal salary = new BigDecimal(salaryField.getText());
            int phone = Integer.parseInt(phoneField.getText());
            String entryTimeText = entryTimeField.getText();
            String exitTimeText = exitTimeField.getText();
            LocalDate entryDate = entryDatePicker.getValue();
            LocalDate exitDate = exitDatePicker.getValue();
            String nif = nifField.getText();
            String name = nameField.getText();

            // Validar campos obligatorios
            if (entryDate == null || exitDate == null || entryTimeText.isEmpty() || exitTimeText.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Debes completar todos los campos obligatorios.");
                return;
            }

            // Convertir fechas y horas
            LocalTime entryTime = LocalTime.parse(entryTimeText);
            LocalTime exitTime = LocalTime.parse(exitTimeText);
            LocalDateTime entryDateTime = LocalDateTime.of(entryDate, entryTime);
            LocalDateTime exitDateTime = LocalDateTime.of(exitDate, exitTime);

            // Crear objeto RoleDTO
            RoleDTO roleDTO = RoleDTO.builder()
                    .name(role)
                    .build();

            // Crear objeto RegisterUserDTO
            RegisterUserDTO newUser = RegisterUserDTO.builder()
                    .username(username)
                    .passwordEmpleado(password)
                    .email(email)
                    .roles(Collections.singletonList(roleDTO))
                    .beneficioEmpleado(benefit)
                    .salario(salary)
                    .telefonoEmpleado(phone)
                    .horaEntrada(entryDateTime)
                    .horaSalida(exitDateTime)
                    .nifEmpleado(nif)
                    .nombreEmpleado(name)
                    .build();

            // Enviar solicitud POST al backend
            postRegisterUser.sendPostRegisterUserRequest(newUser);

            // Mostrar mensaje de éxito y cerrar ventana
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "El empleado ha sido registrado correctamente.");
            closeWindow();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}