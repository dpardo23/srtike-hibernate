package com.dpardo.strike.ui.data_writer;

import com.dpardo.strike.domain.EquipoComboItem;
import com.dpardo.strike.domain.LigaComboItem;
import com.dpardo.strike.repository.EquipoRepository;
import com.dpardo.strike.repository.LigaRepository;
import com.dpardo.strike.repository.PartidoRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

// import java.sql.SQLException; // <-- ELIMINADO
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controlador para el formulario de inserción y gestión de Partidos.
 * (Adaptado para manejar Excepciones de Hibernate)
 */
public class FormPartidoController {

    // --- Componentes FXML (Sin cambios) ---
    @FXML private ComboBox<EquipoComboItem> equipoLocalComboBox;
    @FXML private ComboBox<EquipoComboItem> equipoVisitanteComboBox;
    @FXML private TextField fechaField;
    @FXML private TextField HoraField;
    @FXML private ComboBox<LigaComboItem> ligaComboBox;
    @FXML private Button guardarPartidoButton;
    @FXML private TextField historialField;

    // --- Repositorios (Sin cambios) ---
    private final EquipoRepository equipoRepository = new EquipoRepository();
    private final LigaRepository ligaRepository = new LigaRepository();
    private final PartidoRepository partidoRepository = new PartidoRepository();

    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        cargarEquipos();
        cargarLigas();
    }

    /**
     * Carga la lista de equipos (MODIFICADO: Se eliminó el try-catch)
     */
    private void cargarEquipos() {
        // Ya no se necesita try-catch, el repo devuelve lista vacía si falla
        List<EquipoComboItem> equipos = equipoRepository.obtenerEquiposParaCombo();
        equipoLocalComboBox.setItems(FXCollections.observableArrayList(equipos));
        equipoVisitanteComboBox.setItems(FXCollections.observableArrayList(equipos));
    }

    /**
     * Carga la lista de ligas (MODIFICADO: Se eliminó el try-catch)
     */
    private void cargarLigas() {
        // Ya no se necesita try-catch, el repo devuelve lista vacía si falla
        List<LigaComboItem> ligas = ligaRepository.obtenerLigasParaCombo();
        ligaComboBox.setItems(FXCollections.observableArrayList(ligas));
    }

    /**
     * Maneja el evento de clic en el botón "Guardar".
     * (MODIFICADO: Se unificaron los catch de Excepciones)
     */
    @FXML
    void handleGuardarPartido() {
        // Validación (Sin cambios)
        if (equipoLocalComboBox.getValue() == null || equipoVisitanteComboBox.getValue() == null ||
                ligaComboBox.getValue() == null || fechaField.getText().trim().isEmpty() ||
                HoraField.getText().trim().isEmpty() || historialField.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos Vacíos", "Todos los campos son obligatorios.");
            return;
        }

        try {
            // Conversión de datos (Sin cambios)
            int equipoLocalId = equipoLocalComboBox.getValue().id();
            int equipoVisitanteId = equipoVisitanteComboBox.getValue().id();
            int ligaId = ligaComboBox.getValue().id();
            LocalDate fecha = LocalDate.parse(fechaField.getText()); // Formato AAAA-MM-DD
            LocalTime hora = LocalTime.parse(HoraField.getText()); // Formato HH:MM o HH:MM:SS
            int historial = Integer.parseInt(historialField.getText());

            // Llamada al repositorio (Sin cambios)
            partidoRepository.insertarPartido(equipoLocalId, equipoVisitanteId, fecha, hora, ligaId, historial);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El partido ha sido guardado correctamente.");
            limpiarFormulario();

        } catch (DateTimeParseException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "La fecha debe ser AAAA-MM-DD y la hora HH:MM.");
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "El historial debe ser un número entero.");
        } catch (Exception e) {
            // Este bloque AHORA captura todos los errores de Hibernate
            // (PK duplicada, FKs no encontradas, etc.)
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Guardar", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Limpia el formulario. (Sin cambios)
     */
    private void limpiarFormulario() {
        equipoLocalComboBox.getSelectionModel().clearSelection();
        equipoVisitanteComboBox.getSelectionModel().clearSelection();
        ligaComboBox.getSelectionModel().clearSelection();
        fechaField.clear();
        HoraField.clear();
        historialField.clear();
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