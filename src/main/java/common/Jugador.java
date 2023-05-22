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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 
 * @author Alexandru
 */
@Entity
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "El nombre del jugador no puede estar vacío.")
    @Size(min = 3, max = 10, message = "La longitud ha de estar entre 3 y 10 caracteres")
    private String nombre;

    @Email(message = "El email no es válido")
    private String email;
    
    @OneToMany(mappedBy = "jugador")
    private List<HistorialPartida> historialPartidas;
    
    public Jugador() {
    }

    public Jugador(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Jugador{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", email=").append(email);
        sb.append(", historialPartidas=").append(historialPartidas);
        sb.append('}');
        return sb.toString();
    }
    
}
