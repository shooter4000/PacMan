package it.unibs.pajc;

import java.awt.Shape;

public class Ostacolo {

	private Shape forma;
	
	public Ostacolo(Shape forma) {
		this.forma = forma;
	}
	
	public Shape getShape() {
		return forma;
	}
	
	
}
