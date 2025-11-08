package com.dpardo.strike.ui.data_writer;

import com.dpardo.strike.repository.JugadorRepository;
import com.dpardo.strike.repository.PaisRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
// import java.sql.SQLException; // <-- ELIMINADO
import java.util.Optional;

/**
 * Controlador para el formulario de borrado.
 * (Adaptado para manejar Excepciones de Hibernate en lugar de SQLExceptions)
 */
public class FormBorrarController {

    // --- Componentes FXML (Sin cambios) ---
    @FXML private ComboBox<String> paisComboBox;
    @FXML private Button borrarPaisButton;
    @FXML private ComboBox<String> jugadorCombBox;
    @FXML private Button borrarJugadorButton;

    // --- Repositorios (Sin cambios) ---
    private final PaisRepository paisRepository = new PaisRepository();
    private final JugadorRepository jugadorRepository = new JugadorRepository();

    /**
     * Método de inicialización. (Sin cambios)
     * Los métodos del repositorio se siguen llamando igual.
     */
    @FXML
    public void initialize() {
        cargarPaises();
        cargarJugadores();
    }

    /**
     * Carga la lista de códigos FIFA. (Sin cambios)
     */
    private void cargarPaises() {
        paisComboBox.setItems(FXCollections.observableArrayList(paisRepository.obtenerTodosLosCodigosFifa()));
    }

    /**
     * Carga la lista de nombres de jugadores. (Sin cambios)
     */
    private void cargarJugadores() {
        jugadorCombBox.setItems(FXCollections.observableArrayList(jugadorRepository.obtenerTodosLosNombresDeJugadores()));
    }

    /**
     * Maneja el evento de clic en el botón "Borrar País".
     * (MODIFICADO: Se cambió 'catch (SQLException e)' por 'catch (Exception e)')
     */
    @FXML
    private void handleBorrarPais() {
        String paisSeleccionado = paisComboBox.getValue();
        if (paisSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Vacía", "Por favor, seleccione un país para borrar.");
            return;
        }

        if (confirmarBorrado("país", paisSeleccionado)) {
            try {
                paisRepository.eliminarPais(paisSeleccionado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El país '" + paisSeleccionado + "' ha sido eliminado.");
                cargarPaises(); // Recargar la lista
            } catch (Exception e) { // <-- CAMBIADO DE SQLException
                // El e.getMessage() ahora viene del repositorio con un error claro
                mostrarAlerta(Alert.AlertType.ERROR, "Error al Eliminar", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Maneja el evento de clic en el botón "Borrar Jugador".
     * (MODIFICADO: Se cambió 'catch (SQLException e)' por 'catch (Exception e)')
     */
    @FXML
    private void handleBorrarJugador() {
        String jugadorSeleccionado = jugadorCombBox.getValue();
        if (jugadorSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Vacía", "Por favor, seleccione un jugador para borrar.");
            return;
        }

        if (confirmarBorrado("jugador", jugadorSeleccionado)) {
            try {
                jugadorRepository.eliminarJugadorPorNombre(jugadorSeleccionado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El jugador '" + jugadorSeleccionado + "' ha sido eliminado.");
                cargarJugadores(); // Recargar la lista
            } catch (Exception e) { // <-- CAMBIADO DE SQLException
                // El e.getMessage() ahora viene del repositorio con un error claro
                mostrarAlerta(Alert.AlertType.ERROR, "Error al Eliminar", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Muestra un diálogo de confirmación. (Sin cambios)
     */
    private boolean confirmarBorrado(String tipo, String nombre) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar el " + tipo + " '" + nombre + "'?");
        alert.setContentText("Esta acción es irreversible.");
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
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