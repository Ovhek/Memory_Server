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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

/**
 * 
 * @author Alexandru
 */
@Entity
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotEmpty(message = "El nombre del jugador no puede estar vacío.")
    private String nombre;

    @OneToMany(mappedBy = "jugador")
    private List<HistorialPartida> historialPartidas;
    
    public Jugador() {
    }

    public Jugador(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public List<HistorialPartida> getHistorialPartidas() {
        return historialPartidas;
    }

    public void setHistorialPartidas(List<HistorialPartida> historialPartidas) {
        this.historialPartidas = historialPartidas;
    }

    
   

    
}
