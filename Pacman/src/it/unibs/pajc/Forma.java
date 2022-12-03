package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;

public class Forma {

	private Shape forma;
	private Color colore;
	
	public Forma(Shape forma, Color colore) {
		this.forma = forma;
		this.colore = colore;
	}
	
	public Shape getForma() {
		return forma;
	}
	
	public Color getColore() {
		return colore;
	}
}
