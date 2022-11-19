package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;

public class Ostacolo {

	private Shape forma;
	private Color colore;
	
	public Ostacolo(Shape forma, Color colore) {
		this.forma = forma;
		this.colore = colore;
	}
	
	public Shape getShape() {
		return forma;
	}
	
	public Color getColore() {
		return colore;
	}
	
	
}
