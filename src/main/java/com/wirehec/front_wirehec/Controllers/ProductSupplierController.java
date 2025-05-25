package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.ProductAPI.HTTP.Response.GetProduct;
import com.wirehec.front_wirehec.APIs.SupplierAPI.HTTP.Response.GetSupplier;
import com.wirehec.front_wirehec.DTO.ProductDTO;
import com.wirehec.front_wirehec.DTO.SupplierDTO;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductSupplierController {

    @FXML private VBox menuVBox;
    @FXML private Button userDropdown;
    @FXML private VBox userMenu;
    @FXML private GridPane contentPane;
    @FXML private Label menuLabel;
    @FXML private Button hamburgerButton;

    @FXML private Button inicioButton;
    @FXML private Button productosButton;
    @FXML private Button contabilidadButton;
    @FXML private Button facturasButton;
    @FXML private Button empleadosButton;
    @FXML private Button ajustesButton;

    @FXML private TableView<ProductDTO> productTable;
    @FXML private TableColumn<ProductDTO, Long> idProductColumn;
    @FXML private TableColumn<ProductDTO, String> nombreProductColumn;
    @FXML private TableColumn<ProductDTO, String> categoriaProductColumn;
    @FXML private TableColumn<ProductDTO, BigDecimal> precioVentaProductColumn;

    @FXML private TableView<SupplierDTO> supplierTable;
    @FXML private TableColumn<SupplierDTO, Long> idSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> nombreSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> cifSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> emailSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> categoriaSupplierColumn;
    @FXML private TableColumn<SupplierDTO, String> productoSupplierColumn;

    @FXML private PieChart productDistributionChart;
    @FXML private BarChart<String, Number> supplierOrdersChart;

    private boolean isUserMenuVisible = false;
    private boolean isMenuExpanded = false;

    @FXML
    public void initialize() {
        System.out.println("inicioButton: " + inicioButton);
        System.out.println("productosButton: " + productosButton);
        System.out.println("contabilidadButton: " + contabilidadButton);
        System.out.println("facturasButton: " + facturasButton);
        System.out.println("ajustesButton: " + ajustesButton);

        setButtonIcon(inicioButton, "fas-home");
        setButtonIcon(productosButton, "fas-box-open");
        setButtonIcon(contabilidadButton, "fas-chart-line");
        setButtonIcon(facturasButton, "fas-file-invoice");
        setButtonIcon(empleadosButton, "fas-user-tie");
        setButtonIcon(ajustesButton, "fas-cogs");
        setButtonIcon(hamburgerButton, "fas-th");

        // Configurar columnas de la tabla de productos
        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreProductColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        categoriaProductColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaProducto"));
        precioVentaProductColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        // Configurar columnas de la tabla de proveedores
        idSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        nombreSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        cifSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("cifProveedor"));
        emailSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("emailProveedor"));
        categoriaSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaProveedor"));
        productoSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("productoProveedor"));

        // Cargar datos
        cargarDatos();
    }

    private void cargarDatos() {
        // Obtener datos de productos desde la API
        GetProduct getProduct = new GetProduct();
        List<ProductDTO> productList = getProduct.sendGetProductRequest();
        productTable.setItems(FXCollections.observableArrayList(productList));

        // Obtener datos de proveedores desde la API
        GetSupplier getSupplier = new GetSupplier();
        List<SupplierDTO> supplierList = getSupplier.sendGetSupplierRequest();
        supplierTable.setItems(FXCollections.observableArrayList(supplierList));

        // Configurar gráfico de distribución de productos
        productDistributionChart.setData(FXCollections.observableArrayList(
                productList.stream()
                        .collect(Collectors.groupingBy(ProductDTO::getCategoriaProducto, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .toList()
        ));

        // Configurar gráfico de pedidos por proveedor (simulación)
        supplierOrdersChart.getData().addAll(
                new BarChart.Series<>("Pedidos", FXCollections.observableArrayList(
                        new BarChart.Data<>("Proveedor A", 5),
                        new BarChart.Data<>("Proveedor B", 8)
                ))
        );
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

    @FXML
    public void navigateToProductos(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/Product-SupplierViews/ProductSupplier-View.fxml");
    }

    @FXML
    public void navigateToContabilidad(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/ContabilityViews/Contability-view.fxml");
    }

    @FXML
    public void navigateToFacturas(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/BillViews/Bill-view.fxml");
    }
    @FXML
    public void navigateToEmpleados(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/EmployeeViews/Employee-view.fxml");
    }
    @FXML
    public void navigateToAjustes(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/SettingViews/Setting-View.fxml");
    }

    private void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Obtener el Stage actual y reemplazar la escena
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}