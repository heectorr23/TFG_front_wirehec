package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Response.GetCustomer;
import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Response.GetProduct;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplierOrder;
import com.wirehec.front_wirehec.DTO.CustomerDTO;
import com.wirehec.front_wirehec.DTO.FacturaDTO;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import com.wirehec.front_wirehec.DTO.SupplierOrderDTO;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class MainController {

    @FXML private VBox menuVBox;
    @FXML private Button userDropdown;
    @FXML private VBox userMenu;
    @FXML private GridPane contentPane;
    @FXML private Label menuLabel;
    @FXML private Button hamburgerButton;

    @FXML private Button inicioButton;
    @FXML private Button productosButton;
    @FXML private Button proveedoresButton;
    @FXML private Button contabilidadButton;
    @FXML private Button facturasButton;
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
        System.out.println("inicioButton: " + inicioButton);
        System.out.println("productosButton: " + productosButton);
        System.out.println("proveedoresButton: " + proveedoresButton);
        System.out.println("contabilidadButton: " + contabilidadButton);
        System.out.println("facturasButton: " + facturasButton);
        System.out.println("ajustesButton: " + ajustesButton);

        setButtonIcon(inicioButton, "fas-home");
        setButtonIcon(productosButton, "fas-box-open");
        setButtonIcon(proveedoresButton, "fas-truck");
        setButtonIcon(contabilidadButton, "fas-chart-line");
        setButtonIcon(facturasButton, "fas-file-invoice");
        setButtonIcon(ajustesButton, "fas-cogs");
        setButtonIcon(hamburgerButton, "fas-th");

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

        // Cargar datos
        cargarDatos();
    }

    private void cargarDatos() {
        GetBIll getBIll = new GetBIll();
        List<FacturaDTO> billList = getBIll.sendGetBillRequest();
        billtable.setItems(FXCollections.observableArrayList(billList));

        GetSupplierOrder getSupplierOrder = new GetSupplierOrder();
        List<SupplierOrderDTO> orderList = getSupplierOrder.sendGetSupplierOrderRequest();
        supplierordertable.setItems(FXCollections.observableArrayList(orderList));

        GetProduct getProduct = new GetProduct();
        List<ProductDTO> productList = getProduct.sendGetProductRequest();
        productTable.setItems(FXCollections.observableArrayList(productList));

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
        } else {
            menuVBox.getStyleClass().remove("expanded");
            menuLabel.setVisible(false);
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
                System.out.println("Logout and close session");
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
        changeScene("/com/wirehec/front_wirehec/Views/ContabilityViews/Contability-view.fxml");
    }
    public void navigateToFacturas(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/BillViews/Bill-view.fxml");
    }
    public void navigateToAjustes(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/SettingViews/Setting-View.fxml");
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