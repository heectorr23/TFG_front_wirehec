package com.wirehec.front_wirehec.Controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class MainController {
    @FXML
    private VBox menuVBox;

    @FXML
    private Button userDropdown;

    @FXML
    private VBox userMenu;

    @FXML
    private GridPane contentPane;

    @FXML
    private Label menuLabel;

    @FXML
    private Button hamburgerButton;

    private boolean isUserMenuVisible = false;
    private boolean isMenuExpanded = false;

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
    private void navigateToView() throws IOException {
        Button source = (Button) menuVBox.getScene().getFocusOwner();
        String view = source.getText();
        try {
            switch (view) {
                case "Inicio":
                    // Limpia el contenedor y recarga la vista principal
                    Parent root = FXMLLoader.load(getClass().getResource("/com/wirehec/front_wirehec/Views/MainViews/hello-view.fxml"));
                    contentPane.getScene().setRoot(root); // Reemplaza toda la escena con la nueva vista
                    break;
                case "Productos":
                    contentPane.getChildren().setAll(new Label("Productos View - Add your content here"));
                    break;
                case "Proveedores":
                    contentPane.getChildren().setAll(new Label("Proveedores View - Add your content here"));
                    break;
                case "Contabilidad":
                    contentPane.getChildren().setAll(new Label("Contabilidad View - Add your content here"));
                    break;
                case "Facturas":
                    contentPane.getChildren().setAll(new Label("Facturas View - Add your content here"));
                    break;
                case "Ajustes":
                    contentPane.getChildren().setAll(new Label("Ajustes View - Add your content here"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
