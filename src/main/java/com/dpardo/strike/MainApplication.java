package com.dpardo.strike;

import com.dpardo.strike.util.HibernateUtil; // <-- ¡NUEVO IMPORT!
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/dpardo/strike/ui/login/Login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 600);
        stage.setTitle("strike - login");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * ¡MÉTODO AÑADIDO!
     * Este método es llamado automáticamente por JavaFX cuando
     * se cierra la ventana principal.
     * Es el lugar perfecto para cerrar la conexión de Hibernate.
     */
    @Override
    public void stop() throws Exception {
        System.out.println("Cerrando la aplicación... Apagando Hibernate.");
        HibernateUtil.shutdown(); // Cierra el EntityManagerFactory
        super.stop();
    }

    // (main sin cambios)
    public static void main(String[] args) {
        launch();
    }
}