package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request.PutSupplierDetail;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplier;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplierOrder;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

        GetSupplierOrder getSupplierOrder = new GetSupplierOrder();
        List<SupplierOrderDTO> orders = getSupplierOrder.sendGetSupplierOrderRequest();
        ordersListView.setItems(FXCollections.observableArrayList(orders));

        // Simulación de productos (debería obtenerse de un servicio)
        productComboBox.setItems(FXCollections.observableArrayList(101L, 102L, 103L));
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

    private void closeWindow() {
        Stage stage = (Stage) supplierComboBox.getScene().getWindow();
        stage.close();
    }
}