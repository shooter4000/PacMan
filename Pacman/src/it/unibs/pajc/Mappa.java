package it.unibs.pajc;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Mappa {

	//mappa creata con origine in alto a sinistra
	
	/*
	variazione smussamento angolo piccolo = 3 coordinate		
	variazione smussamento angolo piccolo = 9 coordinate
	distanza tra le due righe del bordo = 9 coordinate
	larghezza strade per il passaggio dei personaggi = 70 coordinate
	*/
	
	private AffineTransform at;
	private AffineTransform atSimmetriaY = new AffineTransform();
	private Path2D bordi = new Path2D.Float();
	private List<Ostacolo> elencoOstacoli = new ArrayList<>();
	
	public Mappa(AffineTransform at) {
		this.at = at;
		atSimmetriaY.translate(1000, 0);
		atSimmetriaY.scale(-1, 1);
		creaMappa();
		creaOstacoli();
	}
	
	public Shape getBordi() {
		return at.createTransformedShape(bordi);
	}
	
	public List<Ostacolo> getElencoOstacoli() {
		return elencoOstacoli;
	}
	
	private void creaMappa() {
		creaPorzioneMappaSuperiore();
		creaPorzioneMappaInferiore();
		
		bordi.append(bordi.createTransformedShape(atSimmetriaY), false);
	}
	
	private enum DirezioneCurva{
		//indica la direzione iniziale prima di percorrere la curva
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
		//meta' margine in alto esterno
		bordi.moveTo(5, 425);
		bordi.lineTo(184, 425);
		curva(bordi, 184, 425, 187, 422, DirezioneCurva.ORIZZONTALE);		
		bordi.lineTo(187, 324);
		curva(bordi, 187, 324, 184, 321, DirezioneCurva.VERTICALE);
		bordi.lineTo(14, 321);
		curva(bordi, 14, 321, 5, 312, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(5 , 16);
		curva(bordi, 5, 16, 14, 7, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 7);
		
		//meta' margine in alto interno
		bordi.moveTo(5, 434);
		bordi.lineTo(187, 434);
		curva(bordi, 187, 434, 196, 425, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(196, 323);
		curva(bordi, 196, 323, 187, 312, DirezioneCurva.VERTICALE);
		bordi.lineTo(23, 312);
		curva(bordi, 23, 312, 14, 303, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(14, 21);
		curva(bordi, 14, 21, 17, 16, DirezioneCurva.VERTICALE);
		bordi.lineTo(472, 16);
		curva(bordi, 472, 16, 481, 25, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(481, 135);
		curva(bordi, 481, 135, 490, 143, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 143);
		
	}
	
	private void creaPorzioneMappaInferiore() {
		//meta' margine in basso esterno
		bordi.moveTo(5, 513);
		bordi.lineTo(184, 513);
		curva(bordi, 184, 513, 187, 516, DirezioneCurva.ORIZZONTALE);	
		bordi.lineTo(187, 614);
		curva(bordi, 187, 614, 184, 617, DirezioneCurva.VERTICALE);
		bordi.lineTo(14, 617);
		curva(bordi, 14, 617, 5, 626, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(5, 984);
		curva(bordi, 5, 984, 14, 993, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 993);
			
		//meta' margine in basso interno
		bordi.moveTo(5, 504);
		bordi.lineTo(187, 504);
		curva(bordi, 187, 504, 196, 513, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(196, 617);
		curva(bordi, 196, 617, 187, 626, DirezioneCurva.VERTICALE);
		bordi.lineTo(19, 626);
		curva(bordi, 19, 626, 14, 629, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(14, 784);
		curva(bordi, 14, 784, 23, 793, DirezioneCurva.VERTICALE);
		bordi.lineTo(78, 793);
		curva(bordi, 78, 793, 87, 802, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(87, 811);
		curva(bordi, 87, 811, 78, 820, DirezioneCurva.VERTICALE);
		bordi.lineTo(23, 820);
		curva(bordi, 23, 820, 14, 829, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(14, 981);
		curva(bordi, 14, 981, 19, 984, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 984);
	}
	
	
	
	private void creaOstacoli() {
		creaOstacoloRettangolare(new RoundRectangle2D.Float(84, 86, 111, 59, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(265, 86, 146, 59, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(84, 215, 111, 27, 18, 18));
		creaOstacoliSimmetrici();
		
	}
	
	private void creaOstacoloRettangolare(RoundRectangle2D forma) {
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
