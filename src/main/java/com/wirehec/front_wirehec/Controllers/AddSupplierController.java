package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request.PostSupplier;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddSupplierController {

    @FXML private TextField nombreField;
    @FXML private TextField cifField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> categoriaComboBox;
    @FXML private TextField productoField;

    @FXML
    public void initialize() {
        categoriaComboBox.setItems(FXCollections.observableArrayList("Cable", "Acero", "Cadena", "Varios", "Accesorios"));
    }

    @FXML
    private void handleSave() {
        try {
            String nombre = nombreField.getText();
            String cif = cifField.getText();
            String email = emailField.getText();
            String categoria = categoriaComboBox.getValue();
            String producto = productoField.getText();

            if (categoria == null) {
                throw new IllegalArgumentException("Debe seleccionar una categoría.");
            }

            int cifInt = Integer.parseInt(cif);
            SupplierDTO supplier = new SupplierDTO(null, nombre, cifInt, email, categoria, producto);
            new PostSupplier().sendPostSupplierRequest(supplier);

            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();
    }
}