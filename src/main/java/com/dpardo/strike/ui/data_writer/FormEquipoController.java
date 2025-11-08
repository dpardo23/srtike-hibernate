package com.dpardo.strike.ui.data_writer;

import com.dpardo.strike.repository.EquipoRepository;
import com.dpardo.strike.repository.PaisRepository; // <-- ¡NUEVO IMPORT!
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

// import java.sql.SQLException; // <-- ELIMINADO
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controlador para el formulario de inserción y gestión de Equipos.
 * (Adaptado para manejar Excepciones de Hibernate y el repositorio de Pais)
 */
public class FormEquipoController {

    //--- Componentes FXML (Sin cambios) ---
    @FXML private TextField identificadorField;
    @FXML private TextField nombreEquipoField;
    @FXML private ComboBox<String> paisComboBox;
    @FXML private TextField ciudadField;
    @FXML private TextField fechaFundacionField;
    @FXML private TextField directorTecnicoField;
    @FXML private Button guardarEquipoButton;

    //--- Repositorios (MODIFICADO) ---
    private final EquipoRepository equipoRepository = new EquipoRepository();
    private final PaisRepository paisRepository = new PaisRepository(); // <-- ¡NUEVO REPOSITORIO AÑADIDO!

    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        cargarPaisesEnComboBox();
    }

    /**
     * Maneja el evento de clic en el botón "Guardar".
     * (MODIFICADO: Se eliminó el 'catch (SQLException e)' obsoleto)
     */
    @FXML
    void handleGuardarEquipo() {
        // Validación de campos obligatorios (Sin cambios)
        if (identificadorField.getText().trim().isEmpty() || nombreEquipoField.getText().trim().isEmpty() ||
                paisComboBox.getValue() == null || ciudadField.getText().trim().isEmpty() ||
                fechaFundacionField.getText().trim().isEmpty() || directorTecnicoField.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos Vacíos", "Todos los campos son obligatorios.");
            return;
        }

        try {
            // Conversión de tipos de dato (Sin cambios)
            int id = Integer.parseInt(identificadorField.getText());
            String nombre = nombreEquipoField.getText();
            String pais = paisComboBox.getValue();
            String ciudad = ciudadField.getText();
            LocalDate fechaFundacion = LocalDate.parse(fechaFundacionField.getText()); // Asume formato AAAA-MM-DD
            String dt = directorTecnicoField.getText();

            // Llamada al repositorio (Sin cambios)
            equipoRepository.insertarEquipo(id, nombre, pais, ciudad, fechaFundacion, dt);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El equipo '" + nombre + "' ha sido guardado correctamente.");
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "El identificador debe ser un número entero.");
        } catch (DateTimeParseException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "La fecha de fundación debe tener el formato AAAA-MM-DD.");
        } catch (Exception e) {
            // Este bloque AHORA captura todos los errores de Hibernate,
            // incluyendo Clave Primaria duplicada o Clave Foránea (Pais) no encontrada.
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Guardar", e.getMessage());
            e.printStackTrace();
        }
    }

    //--- Métodos Privados de Ayuda ---

    /**
     * Carga la lista de códigos de países desde la BD y los añade al ComboBox.
     * (MODIFICADO: Llama a PaisRepository en lugar de EquipoRepository)
     */
    private void cargarPaisesEnComboBox() {
        // Ya no necesitamos try-catch porque el nuevo repositorio
        // maneja sus propios errores y devuelve una lista vacía.

        // Antes: List<String> paises = equipoRepository.obtenerCodigosPaises();
        List<String> paises = paisRepository.obtenerTodosLosCodigosFifa();

        paisComboBox.setItems(FXCollections.observableArrayList(paises));
    }

    /**
     * Limpia todos los campos del formulario. (Sin cambios)
     */
    private void limpiarFormulario() {
        identificadorField.clear();
        nombreEquipoField.clear();
        paisComboBox.getSelectionModel().clearSelection();
        ciudadField.clear();
        fechaFundacionField.clear();
        directorTecnicoField.clear();
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