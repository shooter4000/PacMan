package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;

public abstract class Personaggio {
	private Shape forma;
	private Color colore;
	private Point2D posCentro;
	private Direzioni direzione;
	private static double VELOCITA = 2;
	public Personaggio(Color colore, Point2D posCentro, Direzioni direzione) {
		this.colore = colore;
		this.posCentro = posCentro;
		this.direzione = direzione;
	}
	public Shape getForma() {
		return forma;
	}
	
	public void setForma(Shape forma) {
		this.forma = forma;
	}	
	
	public Point2D getPosCentro() {
		return posCentro;
	}
	
	public void setPosCentro(Point2D posCentro) {
		this.posCentro = posCentro;
	}
	
	public double getVelocita() {
		return VELOCITA;
	}
	
	public Direzioni getDirezione() {
		return direzione;
	}
	
	public void setDirezione(Direzioni direzione) {
		this.direzione = direzione;
	}
	
	public abstract void stepNext();
	
}
