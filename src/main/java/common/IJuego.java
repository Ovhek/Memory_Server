/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.List;
import javafx.scene.image.Image;
import javax.ejb.Remote;

/**
 *
 * @author alexandru
 */
@Remote
public interface IJuego {
    
    /***
     * Valida el client
     * @param jugador
     * @return id de sesión
     * @throws common.JugadorException si el jugador no existe
     */
    public Jugador getSesion(Jugador jugador) throws JugadorException;
    
    /**
     * Registra un jugador en la DB
     * @param jugador jugador a registrar
     * @return jugador registrado
     * @throws java.lang.Exception 
     */
    public Jugador registrarUsuario(Jugador jugador) throws Exception;
    
    /***
     * Cierra la sesión en curso.
     */
    public void cerrarSesion();
    
    /***
     * empieza una partida
     * @return La partida comenzada
     * @throws common.PartidaException si la partida esta llena.
     */
    public Partida empezarPartida(Partida partida) throws PartidaException;

        /***
     * Ve el historial de partidas.
     * @param idJugador el id del jugador.
     * @return lista con todas los historiales de partidas.
     */
    public List<Partida> verHistorial(int idJugador);
    
    /***
     * Termina una partida
     * @param partida
     * @throws common.PartidaException si la partida ya está terminada.
     */
    public Partida terminarPartida() throws Exception;
    
    /***
     * Crea un mazo de cartas y lo mezcla
     * @return Mazo de cartas de la partida
     */
    public MazoDeCartas obtenerMazoMezclado();
    
    /**
     * Comprueba si las dos cartas que estan en el servidor coinciden
     * @return si coinciden o no.
     */
    public boolean cartasConciden();
    
    /**
     * Aumenta el contador de intentos.
     * @return el numero de intentos +1
     * @throws common.PartidaException
     */
    public int sumarIntentos() throws Exception; 
    
    /**
     * Voltea una carta según el indice
     * @param carta carta a voltear
     * @return 
     */
    public Image voltearCarta(CartaMemory carta);
    
    /**
     * Función encargada de obtener el salón de la fama.
     * @return lista de partidas ordenadas de mayor a menor
     */
    public List<Partida> getHallOfGame() throws Exception;
    
    /**
     * Obtiene el tiempo actual de la partida
     * @return tiempo actual de la partida.
     */
    public int obtenerTiempoPartida();
}
