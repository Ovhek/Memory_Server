/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

/**
 *
 * @author Alexandru
 */
public class CartaMemory extends Carta {

    private boolean emparejada;
    private boolean girada;

    public CartaMemory(String palo, String cara) {
        super(palo, cara);
        this.emparejada = false;
    }

    public boolean isEmparejada() {
        return emparejada;
    }

    public void setMatched(boolean emparejada) {
        this.emparejada = emparejada;
    }

    /**
     * Devuelve verdadero si dos cartas tienen el mismo palo y cara.
     * @param otraCarta
     * @return 
     */
    public boolean isMismaCarta(CartaMemory otraCarta) {
        return (this.getPalo().equals(otraCarta.getPalo())
                && (this.getCara().equals(otraCarta.getCara())));
    }

    public boolean isGirada() {
        return girada;
    }

    public void setGirada(boolean girada) {
        this.girada = girada;
    }

    
}
