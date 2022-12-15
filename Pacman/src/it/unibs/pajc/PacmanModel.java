package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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
		elencoPersonaggi.add(new Fantasma(Color.red, new Point2D.Float(100, -20), Direzioni.SOPRA));
		elencoPersonaggi.add(new Fantasma(Color.orange, new Point2D.Float(30, -20), Direzioni.DESTRA));
		elencoPersonaggi.add(new Fantasma(Color.pink, new Point2D.Float(-40, -20), Direzioni.SOTTO));
		elencoPersonaggi.add(new Fantasma(Color.cyan, new Point2D.Float(-110, -20), Direzioni.SINISTRA));
	}
	
	public void stepNext() {
		for(int i = 0 ; i < mappa.getMonete().size(); i++) {
			if(mappa.getMonete().get(i).getFormaComputazionale().getForma().intersects(pacman.getRettangoloCentrale().getForma().getBounds2D())) {
				mappa.getMonete().remove(i);
				break;
			}
					
		}
		
		elencoPersonaggi
			.stream()
			.parallel()
			.forEach(p -> {
				for(int i=1;i<=p.getVelocita();i++) {
					if(p.getDirezioneProssima()!=null && !mappa.interseca(p.simulaProssimaPosizione(p.getDirezioneProssima()))) {
						p.setDirezione(p.getDirezioneProssima());
					}
					
					if(!mappa.interseca(p.simulaProssimaPosizione(p.getDirezione())))
						p.stepNext();
				}
			});
	}
	


}
