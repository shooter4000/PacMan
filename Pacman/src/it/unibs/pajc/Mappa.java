package it.unibs.pajc;

import java.awt.Color;
import java.awt.Point;
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
	
	private Color coloreBordi = new Color(83, 88, 254); 
	private Color coloreOstacoli = new Color(83, 88, 254);
	private Color coloreCancello = new Color(240, 176, 233);
	
	public Mappa(AffineTransform at) {
		this.at = at;
		atSimmetriaY.translate(1000, 0);
		atSimmetriaY.scale(-1, 1);
		creaMappa();
		
		creaOstacoli();
	}
	
	public Shape getBordi() {
		return bordi;
	}
	
	public List<Ostacolo> getElencoOstacoli() {
		return elencoOstacoli;
	}
	
	public Color getColoreBordi() {
		return coloreBordi;
	}
	
	private void creaMappa() {
		creaPorzioneMappaSuperiore();
		creaPorzioneMappaInferiore();
		
		bordi.append(bordi.createTransformedShape(atSimmetriaY), false);
		
		bordi = (Path2D)at.createTransformedShape(bordi);
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
		creaOstacoloRettangolare(new RoundRectangle2D.Float(264, 504, 41, 122, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(264, 696, 148, 26, 18, 18));
		creaOstacoloTRuotata();
		creaOstacoloLRovesciata();
		creaOstacoloManganello();
		
		creaOstacoliSimmetrici();
		
		creaOstacoloCentrale(new Point(379,215)); //da controllare distanza 70
		creaOstacoloCentrale(new Point(379,599));
		creaOstacoloCentrale(new Point(379,793));
		creaRettangoloCentrale();	
	}
	
	private void creaOstacoloRettangolare(RoundRectangle2D forma) {
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaOstacoloCentrale(Point partenza) {
		Path2D ostacolo = new Path2D.Float();
		ostacolo.moveTo(partenza.x, partenza.y);
		ostacolo.lineTo(partenza.x+241, partenza.y);
		curva(ostacolo, partenza.x+241,partenza.y, partenza.x+241+9,partenza.y+9,DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(partenza.x+250, partenza.y+18);
		curva(ostacolo, partenza.x+250,partenza.y+18, partenza.x+250-9,partenza.y+18+9,DirezioneCurva.VERTICALE);
		ostacolo.lineTo(partenza.x+241-92,partenza.y+27);
		curva(ostacolo, partenza.x+149, partenza.y+27, partenza.x+149-9, partenza.y+27+9,DirezioneCurva.ORIZZONTALE);		
		ostacolo.lineTo(partenza.x+140, partenza.y+36+79);
		curva(ostacolo, partenza.x+140, partenza.y+106+9, partenza.x+141-9, partenza.y+106+9+9,DirezioneCurva.VERTICALE);
		ostacolo.lineTo(481+9, partenza.y+124);
		curva(ostacolo, 481+9, partenza.y+124, 481, partenza.y+124-9, DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(481, partenza.y+27+9);
		curva(ostacolo, 481, partenza.y+27+9, 481-9, partenza.y+27, DirezioneCurva.VERTICALE);
		ostacolo.lineTo(partenza.x, partenza.y+27);
		curva(ostacolo,partenza.x, partenza.y+27,  partenza.x-9,partenza.y+16, DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(partenza.x-9, partenza.y+9);
		curva(ostacolo,partenza.x-9, partenza.y+9, partenza.x,partenza.y, DirezioneCurva.VERTICALE);
		ostacolo.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(ostacolo), coloreOstacoli));
	}
	
	private void creaOstacoloLRovesciata() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(84, 705);
		curva(forma, 84, 705, 93, 696, DirezioneCurva.VERTICALE);
		forma.lineTo(187, 696);
		curva(forma, 187, 696, 196, 705, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(196, 811);
		curva(forma, 196, 811, 185, 820, DirezioneCurva.VERTICALE);
		forma.lineTo(166, 820);
		curva(forma, 166, 820, 157, 811, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(157, 731);
		curva(forma, 157, 731, 148, 722, DirezioneCurva.VERTICALE);
		forma.lineTo(93, 722);
		curva(forma, 93, 722, 84, 713, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
		
	private void creaOstacoloTRuotata() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(265, 224);
		curva(forma, 265, 224, 274, 215, DirezioneCurva.VERTICALE); 
		forma.lineTo(297, 215);
		curva(forma, 297, 215, 306, 224, DirezioneCurva.ORIZZONTALE); 
		forma.lineTo(306, 303);
		curva(forma, 306, 312, 315, 312, DirezioneCurva.VERTICALE); 
		forma.lineTo(403, 312);
		curva(forma, 403, 312, 412, 321, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(412, 330);
		curva(forma, 412, 330, 403, 339, DirezioneCurva.VERTICALE);
		forma.lineTo(315, 339);
		curva(forma, 315, 339, 306, 348, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(306, 425);
		curva(forma, 306, 425, 297, 434, DirezioneCurva.VERTICALE);
		forma.lineTo(274, 434);
		curva(forma, 274, 434, 265, 425, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaOstacoloManganello() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(84,900);
		curva(forma, 84, 900, 93, 891, DirezioneCurva.VERTICALE);
		forma.lineTo(255, 891);
		curva(forma, 255, 891, 264, 882, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(264, 802);
		curva(forma, 264, 802, 273, 793, DirezioneCurva.VERTICALE);
		forma.lineTo(296, 793);
		curva(forma, 296, 793, 305, 802, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(305, 882);
		curva(forma, 305, 882, 314, 891, DirezioneCurva.VERTICALE);
		forma.lineTo(403, 891);
		curva(forma, 403, 891, 412, 900, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(412, 909);
		curva(forma, 412, 909, 403, 918, DirezioneCurva.VERTICALE);
		forma.lineTo(93, 918);
		curva(forma, 93, 918, 84, 909, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaRettangoloCentrale() {
		Path2D rettangoloEsterno = new Path2D.Float();
		rettangoloEsterno.moveTo(377, 411);
		rettangoloEsterno.lineTo(500-32-5, 411);
		rettangoloEsterno.moveTo(500+32+5, 411);
		rettangoloEsterno.lineTo(624, 411);
		rettangoloEsterno.lineTo(624, 528);
		rettangoloEsterno.lineTo(377, 528);
		rettangoloEsterno.lineTo(377, 411);
		
		Path2D rettangoloInterno = new Path2D.Float();
		rettangoloInterno.moveTo(381, 415);
		rettangoloInterno.lineTo(500-32-5, 415);
		rettangoloInterno.moveTo(500+32+5, 415);
		rettangoloInterno.lineTo(620, 415);
		rettangoloInterno.lineTo(620, 524);
		rettangoloInterno.lineTo(381, 524);
		rettangoloInterno.lineTo(381, 415);
		
		rettangoloEsterno.append(rettangoloInterno, false);
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(rettangoloEsterno), coloreOstacoli));
		
		Path2D cancello = new Path2D.Float();
		cancello.moveTo(500-32, 411);
		cancello.lineTo(500+32, 411);
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(cancello), coloreCancello));
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
			elencoOstacoli.add(new Ostacolo(at.createTransformedShape(formaSimmetrica), coloreOstacoli));
			
		}
	}
	
}
