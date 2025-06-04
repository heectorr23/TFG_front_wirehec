package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Request.PutBill;
import com.wirehec.front_wirehec.DTO.FacturaDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class UpdateBillController {

    @FXML private TextField priceField;
    @FXML private ComboBox<String> zoneComboBox;
    @FXML private TextField directionField;

    private FacturaDTO factura;

    @FXML
    public void initialize() {
        zoneComboBox.setItems(FXCollections.observableArrayList("Norte", "Sur", "Centro", "Este", "Oeste"));
    }

    public void setBillData(FacturaDTO factura) {
        this.factura = factura;
        priceField.setText(factura.getPrecio().toString());
        zoneComboBox.setValue(factura.getZona());
        directionField.setText(factura.getDireccion());
    }

    @FXML
    private void handleSave() {
        try {
            BigDecimal price = new BigDecimal(priceField.getText());
            String zone = zoneComboBox.getValue();
            String direction = directionField.getText();

            factura.setPrecio(price);
            factura.setZona(zone);
            factura.setDireccion(direction);

            new PutBill().sendPutBillRequest(factura, factura.getId());

            closeWindow();
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
}