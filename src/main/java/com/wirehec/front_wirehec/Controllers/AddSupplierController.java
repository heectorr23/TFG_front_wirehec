package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request.PostSupplier;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
            // Validar que los campos no estén vacíos
            if (nombreField.getText().isEmpty() || cifField.getText().isEmpty() ||
                    emailField.getText().isEmpty() || categoriaComboBox.getValue() == null ||
                    productoField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                return;
            }

            // Validar que el CIF sea numérico
            int cifInt = Integer.parseInt(cifField.getText());

            // Crear el proveedor
            String nombre = nombreField.getText();
            String email = emailField.getText();
            String categoria = categoriaComboBox.getValue();
            String producto = productoField.getText();
            SupplierDTO supplier = new SupplierDTO(null, nombre, cifInt, email, categoria, producto);
            new PostSupplier().sendPostSupplierRequest(supplier);

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El campo CIF debe ser un valor numérico.");
        } catch (Exception e) {
            e.printStackTrace();
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
    private void closeWindow() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();
    }
}