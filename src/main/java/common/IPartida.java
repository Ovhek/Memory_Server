/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javafx.scene.image.Image;
import javax.ejb.Remote;

/**
 *
 * @author alexandru
 */
@Remote
public interface IPartida {
    
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
