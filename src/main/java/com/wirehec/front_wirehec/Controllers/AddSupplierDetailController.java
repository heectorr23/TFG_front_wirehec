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
            System.out.println("saveButton: " + saveButton); // Verificar si el botón se inicializa
            if (saveButton == null) {
                System.err.println("El botón saveButton no está inicializado. Verifica el archivo FXML.");
            }
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
        if (isSaveInProgress) {
            return; // Evitar múltiples ejecuciones
        }
        isSaveInProgress = true; // Marcar que la operación está en progreso
        saveButton.setDisable(true); // Deshabilitar el botón "Guardar"

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isSaveInProgress = false; // Restablecer la bandera
            saveButton.setDisable(false); // Habilitar el botón "Guardar"

            // Cerrar la ventana
            Stage stage = (Stage) supplierComboBox.getScene().getWindow();
            stage.close();
        }
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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}