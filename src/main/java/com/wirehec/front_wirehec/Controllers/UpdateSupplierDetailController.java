package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Response.GetProduct;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request.PutSupplierDetail;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplier;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplierOrder;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class UpdateSupplierDetailController {

    @FXML private ComboBox<SupplierDTO> supplierComboBox;
    @FXML private ListView<SupplierOrderDTO> ordersListView;
    @FXML private ComboBox<Long> productComboBox;

    private SupplierDetailDTO supplierDetail;

    @FXML
    public void initialize() {
        GetSupplier getSupplier = new GetSupplier();
        List<SupplierDTO> suppliers = getSupplier.sendGetSupplierRequest();
        supplierComboBox.setItems(FXCollections.observableArrayList(suppliers));

        // Configurar el CellFactory para el ComboBox de SupplierDTO
        supplierComboBox.setCellFactory(param -> new ListCell<SupplierDTO>() {
            @Override
            protected void updateItem(SupplierDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombreProveedor() + " - " + item.getCifProveedor() + " - " + item.getProductoProveedor());
                }
            }
        });

        supplierComboBox.setButtonCell(new ListCell<SupplierDTO>() {
            @Override
            protected void updateItem(SupplierDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombreProveedor() + " - " + item.getCifProveedor() + " - " + item.getProductoProveedor());
                }
            }
        });

        GetSupplierOrder getSupplierOrder = new GetSupplierOrder();
        List<SupplierOrderDTO> orders = getSupplierOrder.sendGetSupplierOrderRequest();
        ordersListView.setItems(FXCollections.observableArrayList(orders));

        // Configurar el CellFactory para el ListView de SupplierOrderDTO
        ordersListView.setCellFactory(param -> new ListCell<SupplierOrderDTO>() {
            @Override
            protected void updateItem(SupplierOrderDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("ID: " + item.getIdPedidoProveedor() + " | Fecha Pedido: " + item.getFechaPedido() + " | Fecha Entrega: " + item.getFechaEntrega());
                }
            }
        });

        List<Long> productIds = getProductIdsFromDatabase();
        productComboBox.setItems(FXCollections.observableArrayList(productIds));
    }

    public void setSupplierDetailData(SupplierDetailDTO supplierDetail) {
        this.supplierDetail = supplierDetail;
        supplierComboBox.setValue(supplierDetail.getSupplier());
        ordersListView.getSelectionModel().selectAll();
        productComboBox.setValue(supplierDetail.getIdProduct());
    }

    @FXML
    private void handleUpdate() {
        supplierDetail.setSupplier(supplierComboBox.getValue());
        supplierDetail.setSupplierOrders(ordersListView.getSelectionModel().getSelectedItems());
        supplierDetail.setIdProduct(productComboBox.getValue());

        new PutSupplierDetail().sendPutSupplierDetailRequest(supplierDetail, supplierDetail.getIdDetalleProveedor());
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }
    private List<Long> getProductIdsFromDatabase() {
        GetProduct getProduct = new GetProduct();
        List<ProductDTO> products = getProduct.sendGetProductRequest();
        // Mapear los ProductDTO a sus IDs
        return products.stream()
                .map(ProductDTO::getId)
                .toList();
    }
    private void closeWindow() {
        Stage stage = (Stage) supplierComboBox.getScene().getWindow();
        stage.close();
    }
}