package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;

public class Moneta {

    private double valore; 
    private Shape forma;
    private Color colore; 

    public Moneta(double valore, Shape forma, Color colore) {
        this.valore = valore;
        this.forma = forma;
        this.colore = colore;
    }

    public double getValore() {
        return valore;
    }
    public Color getColore() {
        return colore;
    }
    public Shape getForma() {
        return forma;
    }


}