package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Request.PostCustomer;
import com.wirehec.front_wirehec.DTO.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCustomerController {

    @FXML private TextField nameField;
    @FXML private TextField contactoField;
    @FXML private TextField telefonoField;
    @FXML private TextField identificacionField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> zonaComboBox;
    @FXML private TextField direccionField;

    private CustomerController customerController;

    @FXML
    private void initialize() {
        zonaComboBox.setItems(FXCollections.observableArrayList("Norte", "Sur", "Centro", "Este", "Oeste"));
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (!validateFields()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, completa todos los campos correctamente.");
            return;
        }

        try {
            CustomerDTO customer = new CustomerDTO();
            customer.setName(nameField.getText());
            customer.setContacto(contactoField.getText());
            customer.setTelefono(Integer.parseInt(telefonoField.getText()));
            customer.setIdentificacion(identificacionField.getText());
            customer.setEmail(emailField.getText());
            customer.setZona(zonaComboBox.getValue());
            customer.setDireccion(direccionField.getText());

            new PostCustomer().sendPostCustomerRequest(customer);

            if (customerController != null) {
                customerController.cargarDatos(); // Actualizar la tabla
            }

            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente añadido correctamente.");
            closeWindow();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El teléfono debe ser un número válido.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al añadir el cliente.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private boolean validateFields() {
        return !nameField.getText().isEmpty() &&
                !contactoField.getText().isEmpty() &&
                !telefonoField.getText().isEmpty() &&
                !identificacionField.getText().isEmpty() &&
                !emailField.getText().isEmpty() &&
                zonaComboBox.getValue() != null &&
                !direccionField.getText().isEmpty();
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