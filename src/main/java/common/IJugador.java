/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javax.ejb.Remote;

/**
 *
 * @author alexandru
 */
@Remote
public interface IJugador {
    
    /***
     * Valida el client
     * @param login
     * @return id de sesión
     * @throws common.JugadorException si el jugador no existe
     */
    public Jugador getSesion(Jugador jugador) throws JugadorException;
    
    /**
     * Registra un jugador en la DB
     * @param jugador jugador a registrar
     */
    public Jugador registrarUsuario(Jugador jugador) throws Exception;
    
    /***
     * Cierra la sesión en curso.
     */
    public void cerrarSesion();
    
    /***
     * Une un jugador a una partida
     * @param partida partida a la cual se ha de unir.
     * @throws common.PartidaException si la partida esta llena.
     */
    public void unirseAPartida(Partida partida) throws PartidaException;
    
    /***
     * Sale de la partida si esta no ha iniciado.
     * @throws common.PartidaException si el jugador se encuentra en una partida.
     */
    public void salirDePartida(Partida partida) throws PartidaException;
}
