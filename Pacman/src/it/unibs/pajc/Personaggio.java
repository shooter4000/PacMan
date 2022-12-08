package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Personaggio {
	private Shape formaComputazionale;
	private ArrayList<Forma> formaGrafica = new ArrayList<>();
	private Point2D posCentro;
	private Direzioni direzione;
	private double velocita = 2;
	protected final static int SIZE_FORMA_COMPUTAZIONALE = 68;
	protected final static int SIZE_FORMA_GRAFICA = 60;
	
	public Personaggio(Point2D posCentro, Direzioni direzione) {
		this.posCentro = posCentro;
		this.direzione = direzione;
	}
	
	public Shape getFormaComputazionale() {
		return formaComputazionale;
	}
	
	public void setFormaComputazionale(Shape forma) {
		this.formaComputazionale = forma;
	}	
	
	public ArrayList<Forma> getFormaGrafica() {
		return formaGrafica;
	}
	
	protected void resettaFormaGrafica() {
		formaGrafica.clear();
	}
	
	public void addFormaGrafica(Forma forma) {
		this.formaGrafica.add(forma);
	}
	
	public Point2D getPosCentro() {
		return posCentro;
	}
	
	public void setPosCentro(Point2D posCentro) {
		this.posCentro = posCentro;
	}
	
	public double getVelocita() {
		return velocita;
	}
	public void setVelocita(double velocita) {
		this.velocita = velocita;
	}
	
	public Direzioni getDirezione() {
		return direzione;
	}
	
	public void setDirezione(Direzioni direzione) {
		this.direzione = direzione;
	}
	
	public abstract void stepNext();
	
	public abstract Shape simulaProssimaPosizione();
}
