package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Request.DeleteEmployee;
import com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Response.GetEmployee;
import com.wirehec.front_wirehec.Constants.TokenConstants;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeController {

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

    @FXML private TableView<EmployeeDTO> employeeTable;
    @FXML private TableColumn<EmployeeDTO, Long> idColumn;
    @FXML private TableColumn<EmployeeDTO, String> nameColumn;
    @FXML private TableColumn<EmployeeDTO, String> emailColumn;
    @FXML private TableColumn<EmployeeDTO, String> roleColumn;

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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.joining(", "))
        ));

        loadEmployees();
        String token = TokenConstants.TOKEN;
        if (token != null && !token.isEmpty()) {
            String userName = TokenUtils.getUserNameFromToken(token);
            String userRole = TokenUtils.getUserRoleFromToken(token);
            userDropdown.setText(userName);
            userRoleLabel.setText(userRole);
        }
    }

    private void loadEmployees() {
        GetEmployee getEmployee = new GetEmployee();
        List<EmployeeDTO> employees = getEmployee.sendGetEmployeeRequest();
        if (employees != null && !employees.isEmpty()) {
            employeeTable.setItems(FXCollections.observableArrayList(employees));
        } else {
            System.err.println("No se encontraron empleados o la lista está vacía.");
            employeeTable.setItems(FXCollections.observableArrayList()); // Asegura que la tabla esté vacía si no hay datos
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
    private void toggleUserMenu() {
        isUserMenuVisible = !isUserMenuVisible;
        FadeTransition ft = new FadeTransition(Duration.millis(300), userMenu);
        ft.setFromValue(isUserMenuVisible ? 0.0 : 1.0);
        ft.setToValue(isUserMenuVisible ? 1.0 : 0.0);
        ft.play();
        userMenu.setVisible(isUserMenuVisible);
    }

    @FXML
    private void handleAddEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/EmployeeViews/AddEmployee-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Añadir Empleado");
            stage.setScene(new Scene(root, 600, 400));
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadEmployees();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateEmployee() {
        EmployeeDTO selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Empleado", "Por favor, selecciona un empleado para actualizar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/wirehec/front_wirehec/Views/EmployeeViews/UpdateEmployee-view.fxml"));
            Parent root = loader.load();

            UpdateEmployeeController controller = loader.getController();
            controller.setEmployeeData(selectedEmployee);

            Stage stage = new Stage();
            stage.setTitle("Actualizar Empleado");
            stage.setScene(new Scene(root, 600, 400));
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadEmployees();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        EmployeeDTO selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Empleado", "Por favor, selecciona un empleado para eliminar.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar este empleado?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirmar Eliminación");
        confirmation.showAndWait();

        if (confirmation.getResult() == ButtonType.YES) {
            new DeleteEmployee().sendDeleteEmployeeRequest(selectedEmployee.getIdEmpleado());
            loadEmployees();
        }
    }

    @FXML
    private void handleViewEmployee() {
        EmployeeDTO selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showAlert(Alert.AlertType.WARNING, "Seleccionar Empleado", "Por favor, selecciona un empleado para ver los detalles.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Detalles del Empleado", selectedEmployee.toString());
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
        String userRole = TokenUtils.getUserRoleFromToken(TokenConstants.TOKEN);
        if (!"ROLE_BOSS".equals(userRole)) {
            showAlert(Alert.AlertType.ERROR, "Acceso Denegado", "No tienes permiso para acceder a esta vista.");
            return;
        }
        changeScene("/com/wirehec/front_wirehec/Views/EmployeeViews/Employee-view.fxml");
    }

    @FXML
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

            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + fxmlPath);
        }
    }
}