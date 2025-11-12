package com.dpardo.strike.ui.login;

import com.dpardo.strike.domain.SessionInfo;
import com.dpardo.strike.domain.SessionManager;
import com.dpardo.strike.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
// import java.sql.SQLException; // <-- ELIMINADO
import java.util.Objects;

/**
 * Controlador para la ventana de Login.
 * (Adaptado para manejar el UserRepository de Hibernate)
 */
public class LoginController {

    //--- Componentes FXML (Sin cambios) ---
    @FXML private TextField tf_username;
    @FXML private PasswordField pf_password;
    @FXML private Button btnLogin;

    //--- Repositorios (Sin cambios) ---
    private final UserRepository userRepository = new UserRepository();

    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        if (btnLogin != null) {
            btnLogin.setOnAction(event -> handleLoginButtonAction());
        }
    }

    /**
     * Maneja el evento de clic en el botón de "Iniciar Sesión".
     * (MODIFICADO: Se eliminó el try-catch de SQLException)
     */
    @FXML
    private void handleLoginButtonAction() {
        String username = tf_username.getText();
        String password = pf_password.getText();

        // Validación de entrada (Sin cambios)
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Entrada Inválida", "Por favor, ingrese su nombre de usuario y contraseña.", Alert.AlertType.WARNING);
            return;
        }

        // Ya no se necesita un try-catch aquí, porque el repositorio
        // refactorizado maneja sus propios errores y devuelve null.
        SessionInfo sessionInfo = userRepository.authenticateAndRegisterSession(username, password);

        if (sessionInfo != null && sessionInfo.roleName() != null) {
            // Guarda la sesión actual
            SessionManager.setCurrentSession(sessionInfo);
            System.out.println("Login exitoso. Usuario: " + username + ", Rol: " + sessionInfo.roleName());

            // Navega a la ventana principal
            navigateToMainView(sessionInfo.roleName());
        } else {
            // Esta alerta ahora se muestra si la autenticación falla
            // O si el repositorio no pudo conectarse a la BD (ya que devuelve null)
            showAlert("Error de Autenticación", "Usuario, contraseña o rol incorrectos.", Alert.AlertType.ERROR);
        }
    }

    //--- Métodos Privados de Ayuda (Sin cambios) ---

    /**
     * Carga la ventana principal correspondiente al rol del usuario y cierra la ventana de login.
     * @param roleName El nombre del rol del usuario autenticado.
     */
    private void navigateToMainView(String roleName) {
        String fxmlPath;
        String windowTitle;

        // Determina la vista a cargar según el rol
        switch (roleName.toLowerCase()) {
            case "read_only": // Rol corregido (guion medio)
                fxmlPath = "/com/dpardo/strike/ui/read_only/Home-view.fxml";
                windowTitle = "Strike - Vista de Lector";
                break;
            case "data_writer": // Verifica si en tu BD es con guion bajo o medio
                fxmlPath = "/com/dpardo/strike/ui/data_writer/Home-admin.fxml";
                windowTitle = "Strike - Panel de Administración";
                break;
            case "super_user":
                fxmlPath = "/com/dpardo/strike/ui/super_user/Home-superadmin.fxml";
                windowTitle = "Strike - Panel de Super Administrador";
                break;
            default:
                System.err.println("Rol recibido no reconocido: " + roleName);
                showAlert("Error de Rol", "Rol no reconocido: " + roleName, Alert.AlertType.ERROR);
                return;
        }

        try {
            // 1. Cargar el FXML
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));

            // 2. Crear una nueva Escena con tamaño explícito
            Scene scene = new Scene(root, 960, 600);

            // 3. Configurar el Stage (Ventana)
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(scene);

            // --- FUERZA BRUTA PARA EL TAMAÑO ---
            // Establecemos el tamaño mínimo y el tamaño actual directamente al Stage
            stage.setMinWidth(960);
            stage.setMinHeight(600);
            stage.setWidth(960);
            stage.setHeight(600);

            // Evitamos que se pueda redimensionar a algo ilegible (opcional)
            stage.setResizable(false);

            // 4. Mostrar y Centrar
            stage.show();
            stage.centerOnScreen(); // Centrar DESPUÉS de show() a veces funciona mejor en Linux/Gnome

            // 5. Cerrar login
            Stage loginStage = (Stage) btnLogin.getScene().getWindow();
            loginStage.close();

        } catch (IOException | NullPointerException e) {
            showAlert("Error de Carga", "No se pudo cargar la ventana principal para el rol: " + roleName, Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    /**
     * Muestra una ventana de alerta genérica. (Sin cambios)
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}