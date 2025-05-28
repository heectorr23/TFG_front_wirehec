package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PutProduct;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class UpdateProductController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoriaComboBox;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField costPriceField;

    private ProductDTO product;

    @FXML
    public void initialize() {
        categoriaComboBox.setItems(FXCollections.observableArrayList("Cable", "Acero", "Cadena", "Varios", "Accesorios"));
    }

    public void setProductData(ProductDTO product) {
        this.product = product;
        nameField.setText(product.getNombreProducto());
        categoriaComboBox.setValue(product.getCategoriaProducto());
        priceField.setText(product.getPrecioVenta().toString());
        stockField.setText(String.valueOf(product.getStock()));
        costPriceField.setText(product.getPrecioCoste().toString());
    }

    @FXML
    private void handleSave() {
        try {
            String name = nameField.getText();
            String category = categoriaComboBox.getValue();
            BigDecimal price = new BigDecimal(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            BigDecimal costPrice = new BigDecimal(costPriceField.getText());

            ProductDTO updatedProduct = new ProductDTO(null, name, category, price, stock, costPrice);
            new PutProduct().sendPutProductRequest(updatedProduct, product.getId());

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
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}