package com.dpardo.strike.ui.data_writer;

// import com.dpardo.strike.repository.DatabaseConnection; // <-- ELIMINADO
import com.dpardo.strike.repository.LigaRepository;
import com.dpardo.strike.repository.PaisRepository; // <-- ¡NUEVO IMPORT!
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

// --- Imports de java.sql ELIMINADOS ---
// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para el formulario de inserción y gestión de Ligas.
 * (Adaptado para manejar Excepciones de Hibernate)
 */
public class FormLigaController {

    //--- Componentes FXML (Sin cambios) ---
    @FXML private TextField identificadorField;
    @FXML private TextField nombreLigaField;
    @FXML private ComboBox<String> paisComboBox;
    @FXML private ComboBox<String> ligaComboBox;
    @FXML private Button guardarLigaButton;

    //--- Repositorios (MODIFICADO) ---
    private final LigaRepository ligaRepository = new LigaRepository();
    private final PaisRepository paisRepository = new PaisRepository(); // <-- ¡NUEVO REPOSITORIO AÑADIDO!

    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        cargarPaisesEnComboBox();
        cargarTiposDeLigaEnComboBox();
    }

    /**
     * Maneja el evento de clic en el botón "Guardar".
     * (MODIFICADO: Se unificaron los catch de Excepciones)
     */
    @FXML
    void handleGuardarLiga() {
        // Validación de campos obligatorios (Sin cambios)
        if (identificadorField.getText().trim().isEmpty() || nombreLigaField.getText().trim().isEmpty() ||
                paisComboBox.getValue() == null || ligaComboBox.getValue() == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos Vacíos", "Todos los campos son obligatorios.");
            return;
        }

        try {
            // Conversión de tipos de dato (Sin cambios)
            int id = Integer.parseInt(identificadorField.getText());
            String nombre = nombreLigaField.getText();
            String pais = paisComboBox.getValue();
            String tipo = ligaComboBox.getValue();

            // Llamada al repositorio (Sin cambios)
            ligaRepository.insertarLiga(id, nombre, pais, tipo);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "La liga '" + nombre + "' ha sido guardada correctamente.");
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "El identificador debe ser un número entero.");
        } catch (Exception e) {
            // Este bloque AHORA captura todos los errores de Hibernate (PK duplicada, FK no encontrada)
            // y también cualquier otro error inesperado.
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Guardar", e.getMessage());
            e.printStackTrace();
        }
    }

    //--- Métodos Privados de Ayuda ---

    /**
     * Carga las opciones de países en el ComboBox correspondiente.
     * (MODIFICADO: Llama a PaisRepository y elimina el try-catch)
     */
    private void cargarPaisesEnComboBox() {
        // Ya no se necesita try-catch, el repo devuelve lista vacía si falla
        List<String> paises = paisRepository.obtenerTodosLosCodigosFifa();
        paisComboBox.setItems(FXCollections.observableArrayList(paises));
    }

    /**
     * Carga una lista estática de tipos de liga. (Sin cambios)
     */
    private void cargarTiposDeLigaEnComboBox() {
        ObservableList<String> tipos = FXCollections.observableArrayList(
                "Profesional", "Semi-profesional", "Amateur",
                "Con sistema de divisiones y ascensos/descensos",
                "Con playoffs o eliminatorias", "Regional/Local"
        );
        ligaComboBox.setItems(tipos);
    }

    /**
     * Limpia todos los campos del formulario. (Sin cambios)
     */
    private void limpiarFormulario() {
        identificadorField.clear();
        nombreLigaField.clear();
        paisComboBox.getSelectionModel().clearSelection();
        ligaComboBox.getSelectionModel().clearSelection();
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

    /**
     * (ELIMINADO: Este método ya no es necesario)
     * private List<String> obtenerCodigosPaises() throws SQLException { ... }
     */
}