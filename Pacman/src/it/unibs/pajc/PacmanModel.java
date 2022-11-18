package it.unibs.pajc;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Float;
import java.util.ArrayList;

public class PacmanModel {

	private Path2D mappa = new Path2D.Float();
	private ArrayList<Ostacolo> elencoOstacoli = new ArrayList<>();
	
	private AffineTransform at = new AffineTransform();
	private AffineTransform atSimmetriaY = new AffineTransform();
	
	public PacmanModel() {
		at.scale(1, -1);
		at.translate(-500., -500.); 
		atSimmetriaY.translate(1000, 0);
		atSimmetriaY.scale(-1, 1);
		//

		creaMappa(); 
		creaOstacoli();
	}
	
	private void creaMappa() {
		//mappa creata con origine in alto a sinistra
		
		/*
		variazione smussamento angolo piccolo = 3 coordinate
		punto di controllo variazione piccola (guardare 3 e 4 parametri) = mappa.curveTo(184, 425, 186, 425, 187, 422);
			curva da sx verso l'alto
		
		variazione smussamento angolo piccolo = 9 coordinate
		punto di controllo variazione grande (guardare 3 e 4 parametri) = mappa.curveTo(186, 434, 195, 432, 197, 424);
			curva da sx verso l'alto
		*/
		
		int rientranzaBordi = 5;
		
		creaPorzioneMappaSuperiore();
		creaPorzioneMappaInferiore();
		
		mappa.append(mappa.createTransformedShape(atSimmetriaY), false);
	}
	
	enum DirezioneCurva{
		ORIZZONTALE,
		VERTICALE;
		
	}
	
	private void curva(Path2D path, double x1, double y1, double x2, double y2, DirezioneCurva direzione) {
		switch(direzione) {
		case ORIZZONTALE:
			path.curveTo(x1, y1, x2, y1, x2, y2);
			break;
		case VERTICALE:
			path.curveTo(x1, y1, x1, y2, x2, y2);
			break;
		}
	}
	
	private void creaPorzioneMappaSuperiore() {
		//meta margine in alto esterno
		mappa.moveTo(5, 425);
		mappa.lineTo(184, 425);
		curva(mappa, 184, 425, 187, 422, DirezioneCurva.ORIZZONTALE);
		//mappa.curveTo(184, 425, 187, 425, 187, 422);
		
		mappa.lineTo(187, 324);
		curva(mappa, 187, 324, 184, 321, DirezioneCurva.VERTICALE);
		//mappa.curveTo(187, 324, 187, 321, 184, 321);
		//mappa.lineTo(184, 321);
		
		mappa.lineTo(15, 321);
		mappa.lineTo(5 , 311);
		mappa.lineTo(5 , 16);
		mappa.lineTo(15 , 6);
		mappa.lineTo(500, 6);
		
		//meta margine in alto interno
		mappa.moveTo(5, 434);
		mappa.lineTo(187, 434);
		curva(mappa, 187, 434, 196, 425, DirezioneCurva.ORIZZONTALE);
		
		mappa.lineTo(196, 322);
		curva(mappa, 196, 322, 187, 312, DirezioneCurva.VERTICALE);
		//mappa.lineTo(187, 312);
		mappa.lineTo(20, 312);
		mappa.lineTo(14, 306);
		mappa.lineTo(14, 21);
		mappa.lineTo(20, 16);
		mappa.lineTo(472, 16);
		mappa.lineTo(480, 25);
		mappa.lineTo(480, 135);
		mappa.lineTo(489, 146);
		mappa.lineTo(500, 146);
		
	}
	
	private void creaPorzioneMappaInferiore() {
		//meta margine in basso esterno
			mappa.moveTo(5, 513);
			mappa.lineTo(184, 513);
			mappa.lineTo(187, 516);
			mappa.lineTo(187, 614);
			mappa.lineTo(184 , 617);
			mappa.lineTo(15 , 617);
			mappa.lineTo(5, 627);
			mappa.lineTo(5, 984);
			mappa.lineTo(15, 994);
			mappa.lineTo(500, 994);
			
			//meta margine in basso interno
			mappa.moveTo(5, 503);
			mappa.lineTo(186, 503);
			mappa.lineTo(197, 513);
			mappa.lineTo(197, 617);
			mappa.lineTo(186, 627);
			mappa.lineTo(19, 627);
			mappa.lineTo(16, 631);
			mappa.lineTo(16, 784);
			mappa.lineTo(24, 793);
			mappa.lineTo(78, 793);
			mappa.lineTo(88, 803);
			mappa.lineTo(88, 811);
			mappa.lineTo(78, 821);
			mappa.lineTo(24, 821);
			mappa.lineTo(16, 829);
			mappa.lineTo(16, 980);
			mappa.lineTo(21, 984);
			mappa.lineTo(500, 984);
	}
	
	public Shape getMappa() {
		return at.createTransformedShape(mappa);
	}
	
	public ArrayList<Ostacolo> getElencoOstacoli() {
		return elencoOstacoli;
	}
	
	private void creaOstacoli() {
		
		creaOstacolo1();
		creaOstacolo2();
		creaOstacolo3();
		creaOstacoliSimmetrici();
		
	}
	
	private void creaOstacolo1() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(82, 95);
		forma.lineTo(92, 85);
		forma.lineTo(186, 85);
		forma.lineTo(196, 95);
		forma.lineTo(196, 136);
		forma.lineTo(186, 146);
		forma.lineTo(92, 146);
		forma.lineTo(82, 136);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma)));
	}
	
	private void creaOstacolo2() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(262, 95);
		forma.lineTo(272, 85);
		forma.lineTo(403, 85);
		forma.lineTo(413, 95);
		forma.lineTo(413, 136);
		forma.lineTo(403, 146);
		forma.lineTo(272, 146);
		forma.lineTo(262, 136);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma)));
	}
	
	private void creaOstacolo3() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(82, 222);
		forma.lineTo(92, 212);
		forma.lineTo(186, 212);
		forma.lineTo(196, 222);
		forma.lineTo(196, 231);
		forma.lineTo(186, 241);
		forma.lineTo(92, 241);
		forma.lineTo(82, 231);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma)));
	}
	
	private void creaOstacoliSimmetrici() {
		int numeroOstacoliIniziale = elencoOstacoli.size();
		AffineTransform atInversa = null;
		try {
			atInversa = at.createInverse();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		for(int i = 0; i<numeroOstacoliIniziale;i++) {
			Shape forma = atInversa.createTransformedShape(elencoOstacoli.get(i).getShape());
			Shape formaSimmetrica = atSimmetriaY.createTransformedShape(forma);
			elencoOstacoli.add(new Ostacolo(at.createTransformedShape(formaSimmetrica)));
			
		}
	}
	
}
