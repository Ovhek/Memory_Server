/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Alexandru
 */
public class Carta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String palo;

    private String cara;

    public Carta() {
    }

    public Carta(String palo, String cara) {
        this.palo = palo;
        this.cara = cara;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) throws CartaException {
        palo = palo.toLowerCase();
        if (getPalosValidos().contains(palo)) {
            this.palo = palo;
        } else {
            throw new CartaException(palo + ": Inválido, debe ser una de " + getPalosValidos());
        }

    }

    public String getCara() {
        return cara;
    }

    /**
     * caras válidas:
     * "2","3","4","5","6","7","8","9","10","jack","queen","king","ace"
     *
     * @param cara string que representa la cara
     */
    public void setCara(String cara) throws CartaException {
        cara = cara.toLowerCase();
        if (getCarasValidas().contains(cara)) {
            this.cara = cara;
        } else {
            throw new CartaException(cara + ": No es válida debe ser una de: " + getCarasValidas());
        }
    }

    public static List<String> getPalosValidos() {
        return Arrays.asList("hearts","diamonds","clubs","spades");
    }
    
    public static List<String> getCarasValidas()
    {
        return Arrays.asList("2","3","4","5","6","7","8","9","10","jack","queen","king","ace");
    }

    public String toString()
    {
        return cara + " of " + palo;
    }

    public String getColour()
    {
        if (cara.equals("hearts") || palo.equals("diamonds"))
            return "red";
        else
            return "black";
    }

    /**
     * Devuelve el valor de la carta
     * [ "2","3","4","5","6","7","8","9","10","jack","queen","king","ace" ]
     *   0    1   2   3   4  ....                              11    12
     *   +2
     *
     */
    public int getValue()
    {
        return getCarasValidas().indexOf(cara) + 2;
    }

    /**
     * This method will return an Image that represents the Card
     */
    public Image getImage()
    {       
        try {
            return new Image(Carta.class.getClassLoader().getResource("images/"+cara+"_of_"+palo+".png").openStream());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Image getBackOfCardImage()
    {
        return new Image(Carta.class.getResourceAsStream("images/back_of_card.png"));
    }
}
