package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Response.GetCustomer;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Response.GetProduct;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplierOrder;
import com.wirehec.front_wirehec.Constants.TokenConstants;
import com.wirehec.front_wirehec.DTO.CustomerDTO;
import com.wirehec.front_wirehec.DTO.FacturaDTO;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;
import com.wirehec.front_wirehec.Utils.TokenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainController {

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

    @FXML private TableView<FacturaDTO> billtable;
    @FXML private TableColumn<FacturaDTO, Long> idColumn;
    @FXML private TableColumn<FacturaDTO, BigDecimal> priceColumn;
    @FXML private TableColumn<FacturaDTO, String> zoneColumn;
    @FXML private TableColumn<FacturaDTO, String> directionColumn;

    @FXML private TableView<SupplierOrderDTO> supplierordertable;
    @FXML private TableColumn<SupplierOrderDTO, Long> idPedidoProveedorColumn;
    @FXML private TableColumn<SupplierOrderDTO, Date> fechaPedidoColumn;
    @FXML private TableColumn<SupplierOrderDTO, Date> fechaEntregaColumn;

    @FXML private TableView<ProductDTO> productTable;
    @FXML private TableColumn<ProductDTO, Long> idProductColumn;
    @FXML private TableColumn<ProductDTO, String> nombreProductColumn;
    @FXML private TableColumn<ProductDTO, String> categoriaProductColumn;
    @FXML private TableColumn<ProductDTO, BigDecimal> precioVentaProductColumn;
    @FXML private TableColumn<ProductDTO, Integer> stockProductColumn;
    @FXML private TableColumn<ProductDTO, BigDecimal> precioCosteProductColumn;

    @FXML private TableView<CustomerDTO> clientTable;
    @FXML private TableColumn<CustomerDTO, String> nombreClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> contactoClienteColumn;
    @FXML private TableColumn<CustomerDTO, Integer> telefonoClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> identificacionClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> emailClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> zonaClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> direccionClienteColumn;

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

        // Configurar columnas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("zona"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        idPedidoProveedorColumn.setCellValueFactory(new PropertyValueFactory<>("idPedidoProveedor"));
        fechaPedidoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));
        fechaEntregaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));

        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreProductColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        categoriaProductColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaProducto"));
        precioVentaProductColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        stockProductColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        precioCosteProductColumn.setCellValueFactory(new PropertyValueFactory<>("precioCoste"));

        nombreClienteColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactoClienteColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        telefonoClienteColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        identificacionClienteColumn.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        emailClienteColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        zonaClienteColumn.setCellValueFactory(new PropertyValueFactory<>("zona"));
        direccionClienteColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        ajustarColumnas();
        cargarDatos();


        String userName = TokenUtils.getUserNameFromToken(TokenConstants.TOKEN);
        userDropdown.setText(userName != null ? userName : "Invitado");


        String userRole = TokenUtils.getUserRoleFromToken(TokenConstants.TOKEN);
        userRoleLabel.setText(userRole != null ? userRole : "Invitado");


    }

    private void ajustarColumnas() {
        ajustarAnchoColumnas(billtable);
        ajustarAnchoColumnas(supplierordertable);
        ajustarAnchoColumnas(productTable);
        ajustarAnchoColumnas(clientTable);
    }

    private void ajustarAnchoColumnas(TableView<?> tableView) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        for (TableColumn<?, ?> column : tableView.getColumns()) {
            column.setPrefWidth(USE_COMPUTED_SIZE);
        }
    }

    private void cargarDatos() {
        GetBIll getBIll = new GetBIll();
        List<FacturaDTO> billList = getBIll.sendGetBillRequest();
        billtable.setItems(FXCollections.observableArrayList(billList));

        GetSupplierOrder getSupplierOrder = new GetSupplierOrder();
        List<SupplierOrderDTO> orderList = getSupplierOrder.sendGetSupplierOrderRequest();

        List<SupplierOrderDTO> filteredOrders = orderList.stream()
                .filter(order -> order.getFechaEntrega().isAfter(LocalDate.now()))
                .toList();

        supplierordertable.setItems(FXCollections.observableArrayList(filteredOrders));

        GetProduct getProduct = new GetProduct();
        List<ProductDTO> productList = getProduct.sendGetProductRequest();

        List<ProductDTO> filteredProducts = productList.stream()
                .filter(product -> product.getStock() > 0)
                .toList();

        productTable.setItems(FXCollections.observableArrayList(filteredProducts));

        GetCustomer getCustomer = new GetCustomer();
        List<CustomerDTO> customerList = getCustomer.sendGetCustomerRequest();
        clientTable.setItems(FXCollections.observableArrayList(customerList));
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

            // Asegurarse de que solo se muestren los íconos
            inicioButton.setText(null);
            productosButton.setText(null);
            contabilidadButton.setText(null);
            facturasButton.setText(null);
            empleadosButton.setText(null);
            clientesButton.setText(null);
            ajustesButton.setText(null);
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
    private void handleUserAction() {
        Button source = (Button) userMenu.getScene().getFocusOwner();
        String action = source.getText();
        switch (action) {
            case "Cambiar Contraseña":
                System.out.println("Redirect to change password page or call microservice");
                break;
            case "Mi Perfil":
                System.out.println("Redirect to profile page or call microservice");
                break;
            case "Cerrar Sesión":
                // Restablecer el token
                TokenConstants.TOKEN = null;

                // Navegar a la vista de login
                navigateToLogin(null);
                break;
        }
        toggleUserMenu();
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
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Obtener el Stage actual y reemplazar la escena
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}