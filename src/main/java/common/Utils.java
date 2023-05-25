/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import main.Validadors;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author alex
 */
public class Utils {

    private static Media media = null;
    private static MediaPlayer player = null;
    public static boolean login = false;
    private static Scene scene;

    /**
     * *
     * Valida regles de negoci anotades (veure anotacions al BEAN +
     * https://javaee.github.io/tutorial/bean-validation002.html) i controla
     * transacciÃ³
     *
     * @param ob
     * @param userTransaction
     * @param em
     * @param log
     * @return
     * @throws Exception
     */
    public static Object persisteixAmbTransaccio(Object ob, UserTransaction userTransaction, EntityManager em, Logger log) throws Exception {
        List<String> errors = Validadors.validaBean(ob);

        if (errors.isEmpty()) {
            try {

                userTransaction.begin();
                em.persist(ob);
                userTransaction.commit();

            } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                String msg = "Error desant: " + errors.toString();
                log.log(Level.INFO, msg);
                throw new Exception(msg);
            }

        } else {
            String msg = "Errors de validaciÃ³: " + errors.toString();
            log.log(Level.INFO, msg);
            throw new Exception(msg);
        }

        return ob;
    }

    /**
     * Alerta de confirmación al salir de la app
     *
     * @param mensaje
     */
    public static void alertExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de confirmación...");
        alert.setHeaderText(null);
        alert.setContentText("¿Deseas salir del juego?");

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent()) {
            if (resultado.get() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }
    
    public static void alertTime() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("¡Tiempo finalizado!");
        alert.show();
    }
    
    /**
     * Alerta de aviso de login
     */
    public static void alertLogin() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("¡Debes hacer login antes de seguir en la app!");
        alert.showAndWait();
    }

    /**
     * Obtener día y hora actual con formato EU
     *
     * @return String
     */
    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }
    
    /**
     * Reproducir la música de fondo del juego indefinidamente
     */
    public static void playMusic() {
        
        // Ruta del archivo mp3
        String path = "src/main/resources/logica/music/soundtrack.mp3";
        //String path = "http://server/soundtrack.mp3";

        try {
            // Instanciar el archivo mp3 a través de la ruta
            File audio = new File(path);

            // Actualizamos el recurso MP3
            media = new Media(audio.toURI().toString());

            // Inicializamos el reproductor
            player = new MediaPlayer(media);

            // Configuraciones adicionales del reproductor
            player.setCycleCount(MediaPlayer.INDEFINITE); // Repetir la música de fondo indefinidamente
            player.setVolume(0.25); // Volumen (0.0 - 1.0)
            player.setStartTime(Duration.ZERO); // Iniciar desde el principio
            
            // Reproducir la música
            player.play();

        } catch (MediaException e) {

            System.out.println("ERROR abriendo el fichero: " + path + ":" + e.toString());
        }
    }
}
