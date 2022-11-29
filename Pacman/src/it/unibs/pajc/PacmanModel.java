package it.unibs.pajc;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class PacmanModel {

	private Mappa mappa;
	private ArrayList<Personaggio> elencoPersonaggi = new ArrayList<>();
	private AffineTransform at = new AffineTransform();
	private Pacman pacman;
	
	public PacmanModel() {
		at.scale(1, -1);
		at.translate(-500., -500.); 
		
		mappa = new Mappa(at);
		creaPersonaggi();
		
	}
	
	public ArrayList<Personaggio> getElencoPersonaggi() {
		return elencoPersonaggi;
	}
	
	public Mappa getMappa() {
		return mappa;
	}
	
	public Pacman getPacman() {
		return pacman;
	}
	
	private void creaPersonaggi() {
		pacman = new Pacman(Color.yellow);
		elencoPersonaggi.add(pacman);
	}
	
	public void stepNext() {
		for(Personaggio p: elencoPersonaggi) {
			p.stepNext();
		}
	}
	
}
