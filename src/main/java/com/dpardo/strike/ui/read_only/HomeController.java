package com.dpardo.strike.ui.read_only;

// --- Imports existentes ---
import com.dpardo.strike.domain.Pais;
import com.dpardo.strike.repository.PaisRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

// --- Imports AÑADIDOS para la nueva funcionalidad ---
import com.dpardo.strike.domain.SessionInfo;
import com.dpardo.strike.domain.SessionManager;
import com.dpardo.strike.domain.UiComboItem;
import com.dpardo.strike.repository.SuperAdminRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
// import java.sql.SQLException; // <-- ELIMINADO
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Controlador para la vista principal de "solo lectura".
 * (Adaptado para manejar Excepciones de Hibernate)
 */
public class HomeController {

    // --- Componentes FXML (Sin cambios) ---
    @FXML private Button jugadoresButton;
    @FXML private Button estadisticasButton;
    @FXML private Button equiposButton;
    @FXML private Button partidosButton;
    @FXML private Button clasificacionButton;
    @FXML private Button resumenButton;
    @FXML private Button ligaButton;
    @FXML private BorderPane mainBorderPane;
    @FXML private VBox paisContenedor;
    @FXML private ComboBox<UiComboItem> viewHomeComboBox;
    @FXML private Button userinfoHomeButton;
    @FXML private Tooltip usernameAdminTooltip;

    // --- Repositorios (Sin cambios) ---
    private final PaisRepository paisRepository = new PaisRepository();
    private final SuperAdminRepository superAdminRepo = new SuperAdminRepository();

    // --- Utilidades (Sin cambios) ---
    private final Map<String, String> uiPathMap = new HashMap<>();


    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        cargarPaises();
        setupUserInfo();
        setupComboBox();
    }

    /**
     * Carga los países desde la base de datos.
     * (Sin cambios, ya que el método del repositorio se llama igual
     * y el try-catch aquí es para IOException, no SQLException)
     */
    public void cargarPaises() {
        if (paisContenedor != null) {
            paisContenedor.getChildren().clear();
        }
        List<Pais> listaDePaises = paisRepository.obtenerTodosLosPaises();
        System.out.println("Países obtenidos de la BD: " + listaDePaises.size());
        for (Pais pais : listaDePaises) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dpardo/strike/ui/read_only/Pais-view.fxml"));
                Node nodoPaisItem = loader.load();
                PaisItemController paisController = loader.getController();
                String rutaImagen = "/images/flags/" + pais.codigo() + ".png";
                Image bandera = new Image(getClass().getResourceAsStream(rutaImagen));
                paisController.setData(pais.nombre(), bandera);
                paisContenedor.getChildren().add(nodoPaisItem);
            } catch (IOException | NullPointerException e) {
                System.err.println("Error al cargar item para " + pais.nombre() + ". ¿Falta la imagen " + pais.codigo() + ".png en resources?");
                e.printStackTrace();
            }
        }
    }


    // --- Métodos de UI del Header (Sin cambios) ---

    @FXML
    private void handleUserInfoClick() {
        SessionInfo currentSession = SessionManager.getCurrentSession();
        if (currentSession == null) return;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información del Usuario");
        alert.setHeaderText("Detalles de la Sesión Actual");

        String content = String.format(
                "ID de Usuario: %d\n" +
                        "Rol: %s\n" +
                        "PID de la Sesión: %d\n" +
                        "IP Cliente: %s\n" +
                        "Puerto Cliente: %d",
                currentSession.userId(),
                currentSession.roleName(),
                currentSession.pid(),
                currentSession.clientAddress(),
                currentSession.clientPort()
        );
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleViewSelection() {
        UiComboItem selectedUi = viewHomeComboBox.getValue();
        if (selectedUi != null) {
            String fxmlPath = uiPathMap.get(selectedUi.codComponente());
            if (fxmlPath != null) {
                openNewWindow(fxmlPath, selectedUi.descripcion());
            } else {
                System.err.println("No se encontró la ruta para: " + selectedUi.codComponente());
            }
            Platform.runLater(() -> viewHomeComboBox.getSelectionModel().clearSelection());
        }
    }

    private void setupUserInfo() {
        SessionInfo currentSession = SessionManager.getCurrentSession();
        if (currentSession != null) {
            usernameAdminTooltip.setText("Usuario ID: " + currentSession.userId());
        } else {
            userinfoHomeButton.setVisible(false);
        }
    }

    /**
     * Carga las vistas disponibles desde la BD en el ComboBox de navegación.
     * (MODIFICADO: Se eliminó el 'try-catch(SQLException)')
     */
    private void setupComboBox() {
        uiPathMap.put("homeBorderPane", "/com/dpardo/strike/ui/read_only/Home-view.fxml");
        uiPathMap.put("adminBorderPane", "/com/dpardo/strike/ui/data_writer/Home-admin.fxml");
        uiPathMap.put("superadminBorderPane", "/com/dpardo/strike/ui/super_user/Home-superadmin.fxml");

        // Ya no se necesita try-catch, el repo devuelve lista vacía si falla
        SessionInfo currentSession = SessionManager.getCurrentSession();
        if (currentSession != null) {
            int userId = currentSession.userId();
            viewHomeComboBox.setItems(FXCollections.observableArrayList(superAdminRepo.obtenerUis(userId)));
        }

        viewHomeComboBox.setOnAction(event -> handleViewSelection());
    }

    /**
     * Carga y muestra una nueva ventana FXML. (Sin cambios)
     */
    private void openNewWindow(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = new Stage();
            stage.setTitle(title);
            // Añade las dimensiones 960x600 (o las que prefieras)
            stage.setScene(new Scene(root, 960, 600)); // <-- ARREGLADO
            stage.show();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}