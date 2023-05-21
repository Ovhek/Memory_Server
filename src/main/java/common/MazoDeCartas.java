/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Alexandru
 */
public class MazoDeCartas implements Serializable {

    private static final long serialVersionUID = 1L;
    private int maximoCartas = 13;
    private ArrayList<CartaMemory> mazo;

    int contador = 0;
    public MazoDeCartas() {
        this.mazo = new ArrayList<>();
        List<String> suits = Carta.getPalosValidos();
        List<String> faceNames = Carta.getCarasValidas();

        for (String suit : suits)
        {
            for (String faceName : faceNames)
            {
                if(contador == maximoCartas) break;
                mazo.add(new CartaMemory(suit,faceName));
                mazo.add(new CartaMemory(suit,faceName));
                contador++;
            }
        }
    }

    /**
     * función que se encarga de randomizar el mazo
     */
    public void mezclar()
    {
        Collections.shuffle(mazo);
    }

    /**
     * obtiene la primera carta del mazo
     * If the deck is empty, it will return null
     * @return 
     */
    public Carta getPrimeraCartaMazo()
    {
        if (!mazo.isEmpty())
            return mazo.remove(0);
        else
            return null;
    }

    /**
     * Devuelve el número de cartas-
     * @return 
     */
    public int getNumeroCartas()
    {
        return mazo.size();
    }
}
