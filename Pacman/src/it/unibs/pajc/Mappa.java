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
import java.util.HashMap;
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
		bordi.lineTo(187, 321);
		curva(bordi, 187, 321, 184, 318, DirezioneCurva.VERTICALE);
		bordi.lineTo(14, 318);
		curva(bordi, 14, 318, 5, 309, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(5 , 16);
		curva(bordi, 5, 16, 14, 7, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 7);
		
		//meta' margine in alto interno
		bordi.moveTo(5, 434);
		bordi.lineTo(187, 434);
		curva(bordi, 187, 434, 196, 425, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(196, 318);
		curva(bordi, 196, 318, 187, 309, DirezioneCurva.VERTICALE);
		bordi.lineTo(17, 309);
		curva(bordi, 17, 309, 14, 306, DirezioneCurva.ORIZZONTALE);
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
		bordi.lineTo(187, 615);
		curva(bordi, 187, 615, 184, 618, DirezioneCurva.VERTICALE);
		bordi.lineTo(14, 618);
		curva(bordi, 14, 618, 5, 627, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(5, 984);
		curva(bordi, 5, 984, 14, 993, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 993);
			
		//meta' margine in basso interno
		bordi.moveTo(5, 504);
		bordi.lineTo(187, 504);
		curva(bordi, 187, 504, 196, 513, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(196, 618);
		curva(bordi, 196, 618, 187, 627, DirezioneCurva.VERTICALE);
		bordi.lineTo(17, 627);
		curva(bordi, 17, 627, 14, 630, DirezioneCurva.ORIZZONTALE);
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
		creaOstacoloRettangolare(new RoundRectangle2D.Float(84, 215, 111, 26, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(264, 504, 40, 122, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(264, 696, 148, 26, 18, 18));
		creaOstacoloTRuotata();
		creaOstacoloLRovesciata();
		creaOstacoloManganello();
		
		creaOstacoliSimmetrici();
		
		creaOstacoloCentrale(new Point(381,215));
		creaOstacoloCentrale(new Point(381,599));
		creaOstacoloCentrale(new Point(381,793));
		creaRettangoloCentrale();	
	}
	
	private void creaOstacoloRettangolare(RoundRectangle2D forma) {
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaOstacoloCentrale(Point partenza) {
		Path2D ostacolo = new Path2D.Float();
		ostacolo.moveTo(partenza.x, partenza.y);
		ostacolo.lineTo(partenza.x+239, partenza.y);
		curva(ostacolo, partenza.x+239,partenza.y, partenza.x+239+9,partenza.y+9,DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(partenza.x+248, partenza.y+19);
		curva(ostacolo, partenza.x+248,partenza.y+19, partenza.x+248-9,partenza.y+19+9,DirezioneCurva.VERTICALE);
		ostacolo.lineTo(partenza.x+239-92,partenza.y+28);
		curva(ostacolo, partenza.x+147, partenza.y+28, partenza.x+147-9, partenza.y+28+9,DirezioneCurva.ORIZZONTALE);		
		ostacolo.lineTo(partenza.x+138, partenza.y+36+78);
		curva(ostacolo, partenza.x+138, partenza.y+105+9, partenza.x+138-9, partenza.y+105+9+9,DirezioneCurva.VERTICALE);
		ostacolo.lineTo(481+9, partenza.y+123);
		curva(ostacolo, 481+9, partenza.y+123, 481, partenza.y+123-9, DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(481, partenza.y+28+9);
		curva(ostacolo, 481, partenza.y+28+9, 481-9, partenza.y+28, DirezioneCurva.VERTICALE);
		ostacolo.lineTo(partenza.x, partenza.y+28);
		curva(ostacolo,partenza.x, partenza.y+28,  partenza.x-9,partenza.y+16, DirezioneCurva.ORIZZONTALE);
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
		forma.lineTo(157, 733);
		curva(forma, 157, 733, 148, 724, DirezioneCurva.VERTICALE);
		forma.lineTo(93, 724);
		curva(forma, 93, 724, 84, 715, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
		
	private void creaOstacoloTRuotata() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(263, 224);
		curva(forma, 263, 224, 272, 215, DirezioneCurva.VERTICALE); 
		forma.lineTo(295, 215);
		curva(forma, 295, 215, 304, 224, DirezioneCurva.ORIZZONTALE); 
		forma.lineTo(304, 300);
		curva(forma, 304, 300, 313, 309, DirezioneCurva.VERTICALE); 
		forma.lineTo(402, 309);
		curva(forma, 402, 309, 411, 318, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(411, 329);
		curva(forma, 411, 329, 402, 338, DirezioneCurva.VERTICALE);
		forma.lineTo(315, 338);
		curva(forma, 313, 338, 304, 347, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(304, 425);
		curva(forma, 304, 425, 295, 434, DirezioneCurva.VERTICALE);
		forma.lineTo(272, 434);
		curva(forma, 272, 434, 263, 425, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaOstacoloManganello() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(84,898);
		curva(forma, 84, 898, 93, 889, DirezioneCurva.VERTICALE);
		forma.lineTo(255, 889);
		curva(forma, 255, 889, 264, 880, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(264, 802);
		curva(forma, 264, 802, 273, 793, DirezioneCurva.VERTICALE);
		forma.lineTo(296, 793);
		curva(forma, 296, 793, 305, 802, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(305, 880);
		curva(forma, 305, 880, 314, 889, DirezioneCurva.VERTICALE);
		forma.lineTo(402, 889);
		curva(forma, 402, 889, 411, 898, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(411, 907);
		curva(forma, 411, 907, 402, 916, DirezioneCurva.VERTICALE);
		forma.lineTo(93, 916);
		curva(forma, 93, 916, 84, 907, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaRettangoloCentrale() {
		Path2D rettangoloEsterno = new Path2D.Float();
		rettangoloEsterno.moveTo(377, 411);
		rettangoloEsterno.lineTo(500-32-3, 411);
		rettangoloEsterno.moveTo(500+32+3, 411);
		rettangoloEsterno.lineTo(624, 411);
		rettangoloEsterno.lineTo(624, 526);
		rettangoloEsterno.lineTo(377, 526);
		rettangoloEsterno.lineTo(377, 411);
		
		Path2D rettangoloInterno = new Path2D.Float();
		rettangoloInterno.moveTo(381, 415);
		rettangoloInterno.lineTo(500-32-3, 415);
		rettangoloInterno.moveTo(500+32+3, 415);
		rettangoloInterno.lineTo(620, 415);
		rettangoloInterno.lineTo(620, 522);
		rettangoloInterno.lineTo(381, 522);
		rettangoloInterno.lineTo(381, 415);
		
		rettangoloEsterno.append(rettangoloInterno, false);
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(rettangoloEsterno), coloreOstacoli));
		
		Path2D cancello = new Path2D.Float();
		cancello.moveTo(500-32+5-3, 411);
		cancello.lineTo(500+32-5+3, 411);
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
