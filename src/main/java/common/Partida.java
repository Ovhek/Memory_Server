/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * 
 * @author Alexandru
 */
@Entity
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_jugador")
    private Jugador jugador;
    
    private boolean partidaIniciada;

    private boolean partidaFinalizada;
    
    private int numIntentos;
    
    private Date tiempoRestante;
    
    /*
        0  --> Fácil
        1 --> Medio
        2 --> Dificil
    */
    private int dificultad;
    
    
    public Partida() {
    }

    public Partida(Jugador jugador, int dificultad) {
        this.jugador = jugador;
        this.partidaIniciada = false;
        this.partidaFinalizada = false;
        this.dificultad = dificultad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public void setPartidaIniciada(boolean partidaIniciada) {
        this.partidaIniciada = partidaIniciada;
    }

    public boolean isPartidaFinalizada() {
        return partidaFinalizada;
    }

    public void setPartidaFinalizada(boolean partidaFinalizada) {
        this.partidaFinalizada = partidaFinalizada;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public int getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(int numIntentos) {
        this.numIntentos = numIntentos;
    }

    public Date getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(Date tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

}
