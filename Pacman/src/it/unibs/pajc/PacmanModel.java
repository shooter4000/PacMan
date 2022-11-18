package it.unibs.pajc;

import java.awt.geom.AffineTransform;

public class PacmanModel {

	private Mappa mappa;
	
	private AffineTransform at = new AffineTransform();
	
	
	public PacmanModel() {
		at.scale(1, -1);
		at.translate(-500., -500.); 
		
		mappa = new Mappa(at);
		
	}
	
	public Mappa getMappa() {
		return mappa;
	}
	
	
}
