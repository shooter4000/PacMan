package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;

public class Moneta {

    private int valore; 
    private Shape forma;
    private Color colore; 

    public Moneta(int valore, Shape forma, Color colore) {
        this.valore = valore;
        this.forma = forma;
        this.colore = colore;
    }

    public int getValore() {
        return valore;
    }
    public Color getColore() {
        return colore;
    }
    public Shape getForma() {
        return forma;
    }


}