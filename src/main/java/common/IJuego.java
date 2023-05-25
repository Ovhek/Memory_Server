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
    public Partida empezarPartida() throws PartidaException;
    
    /***
     * Sale de la partida si esta no ha iniciado.
     * @throws common.PartidaException si el jugador se encuentra en una partida.
     */
    public void salirDePartida(Partida partida) throws PartidaException;
    
        /***
     * Ve el historial de partidas.
     * @param idJugador el id del jugador.
     * @return lista con todas los historiales de partidas.
     */
    public List<Partida> verHistorial(int idJugador);
    
    /**
     * Actualiza una partida en la BD
     * @param partida partida a guardar
     */
    public void actualizarPartida(Partida partida);
    
    
        /***
     * Empieza una partida
     * @param partida a empezar
     * @throws common.PartidaException si la partida ya está empezada.
     */
    public void empezarPartida(Partida partida) throws PartidaException;
    
    /***
     * Termina una partida
     * @param partida
     * @throws common.PartidaException si la partida ya está terminada.
     */
    public void terminarPartida(Partida partida) throws PartidaException;
    
    /***
     * Crea un mazo de cartas y lo mezcla
     * @return Mazo de cartas de la partida
     */
    public MazoDeCartas mezclarCartas();
    
    /**
     * Comprueba si dos cartas coinciden
     * @param primera primera carta
     * @param segunda segunda carta
     * @return si coinciden o no.
     */
    public boolean cartasConciden(Carta primera, Carta segunda);
    
    /**
     * Aumenta el contador de intentos.
     * @param anteriorNumero el numero de intentos anterior.
     * @return el numero de intentos +1
     */
    public int sumarIntentos(int anteriorNumero);
    
    /**
     * Voltea una carta según el indice
     * @param carta carta a voltear
     * @return 
     */
    public Image voltearCarta(Carta carta);
    
    /***
     * Sale de la partida si esta no ha iniciado.
     * @throws common.PartidaException si el jugador se encuentra en una partida.
     */
    //public int obtenerSegundosRestantes() throws PartidaException;
    
}
