package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.DeleteProduct;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PostProduct;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Request.PutProduct;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Response.GetProduct;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Request.*;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplier;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplierDetail;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplierOrder;
import com.wirehec.front_wirehec.Constants.TokenConstants;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import com.wirehec.front_wirehec.DTO.SupplierDetailDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;
import com.wirehec.front_wirehec.Utils.TokenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductSupplierController {

    @FXML private VBox menuVBox;
    @FXML private Button userDropdown;
    @FXML private VBox userMenu;
    @FXML private GridPane contentPane;
    @FXML private Label menuLabel;
    @FXML private Button hamburgerButton;
    @FXML private Label userRoleLabel;

    @FXML private Button inicioButton;
    @FXML private Button productosButton;
    @FXML private Button contabilidadButton;
    @FXML private Button facturasButton;
    @FXML private Button empleadosButton;
    @FXML private Button clientesButton;
    @FXML private Button ajustesButton;

    @FXML private TableView<ProductDTO> productTable;
    @FXML private TableColumn<ProductDTO, Long> idProductColumn;
    @FXML private TableColumn<ProductDTO, String> nombreProductColumn;
    @FXML private TableColumn<ProductDTO, String> categoriaProductColumn;
    @FXML private TableColumn<ProductDTO, BigDecimal> precioVentaProductColumn;
    @FXML private TableColumn<ProductDTO, Integer> stockProductColumn;
    @FXML private TableColumn<ProductDTO, BigDecimal> precioCosteProductColumn;

    @FXML private TableView<SupplierDTO> supplierTable;
    @FXML private TableColumn<SupplierDTO, Long> idSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> nombreSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> cifSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> emailSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> categoriaSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> productoSupplierColumn;

    @FXML private TableView<SupplierDetailDTO> supplierDetailTable;
    @FXML private TableColumn<SupplierDetailDTO, Long> idSupplierDetailColumn;
    @FXML private TableColumn<SupplierDetailDTO, String> supplierColumn;
    @FXML private TableColumn<SupplierDetailDTO, String> ordersColumn;

    @FXML private PieChart productDistributionChart;
    @FXML private BarChart<String, Number> supplierOrdersChart;

    private boolean isUserMenuVisible = false;
    private boolean isMenuExpanded = false;

    @FXML
    public void initialize() {
        setButtonIcon(inicioButton, "fas-home");
        setButtonIcon(productosButton, "fas-box-open");
        setButtonIcon(contabilidadButton, "fas-chart-line");
        setButtonIcon(facturasButton, "fas-file-invoice");
        setButtonIcon(empleadosButton, "fas-user-tie");
        setButtonIcon(clientesButton, "fas-users");
        setButtonIcon(ajustesButton, "fas-cogs");
        setButtonIcon(hamburgerButton, "fas-th");

        if (isMenuExpanded) {
            menuVBox.getStyleClass().add("expanded");
            menuLabel.setVisible(true);
            inicioButton.setText("Inicio");
            productosButton.setText("Productos");
            contabilidadButton.setText("Contabilidad");
            facturasButton.setText("Facturas");
            empleadosButton.setText("Empleados");
            clientesButton.setText("Clientes");
            ajustesButton.setText("Ajustes");
        } else {
            menuVBox.getStyleClass().remove("expanded");
            menuLabel.setVisible(false);
            inicioButton.setText(null);
            productosButton.setText(null);
            contabilidadButton.setText(null);
            facturasButton.setText(null);
            empleadosButton.setText(null);
            clientesButton.setText(null);
            ajustesButton.setText(null);
        }

        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreProductColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        categoriaProductColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaProducto"));
        precioVentaProductColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        stockProductColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        precioCosteProductColumn.setCellValueFactory(new PropertyValueFactory<>("precioCoste"));

        idSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        nombreSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        cifSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("cifProveedor"));
        emailSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("emailProveedor"));
        categoriaSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaProveedor"));
        productoSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("productoProveedor"));

        idSupplierDetailColumn.setCellValueFactory(new PropertyValueFactory<>("idDetalleProveedor"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        ordersColumn.setCellValueFactory(new PropertyValueFactory<>("supplierOrders"));


        idSupplierDetailColumn.setCellValueFactory(new PropertyValueFactory<>("idDetalleProveedor"));
        supplierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplier().getNombreProveedor()));
        ordersColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplierOrders().toString()));

        configurarTablaDetalles();
        cargarDatos();
        String token = TokenConstants.TOKEN;
        if (token != null && !token.isEmpty()) {
            String userName = TokenUtils.getUserNameFromToken(token);
            String userRole = TokenUtils.getUserRoleFromToken(token);
            userDropdown.setText(userName);
            userRoleLabel.setText(userRole);
        }
    }

    private void setButtonIcon(Button button, String iconLiteral) {
        if (button == null) {
            System.err.println("El botón es null: " + iconLiteral);
            return;
        }
        FontIcon icon = new FontIcon(iconLiteral);
        icon.setIconSize(18);
        icon.setIconColor(javafx.scene.paint.Color.WHITE);
        button.setGraphic(icon);
    }
    private void configurarTablaDetalles() {
        // Configurar columna para el ID del detalle del proveedor
        idSupplierDetailColumn = new TableColumn<>("ID Detalle");
        idSupplierDetailColumn.setCellValueFactory(new PropertyValueFactory<>("idDetalleProveedor"));

        // Configurar columna para el nombre del proveedor
        supplierColumn = new TableColumn<>("Proveedor");
        supplierColumn.setCellValueFactory(cellData -> {
            SupplierDTO supplier = cellData.getValue().getSupplier();
            return new SimpleStringProperty(supplier != null ? supplier.getNombreProveedor() : "N/A");
        });

        // Configurar columna para los pedidos
        ordersColumn = new TableColumn<>("Pedidos");
        ordersColumn.setCellValueFactory(cellData -> {
            List<SupplierOrderDTO> orders = cellData.getValue().getSupplierOrders();
            return new SimpleStringProperty(orders != null ? orders.stream()
                    .map(order -> "ID: " + order.getIdPedidoProveedor())
                    .collect(Collectors.joining(", ")) : "Sin pedidos");
        });

        // Añadir las columnas a la tabla
        supplierDetailTable.getColumns().setAll(idSupplierDetailColumn, supplierColumn, ordersColumn);

        configurarGraficoPedidos();
    }
    private void cargarDatos() {
        try {
            // Obtener los datos de productos
            GetProduct getProduct = new GetProduct();
            List<ProductDTO> productList = getProduct.sendGetProductRequest();
            productTable.setItems(FXCollections.observableArrayList(productList));

            // Obtener los datos de proveedores
            GetSupplier getSupplier = new GetSupplier();
            List<SupplierDTO> supplierList = getSupplier.sendGetSupplierRequest();
            supplierTable.setItems(FXCollections.observableArrayList(supplierList));

            // Obtener los datos de detalles de proveedores
            GetSupplierDetail getSupplierDetail = new GetSupplierDetail();
            List<SupplierDetailDTO> supplierDetailList = getSupplierDetail.sendGetSupplierDetailRequest();

            // Validar si la lista está vacía
            if (supplierDetailList == null || supplierDetailList.isEmpty()) {
                System.out.println("No se encontraron detalles de proveedores.");
                return;
            }

            // Cargar los datos en la tabla
            supplierDetailTable.setItems(FXCollections.observableArrayList(supplierDetailList));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error al cargar datos", "No se pudieron cargar los datos correctamente.");
        }
        try {
            List<SupplierDetailDTO> supplierDetails = new GetSupplierDetail().sendGetSupplierDetailRequest();
            supplierDetailTable.setItems(FXCollections.observableArrayList(supplierDetails));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los datos.");
        }
    }

    @FXML
    private void toggleMenu() {
        isMenuExpanded = !isMenuExpanded;

        double fromWidth = isMenuExpanded ? 50 : 220;
        double toWidth = isMenuExpanded ? 220 : 50;

        animateMenuWidth(fromWidth, toWidth);

        if (isMenuExpanded) {
            if (!menuVBox.getStyleClass().contains("expanded")) {
                menuVBox.getStyleClass().add("expanded");
            }
            menuLabel.setVisible(true);

            // Restaurar texto de los botones
            inicioButton.setText("Inicio");
            productosButton.setText("Productos");
            contabilidadButton.setText("Contabilidad");
            facturasButton.setText("Facturas");
            empleadosButton.setText("Empleados");
            clientesButton.setText("Clientes");
            ajustesButton.setText("Ajustes");
        } else {
            menuVBox.getStyleClass().remove("expanded");
            menuLabel.setVisible(false);

            // Eliminar texto de los botones
            inicioButton.setText("");
            productosButton.setText("");
            contabilidadButton.setText("");
            facturasButton.setText("");
            empleadosButton.setText("");
            clientesButton.setText("");
            ajustesButton.setText("");
        }
    }

    private void animateMenuWidth(double from, double to) {
        Transition transition = new Transition() {
            {
                setCycleDuration(Duration.millis(200));
            }

            @Override
            protected void interpolate(double frac) {
                double width = from + (to - from) * frac;
                menuVBox.setPrefWidth(width);
            }
        };
        transition.play();
    }

    @FXML
    private void toggleUserMenu() {
        isUserMenuVisible = !isUserMenuVisible;
        FadeTransition ft = new FadeTransition(Duration.millis(300), userMenu);
        ft.setFromValue(isUserMenuVisible ? 0.0 : 1.0);
        ft.setToValue(isUserMenuVisible ? 1.0 : 0.0);
        ft.play();
        userMenu.setVisible(isUserMenuVisible);
    }

    @FXML
    private void handleUserAction(ActionEvent event) {
        Button source = (Button) event.getSource();
        String action = source.getText();
        switch (action) {
            case "Cambiar Contraseña":
                System.out.println("Redirigir a la página de cambio de contraseña");
                break;
            case "Mi Perfil":
                System.out.println("Redirigir a la página de perfil");
                break;
            case "Cerrar Sesión":
                TokenConstants.TOKEN = null;

                // Navegar a la vista de login
                navigateToLogin(null);
                break;
            default:
                System.out.println("Acción no reconocida: " + action);
        }
    }
    @FXML
    private void handleAddProduct() {
        openModal("/com/wirehec/front_wirehec/Views/Product-SupplierViews/AddProduct-view.fxml", "Añadir Producto");
    }
    @FXML
    private void handleUpdateProduct() {
        ProductDTO selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Producto", "Por favor, selecciona un producto para actualizar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/Product-SupplierViews/UpdateProduct-view.fxml"));
            Parent root = loader.load();

            UpdateProductController controller = loader.getController();
            controller.setProductData(selectedProduct);

            Stage stage = new Stage();
            stage.setTitle("Actualizar Producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDeleteProduct() {
        ProductDTO selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Producto", "Por favor, selecciona un producto para eliminar.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar este producto?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirmar Eliminación");
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            new DeleteProduct().sendDeleteProductRequest(selectedProduct);
            cargarDatos();
        }
    }

    @FXML
    private void handleViewProduct() {
        ProductDTO selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Producto", "Por favor, selecciona un producto para ver los detalles.");
            return;
        }

        String details = String.format(
                "ID: %d\nNombre: %s\nCategoría: %s\nPrecio Venta: %.2f\nPrecio Compra: %.2f\nStock: %d",
                selectedProduct.getId(),
                selectedProduct.getNombreProducto(),
                selectedProduct.getCategoriaProducto(),
                selectedProduct.getPrecioVenta(),
                selectedProduct.getPrecioCoste(),
                selectedProduct.getStock()
        );

        showAlert(Alert.AlertType.INFORMATION, "Detalles del Producto", details);
    }
    @FXML
    private void handleAddSupplier() {
        openModal("/com/wirehec/front_wirehec/Views/Product-SupplierViews/AddSupplier-view.fxml", "Añadir Proveedor");
    }

    @FXML
    private void handleUpdateSupplier() {
        SupplierDTO selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Proveedor", "Por favor, selecciona un proveedor para actualizar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/Product-SupplierViews/UpdateSupplier-view.fxml"));
            Parent root = loader.load();

            UpdateSupplierController controller = loader.getController();
            controller.setSupplierData(selectedSupplier);

            Stage stage = new Stage();
            stage.setTitle("Actualizar Proveedor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDeleteSupplier() {
        SupplierDTO selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Proveedor", "Por favor, selecciona un proveedor para eliminar.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar este proveedor?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirmar Eliminación");
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            new DeleteSupplier().sendDeleteSupplierRequest(selectedSupplier);
            cargarDatos();
        }
    }

    @FXML
    private void handleViewSupplier() {
        SupplierDTO selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Proveedor", "Por favor, selecciona un proveedor para ver los detalles.");
            return;
        }

        String details = String.format(
                "ID: %d\nNombre: %s\nCIF: %s\nEmail: %s\nCategoría: %s\nProducto: %s",
                selectedSupplier.getIdProveedor(),
                selectedSupplier.getNombreProveedor(),
                selectedSupplier.getCifProveedor(),
                selectedSupplier.getEmailProveedor(),
                selectedSupplier.getCategoriaProveedor(),
                selectedSupplier.getProductoProveedor()
        );

        showAlert(Alert.AlertType.INFORMATION, "Detalles del Proveedor", details);
    }
    @FXML
    private void handleAddSupplierDetail() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/Product-SupplierViews/AddSupplierDetail-view.fxml"));
            Parent root = loader.load();

            AddSupplierDetailController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Añadir Detalle de Proveedor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Eliminar la llamada redundante a sendPostSupplierDetailRequest
            SupplierDetailDTO newDetail = controller.getSupplierDetail();
            if (newDetail != null) {
                cargarDatos();
                configurarGraficoPedidos();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo abrir el formulario para añadir detalles.");
        }
    }

    @FXML
    private void handleUpdateSupplierDetail() {
        SupplierDetailDTO selectedDetail = supplierDetailTable.getSelectionModel().getSelectedItem();
        if (selectedDetail == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Detalle", "Por favor, selecciona un detalle para actualizar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/Product-SupplierViews/UpdateSupplierDetail-view.fxml"));
            Parent root = loader.load();

            UpdateSupplierDetailController controller = loader.getController();
            controller.setSupplierDetailData(selectedDetail);

            Stage stage = new Stage();
            stage.setTitle("Actualizar Detalle de Proveedor");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteSupplierDetail() {
        SupplierDetailDTO selectedDetail = supplierDetailTable.getSelectionModel().getSelectedItem();
        if (selectedDetail == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Detalle", "Por favor, selecciona un detalle para eliminar.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar este detalle?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirmar Eliminación");
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            new DeleteSupplierDetail().sendDeleteSupplierDetailRequest(selectedDetail);
            cargarDatos();
        }
    }

    @FXML
    private void handleViewSupplierDetail() {
        SupplierDetailDTO selectedDetail = supplierDetailTable.getSelectionModel().getSelectedItem();
        if (selectedDetail == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Detalle", "Por favor, selecciona un detalle para ver los detalles.");
            return;
        }

        String ordersDetails = selectedDetail.getSupplierOrders().stream()
                .map(order -> String.format("ID Pedido: %d, Fecha Pedido: %s, Fecha Entrega: %s",
                        order.getIdPedidoProveedor(),
                        order.getFechaPedido(),
                        order.getFechaEntrega()))
                .collect(Collectors.joining("\n"));

        String details = String.format(
                "ID Detalle: %d\nProveedor: %s\nPedidos:\n%s",
                selectedDetail.getIdDetalleProveedor(),
                selectedDetail.getSupplier().getNombreProveedor(),
                ordersDetails.isEmpty() ? "Sin pedidos" : ordersDetails
        );

        showAlert(Alert.AlertType.INFORMATION, "Detalles del Detalle de Proveedor", details);
    }

    private void openModal(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configurarGraficoPedidos() {
        GetSupplierOrder getSupplierOrder = new GetSupplierOrder();
        List<SupplierOrderDTO> orders = getSupplierOrder.sendGetSupplierOrderRequest();

        if (orders == null || orders.isEmpty()) {
            System.out.println("No se encontraron pedidos de proveedores.");
            return;
        }

        Map<String, Long> pedidosPorFecha = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getFechaPedido().toString(),
                        Collectors.counting()
                ));

        supplierOrdersChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Pedidos por Fecha");

        pedidosPorFecha.forEach((fecha, cantidad) -> {
            series.getData().add(new XYChart.Data<>(fecha, cantidad));
        });

        supplierOrdersChart.getData().add(series);
        CategoryAxis xAxis = (CategoryAxis) supplierOrdersChart.getXAxis();
        xAxis.setAutoRanging(true);

        NumberAxis yAxis = (NumberAxis) supplierOrdersChart.getYAxis();
        yAxis.setAutoRanging(true);
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void navigateToInicio(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/MainViews/hello-view.fxml");
    }

    public void navigateToProductos(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/Product-SupplierViews/ProductSupplier-View.fxml");
    }

    public void navigateToContabilidad(ActionEvent event) {
        String userRole = TokenUtils.getUserRoleFromToken(TokenConstants.TOKEN);
        if (!"ROLE_BOSS".equals(userRole)) {
            showAlert(Alert.AlertType.ERROR, "Acceso Denegado", "No tienes permiso para acceder a esta vista.");
            return;
        }
        changeScene("/com/wirehec/front_wirehec/Views/ContabilityViews/Contability-view.fxml");
    }

    @FXML
    public void navigateToFacturas(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/BillViews/Bill-view.fxml");
    }

    @FXML
    public void navigateToEmpleados(ActionEvent event) {
        String userRole = TokenUtils.getUserRoleFromToken(TokenConstants.TOKEN);
        if (!"ROLE_BOSS".equals(userRole)) {
            showAlert(Alert.AlertType.ERROR, "Acceso Denegado", "No tienes permiso para acceder a esta vista.");
            return;
        }
        changeScene("/com/wirehec/front_wirehec/Views/EmployeeViews/Employee-view.fxml");
    }

    public void navigateToAjustes(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/SettingViews/Setting-View.fxml");
    }

    public void navigateToLogin(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/AuthViews/Login-view.fxml");
    }
    public void navigateToCustomer(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/CustomerViews/Customer-view.fxml");
    }
    private void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Obtener el Stage actual y reemplazar la escena
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + fxmlPath);
        }
    }
}