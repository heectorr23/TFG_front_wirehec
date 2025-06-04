package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PostProduct;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class AddProductController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoriaComboBox;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField costPriceField;

    @FXML
    public void initialize() {
        categoriaComboBox.setItems(FXCollections.observableArrayList("Cable", "Acero", "Cadena", "Varios", "Accesorios"));
    }

    @FXML
    private void handleSave() {
        try {
            // Validar que los campos no estén vacíos
            if (nameField.getText().isEmpty() || categoriaComboBox.getValue() == null ||
                    priceField.getText().isEmpty() || stockField.getText().isEmpty() ||
                    costPriceField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                return;
            }

            // Validar que los valores numéricos sean correctos
            BigDecimal price = new BigDecimal(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            BigDecimal costPrice = new BigDecimal(costPriceField.getText());

            // Crear el producto
            String name = nameField.getText();
            String category = categoriaComboBox.getValue();
            ProductDTO product = new ProductDTO(null, name, category, price, stock, costPrice);
            new PostProduct().sendPostProductRequest(product);

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Los campos de precio y stock deben ser valores numéricos.");
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
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}