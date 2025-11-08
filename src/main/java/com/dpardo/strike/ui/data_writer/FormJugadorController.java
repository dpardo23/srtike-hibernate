package com.dpardo.strike.ui.data_writer;

import com.dpardo.strike.domain.EquipoComboItem;
import com.dpardo.strike.domain.PaisComboItem;
import com.dpardo.strike.domain.SexoComboItem;
import com.dpardo.strike.repository.EquipoRepository;
import com.dpardo.strike.repository.JugadorRepository;
import com.dpardo.strike.repository.PaisRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
// import java.sql.SQLException; // <-- ELIMINADO
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controlador para el formulario de inserción y gestión de Jugadores.
 * (Adaptado para manejar Excepciones de Hibernate)
 */
public class FormJugadorController {

    //--- Componentes FXML (Sin cambios) ---
    @FXML private TextField identificadorField;
    @FXML private TextField nombreField;
    @FXML private ComboBox<SexoComboItem> sexoComboBox;
    @FXML private TextField alturaField;
    @FXML private ComboBox<EquipoComboItem> equipoComboBox;
    @FXML private TextField pesoField;
    @FXML private TextField fechaNacimientoField;
    @FXML private ComboBox<PaisComboItem> paisComboBox;
    @FXML private ComboBox<String> posicionComboBox;
    @FXML private Button elegirFotoButton;
    @FXML private TextField estadisticaField;
    @FXML private Button guardarJugadorButton;

    //--- Repositorios (Sin cambios) ---
    private final JugadorRepository jugadorRepository = new JugadorRepository();
    private final PaisRepository paisRepository = new PaisRepository();
    private final EquipoRepository equipoRepository = new EquipoRepository();

    //--- Estado del Controlador (Sin cambios) ---
    private byte[] fotoBytes;

    /**
     * Método de inicialización. (Sin cambios)
     */
    @FXML
    public void initialize() {
        cargarSexos();
        cargarPosiciones();
        cargarPaises();
        cargarEquipos();
    }

    //--- Manejadores de Eventos FXML ---

    /**
     * Abre un FileChooser. (Sin cambios)
     */
    @FXML
    void handleElegirFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto del Jugador");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg"));

        Stage stage = (Stage) elegirFotoButton.getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            try {
                this.fotoBytes = Files.readAllBytes(archivoSeleccionado.toPath());
                elegirFotoButton.setText(archivoSeleccionado.getName());
            } catch (IOException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Archivo", "No se pudo leer la imagen seleccionada.");
            }
        }
    }

    /**
     * Maneja el evento de clic en el botón "Guardar".
     * (MODIFICADO: Se eliminó el 'catch (SQLException e)' obsoleto)
     */
    @FXML
    void handleGuardarJugador() {
        // Validación de campos obligatorios (Sin cambios)
        if (identificadorField.getText().isBlank() || nombreField.getText().isBlank() ||
                sexoComboBox.getValue() == null || paisComboBox.getValue() == null ||
                posicionComboBox.getValue() == null || equipoComboBox.getValue() == null ||
                fechaNacimientoField.getText().isBlank() || alturaField.getText().isBlank() ||
                pesoField.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos Vacíos", "Todos los campos, excepto foto y estadísticas, son obligatorios.");
            return;
        }

        try {
            // Recolección y conversión de datos (Sin cambios)
            int id = Integer.parseInt(identificadorField.getText());
            String nombre = nombreField.getText();
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoField.getText());
            char sexo = sexoComboBox.getValue().valor();
            String paisCodFifa = paisComboBox.getValue().codFifa();
            String posicion = posicionComboBox.getValue();
            int equipoId = equipoComboBox.getValue().id();
            int altura = Integer.parseInt(alturaField.getText());
            BigDecimal peso = new BigDecimal(pesoField.getText().replace(',', '.'));
            Integer estadisticas = estadisticaField.getText().isBlank() ? null : Integer.parseInt(estadisticaField.getText());

            if (this.fotoBytes == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Sin Foto", "No se ha seleccionado una foto. Se guardará sin imagen.");
            }

            // Llamada al repositorio (Sin cambios)
            jugadorRepository.insertarJugador(id, nombre, fechaNacimiento, sexo, paisCodFifa,
                    posicion, equipoId, estadisticas, altura, peso, this.fotoBytes);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El jugador '" + nombre + "' ha sido guardado.");
            limpiarFormulario();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "Identificador, altura, peso y estadísticas deben ser números válidos.");
        } catch (DateTimeParseException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", "La fecha de nacimiento debe tener el formato AAAA-MM-DD.");
        } catch (Exception e) { // <-- CAMBIADO DE SQLException
            // Este bloque AHORA captura todos los errores de Hibernate
            // (PK duplicada, FKs no encontradas, etc.)
            mostrarAlerta(Alert.AlertType.ERROR, "Error al Guardar", e.getMessage());
            e.printStackTrace();
        }
    }

    //--- Métodos Privados de Ayuda ---

    /**
     * Carga las opciones de sexo. (Sin cambios)
     */
    private void cargarSexos() {
        sexoComboBox.setItems(FXCollections.observableArrayList(
                new SexoComboItem('M', "Masculino"),
                new SexoComboItem('F', "Femenino")
        ));
    }

    /**
     * Carga las posiciones de jugador. (Sin cambios)
     */
    private void cargarPosiciones() {
        posicionComboBox.setItems(FXCollections.observableArrayList(
                "Arquero", "Defensa", "Lateral", "Mediocampista",
                "Volante", "Delantero", "Extremo", "Pivote"
        ));
    }

    /**
     * Carga la lista de países desde la BD para el ComboBox.
     * (MODIFICADO: Se eliminó el 'try-catch(SQLException)')
     */
    private void cargarPaises() {
        // Ya no se necesita try-catch
        List<PaisComboItem> paises = paisRepository.obtenerPaisesParaCombo();
        paisComboBox.setItems(FXCollections.observableArrayList(paises));
    }

    /**
     * Carga la lista de equipos desde la BD para el ComboBox.
     * (MODIFICADO: Se eliminó el 'try-catch(SQLException)')
     */
    private void cargarEquipos() {
        // Ya no se necesita try-catch
        List<EquipoComboItem> equipos = equipoRepository.obtenerEquiposParaCombo();
        equipoComboBox.setItems(FXCollections.observableArrayList(equipos));
    }

    /**
     * Limpia todos los campos del formulario. (Sin cambios)
     */
    private void limpiarFormulario() {
        identificadorField.clear();
        nombreField.clear();
        sexoComboBox.getSelectionModel().clearSelection();
        alturaField.clear();
        equipoComboBox.getSelectionModel().clearSelection();
        pesoField.clear();
        fechaNacimientoField.clear();
        paisComboBox.getSelectionModel().clearSelection();
        posicionComboBox.getSelectionModel().clearSelection();
        estadisticaField.clear();
        elegirFotoButton.setText("Elegir Foto...");
        this.fotoBytes = null;
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