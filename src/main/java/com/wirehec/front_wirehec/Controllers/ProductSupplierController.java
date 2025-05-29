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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
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
        setButtonIcon(inicioButton, "fas-home");
        setButtonIcon(productosButton, "fas-box-open");
        setButtonIcon(contabilidadButton, "fas-chart-line");
        setButtonIcon(facturasButton, "fas-file-invoice");
        setButtonIcon(empleadosButton, "fas-user-tie");
        setButtonIcon(ajustesButton, "fas-cogs");
        setButtonIcon(hamburgerButton, "fas-th");

        idProductColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreProductColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        categoriaProductColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaProducto"));
        precioVentaProductColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        idSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        nombreSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        cifSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("cifProveedor"));
        emailSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("emailProveedor"));
        categoriaSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("categoriaProveedor"));
        productoSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("productoProveedor"));

        cargarDatos();
        String token = TokenConstants.TOKEN;
        if (token != null && !token.isEmpty()) {
            String userName = TokenUtils.getUserNameFromToken(token);
            String userRole = TokenUtils.getUserRoleFromToken(token);
            userDropdown.setText(userName);
            userRoleLabel.setText(userRole);
        }
    }

    private void cargarDatos() {
        GetProduct getProduct = new GetProduct();
        List<ProductDTO> productList = getProduct.sendGetProductRequest();
        productTable.setItems(FXCollections.observableArrayList(productList));

        GetSupplier getSupplier = new GetSupplier();
        List<SupplierDTO> supplierList = getSupplier.sendGetSupplierRequest();
        supplierTable.setItems(FXCollections.observableArrayList(supplierList));

        productDistributionChart.setData(FXCollections.observableArrayList(
                productList.stream()
                        .collect(Collectors.groupingBy(ProductDTO::getCategoriaProducto, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .toList()
        ));
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
                // Restablecer el token
                TokenConstants.TOKEN = null;

                // Navegar a la vista de login
                navigateToLogin(null);
                break;
        }
        toggleUserMenu();
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

        showAlert(Alert.AlertType.INFORMATION, "Detalles del Producto", selectedProduct.toString());
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

        showAlert(Alert.AlertType.INFORMATION, "Detalles del Proveedor", selectedSupplier.toString());
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
    private void setButtonIcon(Button button, String iconLiteral) {
        FontIcon icon = new FontIcon(iconLiteral);
        icon.setIconSize(18);
        icon.setIconColor(javafx.scene.paint.Color.WHITE);
        button.setGraphic(icon);
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