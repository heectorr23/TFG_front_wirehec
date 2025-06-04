package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Request.PostBill;
import com.wirehec.front_wirehec.DTO.FacturaDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class AddBillController {

    @FXML private TextField priceField;
    @FXML private ComboBox<String> zoneComboBox;
    @FXML private TextField directionField;
    @FXML
    public void initialize() {
        zoneComboBox.setItems(FXCollections.observableArrayList("Norte", "Sur", "Centro", "Este", "Oeste"));
    }
    @FXML
    private void handleSave() {
        try {
            // Validar que los campos no estén vacíos
            if (priceField.getText().isEmpty() || zoneComboBox.getValue() == null || directionField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                return;
            }

            // Validar que el precio sea un número válido
            BigDecimal price;
            try {
                price = new BigDecimal(priceField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "El precio debe ser un número válido.");
                return;
            }

            String zone = zoneComboBox.getValue();
            String direction = directionField.getText();

            // Crear la factura y enviarla
            FacturaDTO factura = new FacturaDTO(null, price, zone, direction);
            new PostBill().sendPostBillRequest(factura);

            closeWindow();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al guardar la factura:\n" + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) priceField.getScene().getWindow();
        stage.close();
    }

}