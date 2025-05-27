package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Request.PutBill;
import com.wirehec.front_wirehec.DTO.FacturaDTO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class UpdateBillController {

    @FXML private TextField priceField;
    @FXML private TextField zoneField;
    @FXML private TextField directionField;

    private FacturaDTO factura;

    public void setBillData(FacturaDTO factura) {
        this.factura = factura;
        priceField.setText(factura.getPrecio().toString());
        zoneField.setText(factura.getZona());
        directionField.setText(factura.getDireccion());
    }

    @FXML
    private void handleSave() {
        try {
            BigDecimal price = new BigDecimal(priceField.getText());
            String zone = zoneField.getText();
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