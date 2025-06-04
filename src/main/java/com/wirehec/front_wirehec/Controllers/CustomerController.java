package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Request.DeleteCustomer;
import com.wirehec.front_wirehec.APIs.CustomerAPI.HTTP.Response.GetCustomer;
import com.wirehec.front_wirehec.Constants.TokenConstants;
import com.wirehec.front_wirehec.DTO.CustomerDTO;
import com.wirehec.front_wirehec.Utils.TokenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerController {
    @FXML private VBox menuVBox;
    @FXML private Button userDropdown;
    @FXML private VBox userMenu;
    @FXML private BorderPane contentPane;
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

    @FXML private TableView<CustomerDTO> clientTable;
    @FXML private TableColumn<CustomerDTO, Long> idColumn;
    @FXML private TableColumn<CustomerDTO, String> nombreClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> contactoClienteColumn;
    @FXML private TableColumn<CustomerDTO, Integer> telefonoClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> identificacionClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> emailClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> zonaClienteColumn;
    @FXML private TableColumn<CustomerDTO, String> direccionClienteColumn;

    @FXML private PieChart zonaClienteChart;

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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreClienteColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactoClienteColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        telefonoClienteColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        identificacionClienteColumn.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        emailClienteColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        zonaClienteColumn.setCellValueFactory(new PropertyValueFactory<>("zona"));
        direccionClienteColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        cargarDatos();
        clientTable.getItems().addListener((ListChangeListener<CustomerDTO>) change -> updateZonaClienteChart());

        // Actualizar gráfico inicialmente
        updateZonaClienteChart();


        // Decodificar el token y actualizar el botón userDropdown
        String token = TokenConstants.TOKEN;
        if (token != null && !token.isEmpty()) {
            String userName = TokenUtils.getUserNameFromToken(token);
            String userRole = TokenUtils.getUserRoleFromToken(token);

            if (userName != null && !userName.isEmpty()) {
                userDropdown.setText(userName);
            }

            if (userRole != null && !userRole.isEmpty()) {
                userRoleLabel.setText(userRole);
                userRoleLabel.setVisible(true);
            } else {
                userRoleLabel.setVisible(false);
            }
        }
    }
    private void updateZonaClienteChart() {
        ObservableList<CustomerDTO> customers = clientTable.getItems();
        if (customers != null && !customers.isEmpty()) {
            // Contar clientes por zona
            Map<String, Long> zonaCounts = customers.stream()
                    .collect(Collectors.groupingBy(CustomerDTO::getZona, Collectors.counting()));

            // Crear datos para el gráfico
            ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
            zonaCounts.forEach((zona, count) -> chartData.add(new PieChart.Data(zona, count)));

            // Actualizar el gráfico
            zonaClienteChart.setData(chartData);
        } else {
            // Vaciar el gráfico si no hay datos
            zonaClienteChart.setData(FXCollections.observableArrayList());
        }
    }
    public void cargarDatos() {
        GetCustomer getCustomer = new GetCustomer();
        List<CustomerDTO> customerList = getCustomer.sendGetCustomerRequest();
        clientTable.setItems(FXCollections.observableArrayList(customerList));

        updateZonaClienteChart();
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
    private void handleAddCustomer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/CustomerViews/AddCustomer-view.fxml"));
            Parent root = loader.load();
            AddCustomerController controller = loader.getController();
            controller.setCustomerController(this); // Pasar referencia del controlador principal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            cargarDatos(); // Recargar datos después de cerrar la ventana
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateCustomer() {
        CustomerDTO selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null || selected.getId() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, selecciona un cliente válido.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/CustomerViews/UpdateCustomer-view.fxml"));
            Parent root = loader.load();
            UpdateCustomerController controller = loader.getController();
            controller.setCustomer(selected); // Asegúrate de pasar el cliente con su ID
            controller.setCustomerController(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteCustomer() {
        CustomerDTO selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Cliente", "Por favor, selecciona un cliente para eliminar.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar este cliente?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirmar Eliminación");
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            try {
                Long idCliente = selected.getId();
                if (idCliente == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "El ID del cliente no es válido.");
                    return;
                }

                new DeleteCustomer().sendDeleteCustomerRequest(selected.getId());                cargarDatos();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error al eliminar el cliente.");
            }
        }
    }

    @FXML
    private void handleViewCustomer() {
        CustomerDTO selected = clientTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Cliente", "Por favor, selecciona un cliente para ver los detalles.");
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Detalles del Cliente");
        dialog.setHeaderText("Información del Cliente");

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Nombre: " + selected.getName()),
                new Label("Contacto: " + selected.getContacto()),
                new Label("Teléfono: " + selected.getTelefono()),
                new Label("Identificación: " + selected.getIdentificacion()),
                new Label("Email: " + selected.getEmail()),
                new Label("Zona: " + selected.getZona()),
                new Label("Dirección: " + selected.getDireccion())
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
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
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}