/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * 
 * @author Alexandru
 */
@Entity
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nombre;
    
    @ManyToMany
    private List<Jugador> jugadores;
    
    private boolean partidaIniciada;

    private boolean partidaFinalizada;
    
    
    /*
        0  --> Fácil
        1 --> Medio
        2 --> Dificil
    */
    private int dificultad;
    
    
    public Partida() {
    }

    
    public Partida(Long id, String nombre, List<Jugador> jugadores, boolean partidaIniciada) {
        this.id = id;
        this.nombre = nombre;
        this.jugadores = jugadores;
        this.partidaIniciada = partidaIniciada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public void setPartidaIniciada(boolean partidaIniciada) {
        this.partidaIniciada = partidaIniciada;
    }

    
    

}
