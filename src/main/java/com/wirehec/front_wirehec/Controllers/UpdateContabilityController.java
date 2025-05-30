package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ContabilityApi.HTTP.Request.PutContability;
import com.wirehec.front_wirehec.DTO.ContabilityDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateContabilityController {

    @FXML private TextField presupuestoField;
    @FXML private TextField beneficioField;
    @FXML private TextField gastoField;

    private ContabilityDTO contabilityData;

    public void setContabilityData(ContabilityDTO contabilityData) {
        this.contabilityData = contabilityData;
        presupuestoField.setText(String.valueOf(contabilityData.getPresupuestomensual()));
        beneficioField.setText(String.valueOf(contabilityData.getBeneficio()));
        gastoField.setText(String.valueOf(contabilityData.getGasto()));
    }

    @FXML
    private void handleSave() {
        try {
            float presupuesto = Float.parseFloat(presupuestoField.getText());
            float beneficio = Float.parseFloat(beneficioField.getText());
            float gasto = Float.parseFloat(gastoField.getText());

            contabilityData.setPresupuestomensual(presupuesto);
            contabilityData.setBeneficio(beneficio);
            contabilityData.setGasto(gasto);

            PutContability putContability = new PutContability();
            putContability.sendPutContabilityRequest(contabilityData, contabilityData.getIdContabilidad());

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error de Validación", "Por favor, ingresa valores numéricos válidos.");
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