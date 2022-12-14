package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class Personaggio {
	private Forma formaComputazionale;
	private ArrayList<Forma> formaGrafica = new ArrayList<>();
	private ArrayList<Forma> formaGraficaDebug = new ArrayList<>();
	private Point2D posCentro;
	private Direzioni direzione;
	private Direzioni direzioneProssima;
	private int velocita = 3;
	protected final static int SIZE_FORMA_COMPUTAZIONALE = 70;
	protected final static int SIZE_FORMA_GRAFICA = 60;
	
	public Personaggio(Point2D posCentro, Direzioni direzione) {
		this.posCentro = posCentro;
		this.direzione = direzione;
	}
	
	public Forma getFormaComputazionale() {
		return formaComputazionale;
	}
	
	public void setFormaComputazionale(Forma forma) {
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
	
	public ArrayList<Forma> getFormaGraficaDebug() {
		return formaGraficaDebug;
	}
	
	protected void resettaFormaGraficaDebug() {
		formaGraficaDebug.clear();
	}
	
	public void addFormaGraficaDebug(Forma forma) {
		this.formaGraficaDebug.add(forma);
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
	public void setVelocita(int velocita) {
		this.velocita = velocita;
	}
	
	public Direzioni getDirezione() {
		return direzione;
	}
	
	public void setDirezione(Direzioni direzione) {
		this.direzione = direzione;
	}
	
	public Direzioni getDirezioneProssima() {
		return direzioneProssima;
	}
	
	public void setDirezioneProssima(Direzioni direzione) {
		direzioneProssima = direzione;
	
	}
	
	public abstract void stepNext();
	
	public Shape simulaProssimaPosizione(Direzioni direzione) {
		Point2D posCentro = (Point2D)getPosCentro().clone();
		posCentro.setLocation(posCentro.getX() + /*getVelocita() * */ direzione.getVersoreX(), posCentro.getY() + /*getVelocita() **/ direzione.getVersoreY());
		return new Rectangle2D.Float((float)(posCentro.getX()-SIZE_FORMA_COMPUTAZIONALE/2.),
				(float)(posCentro.getY()- SIZE_FORMA_COMPUTAZIONALE/2.), (float)SIZE_FORMA_COMPUTAZIONALE, (float)SIZE_FORMA_COMPUTAZIONALE);
	}
}
