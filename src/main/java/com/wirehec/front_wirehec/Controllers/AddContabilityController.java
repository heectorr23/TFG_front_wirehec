package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ContabilityApi.HTTP.Request.PostContability;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddContabilityController {

    @FXML private TextField presupuestoField;
    @FXML private TextField beneficioField;
    @FXML private TextField gastoField;
    @FXML private Button saveButton;


    @FXML
    public void initialize() {
        presupuestoField.textProperty().addListener((observable, oldValue, newValue) -> calculateBeneficio());
        gastoField.textProperty().addListener((observable, oldValue, newValue) -> calculateBeneficio());
    }

    private void calculateBeneficio() {
        try {
            float presupuesto = Float.parseFloat(presupuestoField.getText());
            float gasto = Float.parseFloat(gastoField.getText());
            float beneficio = presupuesto - gasto;
            beneficioField.setText(String.valueOf(beneficio));
        } catch (NumberFormatException e) {
            beneficioField.setText("");
        }
    }

    @FXML
    private void handleSave() {
        try {
            saveButton.setDisable(true); // Deshabilitar el botón para evitar múltiples clics

            float presupuesto = Float.parseFloat(presupuestoField.getText());
            float beneficio = Float.parseFloat(beneficioField.getText());
            float gasto = Float.parseFloat(gastoField.getText());

            ContabilityDTO newContability = new ContabilityDTO(null, presupuesto, beneficio, gasto);
            PostContability postContability = new PostContability();
            postContability.sendPostContabilityRequest(newContability);

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de Validación", "Por favor, ingresa valores numéricos válidos.");
        } finally {
            saveButton.setDisable(false); // Rehabilitar el botón
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) presupuestoField.getScene().getWindow();
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