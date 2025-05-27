package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Request.PostBill;
import com.wirehec.front_wirehec.DTO.FacturaDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class AddBillController {

    @FXML private TextField priceField;
    @FXML private TextField zoneField;
    @FXML private TextField directionField;

    @FXML
    private void handleSave() {
        try {
            if (priceField.getText().isEmpty() || zoneField.getText().isEmpty() || directionField.getText().isEmpty()) {
                showAlert("Error", "Todos los campos son obligatorios.");
                return;
            }

            BigDecimal price = new BigDecimal(priceField.getText());
            String zone = zoneField.getText();
            String direction = directionField.getText();

            FacturaDTO factura = new FacturaDTO(null, price, zone, direction);
            new PostBill().sendPostBillRequest(factura);

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert("Error", "El precio debe ser un número válido.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) priceField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        System.err.println(title + ": " + content);
    }
}