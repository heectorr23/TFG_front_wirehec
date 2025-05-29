package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request.PutSupplier;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateSupplierController {

    @FXML private TextField nombreField;
    @FXML private TextField cifField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> categoriaComboBox;
    @FXML private TextField productoField;

    private SupplierDTO supplier;

    @FXML
    public void initialize() {
        categoriaComboBox.setItems(FXCollections.observableArrayList("Cable", "Acero", "Cadena", "Varios", "Accesorios"));
    }

    public void setSupplierData(SupplierDTO supplier) {
        this.supplier = supplier;
        nombreField.setText(supplier.getNombreProveedor());
        cifField.setText(String.valueOf(supplier.getCifProveedor()));
        emailField.setText(supplier.getEmailProveedor());
        categoriaComboBox.setValue(supplier.getCategoriaProveedor());
        productoField.setText(supplier.getProductoProveedor());
    }

    @FXML
    private void handleSave() {
        try {
            String nombre = nombreField.getText();
            int cif = Integer.parseInt(cifField.getText());
            String email = emailField.getText();
            String categoria = categoriaComboBox.getValue();
            String producto = productoField.getText();

            SupplierDTO updatedSupplier = new SupplierDTO(null, nombre, cif, email, categoria, producto);
            new PutSupplier().sendPutSupplierRequest(updatedSupplier, supplier.getIdProveedor());

            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCancel() {
        closeWindow();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();
    }
}