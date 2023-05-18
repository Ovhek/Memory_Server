/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author alexandru
 */
@Remote
public interface IHistorialPartida {
    
    /***
     * Ve el historial de partidas.
     * @param idJugador el id del jugador.
     * @return lista con todas los historiales de partidas.
     */
    public List<HistorialPartida> verHistorial(int idJugador);
    
    /**
     * Guarda un historial de partial a los historiales de partidas
     * @param historial historial a guardar
     */
    public void guardarHistorial(HistorialPartida historial);
    
    
}
