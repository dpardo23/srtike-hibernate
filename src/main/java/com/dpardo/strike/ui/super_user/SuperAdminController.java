package com.dpardo.strike.ui.super_user;

import com.dpardo.strike.domain.SessionInfo;
import com.dpardo.strike.domain.SessionManager;
import com.dpardo.strike.domain.SessionViewModel;
import com.dpardo.strike.domain.UiComboItem;
import com.dpardo.strike.repository.SuperAdminRepository;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
// import java.sql.SQLException; // <-- ELIMINADO
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Controlador para la ventana de Super Administrador.
 * (Adaptado para manejar el Repositorio de Hibernate)
 */
public class SuperAdminController {

    //--- Componentes FXML (Sin cambios) ---
    @FXML private ComboBox<UiComboItem> viewSelectorComboBox;
    @FXML private Button userInfoButton;
    @FXML private Tooltip usernameTooltip;
    @FXML private TableView<SessionViewModel> mainTableView;
    @FXML private TableColumn<SessionViewModel, Integer> pidColumn;
    @FXML private TableColumn<SessionViewModel, String> userColumn;
    @FXML private TableColumn<SessionViewModel, String> correoColumn;
    @FXML private TableColumn<SessionViewModel, Timestamp> fecCreacionColumn;
    @FXML private TableColumn<SessionViewModel, String> rolColumn;
    @FXML private TableColumn<SessionViewModel, String> uiColumn;
    @FXML private TableColumn<SessionViewModel, String> direccionIpColumn;
    @FXML private TableColumn<SessionViewModel, Integer> puertoColumn;
    @FXML private TableColumn<SessionViewModel, Timestamp> fecAsignacionColumn;
    @FXML private TableColumn<SessionViewModel, Boolean> activoColumn;

    //--- Repositorios y Servicios (Sin cambios) ---
    private final SuperAdminRepository repository = new SuperAdminRepository();
    private ScheduledService<ObservableList<SessionViewModel>> sessionUpdateService;
    private final Map<String, String> uiPathMap = new HashMap<>();

    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        setupUserInfo();
        setupComboBox();
        setupTableView();
        startSessionUpdateService();
    }

    /**
     * Detiene los servicios en segundo plano. (Sin cambios)
     */
    public void stop() {
        if (sessionUpdateService != null) {
            sessionUpdateService.cancel();
            System.out.println("Servicio de actualización de sesiones detenido.");
        }
    }

    //--- Manejadores de Eventos FXML (Sin cambios) ---

    @FXML
    private void handleViewSelection() {
        UiComboItem selectedUi = viewSelectorComboBox.getValue();
        if (selectedUi != null) {
            String fxmlPath = uiPathMap.get(selectedUi.codComponente());
            if (fxmlPath != null) {
                openNewWindow(fxmlPath, selectedUi.descripcion());
            } else {
                System.err.println("No se encontró la ruta para: " + selectedUi.codComponente());
            }
            Platform.runLater(() -> viewSelectorComboBox.getSelectionModel().clearSelection());
        }
    }

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

    //--- Métodos Privados de Configuración ---

    /**
     * Configura la información del usuario. (Sin cambios)
     */
    private void setupUserInfo() {
        SessionInfo currentSession = SessionManager.getCurrentSession();
        if (currentSession != null) {
            usernameTooltip.setText("Usuario: " + currentSession.userId());
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

        // --- El bloque try-catch fue eliminado ---
        // Nuestro 'repository.obtenerUis()' ahora devuelve una lista vacía
        // si falla, por lo que no lanzará una excepción aquí.
        SessionInfo currentSession = SessionManager.getCurrentSession();
        if (currentSession != null) {
            int userId = currentSession.userId();
            viewSelectorComboBox.setItems(FXCollections.observableArrayList(repository.obtenerUis(userId)));
        }

        viewSelectorComboBox.setOnAction(event -> handleViewSelection());
    }

    /**
     * Configura las columnas de la TableView. (Sin cambios)
     */
    private void setupTableView() {
        pidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().pid()).asObject());
        userColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreUsuario()));
        correoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        fecCreacionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().fecCreacionUsuario()));
        rolColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreRol()));
        uiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codComponenteUi()));
        direccionIpColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().direccionIp()));
        puertoColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().puerto()).asObject());
        fecAsignacionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().fechaAsignacionRol()));
        activoColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().rolActivo()).asObject());
    }

    /**
     * Inicia un servicio que actualiza la tabla de sesiones periódicamente.
     * (Sin cambios. El Task ya maneja 'Exception' genéricas, y nuestro
     * repositorio ahora devuelve una lista vacía si falla, por lo que
     * 'setOnFailed' solo se activará si el Task de JavaFX falla,
     * lo cual es correcto.)
     */
    private void startSessionUpdateService() {
        sessionUpdateService = new ScheduledService<>() {
            @Override
            protected Task<ObservableList<SessionViewModel>> createTask() {
                return new Task<>() {
                    @Override
                    protected ObservableList<SessionViewModel> call() throws Exception {
                        // Esta llamada ya no lanza SQLException.
                        return FXCollections.observableArrayList(repository.obtenerSesionesActivas());
                    }
                };
            }
        };

        sessionUpdateService.setPeriod(Duration.seconds(3));
        sessionUpdateService.setOnSucceeded(event -> mainTableView.setItems(sessionUpdateService.getValue()));
        sessionUpdateService.setOnFailed(event -> sessionUpdateService.getException().printStackTrace());
        sessionUpdateService.start();
    }

    /**
     * Carga y muestra una nueva ventana FXML. (Sin cambios)
     */
    private void openNewWindow(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            root.setOpacity(0.0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            stage.show();
            fadeIn.play();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}