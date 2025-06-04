package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.AuthAPI.HTTP.Request.PutChangePassword;
import com.wirehec.front_wirehec.APIs.EmployeeAPI.HTTP.Response.GetEmployee;
import com.wirehec.front_wirehec.Constants.TokenConstants;
import com.wirehec.front_wirehec.DTO.EmployeeDTO;
import com.wirehec.front_wirehec.Utils.TokenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SettingController {

    @FXML
    private VBox menuVBox;
    @FXML
    private Button userDropdown;
    @FXML
    private VBox userMenu;
    @FXML
    private Label menuLabel;
    @FXML
    private Button hamburgerButton;
    @FXML
    private Label userRoleLabel;
    @FXML
    private GridPane contentPane;

    @FXML
    private Button inicioButton;
    @FXML
    private Button productosButton;
    @FXML
    private Button contabilidadButton;
    @FXML
    private Button facturasButton;
    @FXML
    private Button empleadosButton;
    @FXML
    private Button clientesButton;
    @FXML
    private Button ajustesButton;


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


        String userName = TokenUtils.getUserNameFromToken(TokenConstants.TOKEN);
        userDropdown.setText(userName != null ? userName : "Invitado");

        String userRole = TokenUtils.getUserRoleFromToken(TokenConstants.TOKEN);
        userRoleLabel.setText(userRole != null ? userRole : "Invitado");


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
    }

    private void setButtonIcon(Button button, String iconLiteral) {
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
    private void navigateToInicio() {
        changeScene("/com/wirehec/front_wirehec/Views/MainViews/hello-view.fxml");
    }

    @FXML
    private void navigateToProductos() {
        changeScene("/com/wirehec/front_wirehec/Views/Product-SupplierViews/ProductSupplier-View.fxml");
    }

    @FXML
    private void navigateToContabilidad() {
        changeScene("/com/wirehec/front_wirehec/Views/ContabilityViews/Contability-view.fxml");
    }

    @FXML
    private void navigateToFacturas() {
        changeScene("/com/wirehec/front_wirehec/Views/BillViews/Bill-view.fxml");
    }

    @FXML
    private void navigateToEmpleados() {
        changeScene("/com/wirehec/front_wirehec/Views/EmployeeViews/Employee-view.fxml");
    }

    @FXML
    private void navigateToCustomer() {
        changeScene("/com/wirehec/front_wirehec/Views/CustomerViews/Customer-view.fxml");
    }

    public void navigateToLogin(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/AuthViews/Login-view.fxml");
    }

    @FXML
    private void navigateToAjustes() {
        changeScene("/com/wirehec/front_wirehec/Views/SettingViews/Setting-view.fxml");
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

                TokenConstants.TOKEN = null;


                navigateToLogin(null);
                break;
        }
        toggleUserMenu();
    }

    private void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChangePassword() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cambiar Contraseña");
        dialog.setHeaderText("Ingrese su nueva contraseña:");
        dialog.setContentText("Nueva contraseña:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPassword -> {
            if (newPassword.isEmpty()) {
                showAlert("Error", "La contraseña no puede estar vacía.", Alert.AlertType.ERROR);
                return;
            }


            PutChangePassword changePasswordRequest = new PutChangePassword();
            String response = changePasswordRequest.sendChangePasswordRequest(TokenConstants.TOKEN, newPassword);


            if (response.contains("actualizada correctamente")) {
                showAlert("Éxito", response, Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", response, Alert.AlertType.ERROR);
            }
        });
    }
    @FXML
    private void handleShowUserInfo() {
        try {

            GetEmployee getEmployeeRequest = new GetEmployee();
            List<EmployeeDTO> employees = getEmployeeRequest.sendGetEmployeeRequest();

            if (employees.isEmpty()) {
                showAlert("Información", "No se encontró información del usuario registrado.", Alert.AlertType.INFORMATION);
                return;
            }


            String currentName = TokenUtils.getUserNameFromToken(TokenConstants.TOKEN);
            EmployeeDTO user = employees.stream()
                    .filter(employee -> employee.getNombreEmpleado() != null && employee.getNombreEmpleado().equals(currentName))
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                showAlert("Información", "No se encontró información del usuario registrado.", Alert.AlertType.INFORMATION);
                return;
            }


            String userInfo = String.format(
                    "Nombre: %s\nNIF: %s\nTeléfono: %d\nEmail: %s",
                    user.getNombreEmpleado(),
                    user.getNifEmpleado(),
                    user.getTelefonoEmpleado(),
                    user.getEmail()
            );

            showAlert("Información del Usuario", userInfo, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error al obtener la información del usuario.", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}