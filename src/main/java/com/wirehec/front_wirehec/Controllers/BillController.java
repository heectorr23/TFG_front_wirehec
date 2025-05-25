package com.wirehec.front_wirehec.Controllers;

import com.wirehec.front_wirehec.APIs.BillApi.HTTP.Response.GetBIll;
import com.wirehec.front_wirehec.DTO.FacturaDTO;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class BillController {

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

    @FXML private TableView<FacturaDTO> billTable;
    @FXML private TableColumn<FacturaDTO, Long> idColumn;
    @FXML private TableColumn<FacturaDTO, BigDecimal> priceColumn;
    @FXML private TableColumn<FacturaDTO, String> zoneColumn;
    @FXML private TableColumn<FacturaDTO, String> directionColumn;

    @FXML private PieChart billDistributionChart;

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

        // Configurar columnas de la tabla de facturas
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        zoneColumn.setCellValueFactory(new PropertyValueFactory<>("zona"));
        directionColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        // Cargar datos
        cargarDatos();
    }

    private void cargarDatos() {
        // Obtener datos de facturas desde la API
        GetBIll getBIll = new GetBIll();
        List<FacturaDTO> billList = getBIll.sendGetBillRequest();
        billTable.setItems(FXCollections.observableArrayList(billList));

        // Configurar gráfico de distribución de facturas por zona
        billDistributionChart.setData(FXCollections.observableArrayList(
                billList.stream()
                        .collect(Collectors.groupingBy(FacturaDTO::getZona, Collectors.counting()))
                        .entrySet()
                        .stream()
                        .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                        .toList()
        ));
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
                System.out.println("Cerrar sesión");
                break;
            default:
                System.out.println("Acción no reconocida: " + action);
        }
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
    public void navigateToInicio(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/MainViews/hello-view.fxml");
    }
    public void navigateToProductos(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/Product-SupplierViews/ProductSupplier-View.fxml");
    }
    public void navigateToContabilidad(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/ContabilityViews/Contability-view.fxml");
    }
    @FXML
    public void navigateToFacturas(ActionEvent event) {
        changeScene("/com/wirehec/front_wirehec/Views/BillViews/Bill-view.fxml");
    }
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
            stage.show(); // Asegúrate de mostrar la nueva escena
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + fxmlPath);
        }
    }
}