package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PostProduct;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
            String name = nameField.getText();
            String category = categoriaComboBox.getValue();
            BigDecimal price = new BigDecimal(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            BigDecimal costPrice = new BigDecimal(costPriceField.getText());

            ProductDTO product = new ProductDTO(null, name, category, price, stock, costPrice);
            new PostProduct().sendPostProductRequest(product);

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
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}