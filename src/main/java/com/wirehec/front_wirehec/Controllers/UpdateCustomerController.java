package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Request.PutCustomer;
import com.wirehec.front_wirehec.DTO.CustomerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateCustomerController {

    @FXML private TextField nameField;
    @FXML private TextField contactoField;
    @FXML private TextField telefonoField;
    @FXML private TextField identificacionField;
    @FXML private TextField emailField;
    @FXML private TextField zonaField;
    @FXML private TextField direccionField;

    private CustomerDTO customer;
    private CustomerController customerController;

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
        if (customer != null) {
            nameField.setText(customer.getName());
            contactoField.setText(customer.getContacto());
            telefonoField.setText(String.valueOf(customer.getTelefono()));
            identificacionField.setText(customer.getIdentificacion());
            emailField.setText(customer.getEmail());
            zonaField.setText(customer.getZona());
            direccionField.setText(customer.getDireccion());
        }
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (customer == null || customer.getId() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "El cliente no tiene un ID válido.");
            return;
        }

        try {
            // Actualizar solo los campos que no están vacíos
            if (nameField.getText() != null && !nameField.getText().isEmpty()) {
                customer.setName(nameField.getText());
            }
            if (contactoField.getText() != null && !contactoField.getText().isEmpty()) {
                customer.setContacto(contactoField.getText());
            }
            if (telefonoField.getText() != null && !telefonoField.getText().isEmpty()) {
                try {
                    customer.setTelefono(Integer.parseInt(telefonoField.getText()));
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "El teléfono debe ser un número válido.");
                    return;
                }
            }
            // Si el campo está vacío, no sobrescribir el valor actual
            if (identificacionField.getText() != null) {
                if (!identificacionField.getText().isEmpty()) {
                    customer.setIdentificacion(identificacionField.getText());
                }
            }
            if (emailField.getText() != null && !emailField.getText().isEmpty()) {
                customer.setEmail(emailField.getText());
            }
            if (zonaField.getText() != null && !zonaField.getText().isEmpty()) {
                customer.setZona(zonaField.getText());
            }
            if (direccionField.getText() != null && !direccionField.getText().isEmpty()) {
                customer.setDireccion(direccionField.getText());
            }

            // Enviar la solicitud PUT con el ID del cliente
            new PutCustomer().sendPutCustomerRequest(customer, customer.getId());

            if (customerController != null) {
                customerController.cargarDatos();
            }

            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente actualizado correctamente.");
            closeWindow();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al actualizar el cliente.");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow();
    }

    private boolean validateFields() {
        return nameField.getText() != null && !nameField.getText().isEmpty() &&
                contactoField.getText() != null && !contactoField.getText().isEmpty() &&
                telefonoField.getText() != null && !telefonoField.getText().isEmpty() &&
                identificacionField.getText() != null && !identificacionField.getText().isEmpty() &&
                emailField.getText() != null && !emailField.getText().isEmpty() &&
                zonaField.getText() != null && !zonaField.getText().isEmpty() &&
                direccionField.getText() != null && !direccionField.getText().isEmpty();
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