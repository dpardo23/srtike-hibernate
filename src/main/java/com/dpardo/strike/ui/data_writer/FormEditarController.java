package com.dpardo.strike.ui.data_writer;

import com.dpardo.strike.repository.PaisRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
// import java.sql.SQLException; // <-- ELIMINADO
import java.util.List;

/**
 * Controlador para el formulario de edición de Países.
 * (Adaptado para manejar Excepciones de Hibernate en lugar de SQLExceptions)
 */
public class FormEditarController {

    // --- Componentes FXML (Sin cambios) ---
    @FXML private ComboBox<String> codFifaComboBox;
    @FXML private TextField nombrePaisEditField;
    @FXML private Button guardarEditPaisButton;

    // --- Repositorios (Sin cambios) ---
    private final PaisRepository paisRepository = new PaisRepository();

    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        cargarCodigosFifa();
    }

    /**
     * Carga la lista de códigos FIFA. (Sin cambios)
     * El repositorio refactorizado sigue teniendo este método.
     */
    private void cargarCodigosFifa() {
        List<String> codigos = paisRepository.obtenerTodosLosCodigosFifa();
        codFifaComboBox.setItems(FXCollections.observableArrayList(codigos));
    }

    /**
     * Maneja el evento de clic en el botón "Guardar" (Editar).
     * (MODIFICADO: Se cambió 'catch (SQLException e)' por 'catch (Exception e)')
     */
    @FXML
    private void handleGuardarEditPais() {
        String codFifaSeleccionado = codFifaComboBox.getValue();
        String nuevoNombre = nombrePaisEditField.getText();

        // Validaciones (Sin cambios)
        if (codFifaSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Validación", "Debe seleccionar un código FIFA.");
            return;
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Validación", "El campo de nuevo nombre no puede estar vacío.");
            return;
        }

        try {
            // Llamada al repositorio para ejecutar el UPDATE
            paisRepository.actualizarNombrePais(codFifaSeleccionado, nuevoNombre);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El país con código '" + codFifaSeleccionado + "' ha sido actualizado.");

            // Limpiar formulario
            nombrePaisEditField.clear();
            codFifaComboBox.getSelectionModel().clearSelection();

        } catch (Exception e) { // <-- CAMBIADO DE SQLException
            // El e.getMessage() ahora viene del repositorio con un error claro
            // (ej. "No se encontró el país...")
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Actualizar", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra una ventana de alerta genérica. (Sin cambios)
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}