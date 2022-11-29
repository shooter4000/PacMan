package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.Timer;


public class Pacman extends Personaggio{
	private static final double ANGOLO_APERTURA_MAX = 45;
	private double angolo=0;
	private static double DELTA_ANGOLO = 3;
	private boolean isClosing = true;
	
	public Pacman(Color colore) {
		super(colore, new Point2D.Float(0, 0), Direzioni.DESTRA);
		setForma(costruisciPacman());
		
	}
	
	//70
	private Arc2D costruisciPacman() {
		Arc2D cerchio = new Arc2D.Float();
		Direzioni direzione = getDirezione();
		double angoloRotazioneApertura = getDirezione().getAngolo();
		if(direzione == Direzioni.SOPRA || direzione == Direzioni.SOTTO) {
			angoloRotazioneApertura = getDirezione().getAngolo() + 180;
		}else {
			angoloRotazioneApertura = getDirezione().getAngolo(); 
		}		
		cerchio.setArcByCenter(getPosCentro().getX(), getPosCentro().getY(), 35,ANGOLO_APERTURA_MAX-angolo + angoloRotazioneApertura,
				360-(ANGOLO_APERTURA_MAX - angolo)*2, Arc2D.PIE);
		return cerchio;
	}

	public void stepNext() {
		cambiaAngolo();
		muoviPacman();
	}
	
	private void muoviPacman() {
		Direzioni direzione = getDirezione();
		Point2D posCentro = getPosCentro();
		posCentro.setLocation(posCentro.getX() + getVelocita() * direzione.getVersoreX(), posCentro.getY() + getVelocita() * direzione.getVersoreY());
		setPosCentro(posCentro);
		setForma(costruisciPacman());
		
	}

	private void cambiaAngolo() {
		if(isClosing) {
			this.angolo += DELTA_ANGOLO;
			if(Math.abs(ANGOLO_APERTURA_MAX - angolo)<=0.001 )
				isClosing=false;
		}
		else {
			this.angolo -=DELTA_ANGOLO;
			if(Math.abs(0 - angolo)<=0.001 )
				isClosing=true;
	
			
		}
		
		setForma(costruisciPacman());
	}

	public void cambiaDirezione(Direzioni direzione) {
		setDirezione(direzione);
		
	}

}
