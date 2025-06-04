package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request.PostSupplierDetail;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplier;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplierOrder;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class AddSupplierDetailController {

    @FXML
    private ComboBox<SupplierDTO> supplierComboBox;
    @FXML
    private TextField idProveedorField;
    @FXML
    private TextField nombreProveedorField;
    @FXML
    private TextField cifProveedorField;
    @FXML
    private TextField emailProveedorField;
    @FXML
    private TextField categoriaProveedorField;
    @FXML
    private TextField productoProveedorField;
    @FXML
    private TextField idPedidoField;
    @FXML
    private DatePicker fechaPedidoField;
    @FXML
    private DatePicker fechaEntregaField;
    @FXML
    private Button saveButton;

    private SupplierDetailDTO supplierDetail;
    private boolean isSaveInProgress = false; // Bandera para evitar múltiples ejecuciones

    public void initialize() {
        GetSupplier getSupplier = new GetSupplier();
        List<SupplierDTO> suppliers = getSupplier.sendGetSupplierRequest();
        if (suppliers != null && !suppliers.isEmpty()) {
            supplierComboBox.setItems(FXCollections.observableArrayList(suppliers));
        } else {
            showAlert(Alert.AlertType.WARNING, "Advertencia", "No se encontraron proveedores.");
        }

        // Configurar el CellFactory para mostrar solo el nombre, número y producto
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

        // Mostrar el mismo formato en el ComboBox cuando se selecciona un elemento
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

        supplierComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idProveedorField.setText(String.valueOf(newValue.getIdProveedor()));
                nombreProveedorField.setText(newValue.getNombreProveedor());
                cifProveedorField.setText(String.valueOf(newValue.getCifProveedor()));
                emailProveedorField.setText(newValue.getEmailProveedor());
                categoriaProveedorField.setText(newValue.getCategoriaProveedor());
                productoProveedorField.setText(newValue.getProductoProveedor());
            } else {
                idProveedorField.clear();
                nombreProveedorField.clear();
                cifProveedorField.clear();
                emailProveedorField.clear();
                categoriaProveedorField.clear();
                productoProveedorField.clear();
            }
        });

        // Obtener el último ID de pedido y asignar el próximo ID
        Long lastOrderId = getLastOrderIdFromDatabase();
        idPedidoField.setText(String.valueOf(lastOrderId + 1));

        // Configurar fechas predeterminadas
        LocalDate currentDate = LocalDate.now();
        fechaPedidoField.setValue(currentDate);
        fechaEntregaField.setValue(currentDate.plusDays(7));
    }

    private Long getLastOrderIdFromDatabase() {
        GetSupplierOrder getSupplierOrder = new GetSupplierOrder();
        List<SupplierOrderDTO> orders = getSupplierOrder.sendGetSupplierOrderRequest();

        if (orders == null || orders.isEmpty()) {
            return 0L;
        }

        return orders.stream()
                .mapToLong(SupplierOrderDTO::getIdPedidoProveedor)
                .max()
                .orElse(0L);
    }

    @FXML
    private void handleSave() {
        try {
            // Validar que los campos no estén vacíos
            if (supplierComboBox.getValue() == null || idProveedorField.getText().isEmpty() ||
                    nombreProveedorField.getText().isEmpty() || cifProveedorField.getText().isEmpty() ||
                    emailProveedorField.getText().isEmpty() || categoriaProveedorField.getText().isEmpty() ||
                    productoProveedorField.getText().isEmpty() || fechaPedidoField.getValue() == null ||
                    fechaEntregaField.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
                return;
            }

            // Crear el detalle del proveedor
            supplierDetail = new SupplierDetailDTO();
            supplierDetail.setSupplier(supplierComboBox.getValue());

            // Crear un nuevo pedido asociado al detalle
            SupplierOrderDTO order = new SupplierOrderDTO();
            order.setFechaPedido(fechaPedidoField.getValue());
            order.setFechaEntrega(fechaEntregaField.getValue());

            // Asociar el pedido al detalle
            supplierDetail.setSupplierOrders(List.of(order));

            // Enviar el detalle con el pedido a la base de datos
            new PostSupplierDetail().sendPostSupplierDetailRequest(supplierDetail);

            closeWindow();
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
        supplierDetail = null;
        Stage stage = (Stage) supplierComboBox.getScene().getWindow();
        stage.close();
    }

    public void setSupplierData(SupplierDetailDTO supplierDetail) {
        if (supplierDetail != null) {
            supplierComboBox.setValue(supplierDetail.getSupplier());
            idProveedorField.setText(String.valueOf(supplierDetail.getSupplier().getIdProveedor()));
            nombreProveedorField.setText(supplierDetail.getSupplier().getNombreProveedor());
            cifProveedorField.setText(String.valueOf(supplierDetail.getSupplier().getCifProveedor()));
            emailProveedorField.setText(supplierDetail.getSupplier().getEmailProveedor());
            categoriaProveedorField.setText(supplierDetail.getSupplier().getCategoriaProveedor());
            productoProveedorField.setText(supplierDetail.getSupplier().getProductoProveedor());
            idPedidoField.setText(String.valueOf(supplierDetail.getSupplierOrders().get(0).getIdPedidoProveedor()));
        } else {
            supplierComboBox.setValue(null);
            idProveedorField.clear();
            nombreProveedorField.clear();
            cifProveedorField.clear();
            emailProveedorField.clear();
            categoriaProveedorField.clear();
            productoProveedorField.clear();
            idPedidoField.clear();
        }
    }

    public SupplierDetailDTO getSupplierDetail() {
        return supplierDetail;
    }
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) supplierComboBox.getScene().getWindow();
        stage.close();
    }
}