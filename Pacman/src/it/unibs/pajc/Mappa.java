package it.unibs.pajc;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
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
	private Path2D bordiEsterni = new Path2D.Float();
	private Path2D bordiInterni = new Path2D.Float();
	private List<Ostacolo> elencoOstacoli = new ArrayList<>();
		
	private final Color coloreBordi = new Color(83, 88, 254); 
	private final Color coloreOstacoli = new Color(83, 88, 254);
	private final Color coloreCancello = new Color(240, 176, 233);
	
	private final int larghezzaMonetePiccole = 8;
	private final int altezzaMonetePiccole = 8;
	private final int larghezzaMonetGrandi = 16;
	private final int altezzaMoneteGrande = 16;
	
	public Mappa(AffineTransform at) {
		this.at = at;
		atSimmetriaY.translate(1000, 0);
		atSimmetriaY.scale(-1, 1);
		
		creaMappa();
		creaOstacoli();
		creaMonete();
	}
	
	public Shape getBordi() {
		Path2D bordiTotali = (Path2D)bordiEsterni.clone();
		bordiTotali.append(bordiInterni, false);
		return bordiTotali;
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
		
		bordiEsterni.append(bordiEsterni.createTransformedShape(atSimmetriaY), false);
		
		bordiEsterni = (Path2D)at.createTransformedShape(bordiEsterni);
		bordiInterni = (Path2D)at.createTransformedShape(bordiInterni);
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
		
		bordiEsterni.moveTo(0, 425);
		bordiEsterni.lineTo(183, 425);
		curva(bordiEsterni, 183, 425, 186, 422, DirezioneCurva.ORIZZONTALE);		
		bordiEsterni.lineTo(186, 324);
		curva(bordiEsterni, 186, 324, 183, 321, DirezioneCurva.VERTICALE);
		bordiEsterni.lineTo(14, 321);
		curva(bordiEsterni, 14, 321, 5, 312, DirezioneCurva.ORIZZONTALE);
		bordiEsterni.lineTo(5 , 16);
		curva(bordiEsterni, 5, 16, 14, 7, DirezioneCurva.VERTICALE);
		bordiEsterni.lineTo(500, 7);
		
		//meta' margine in alto interno
		bordiInterni.moveTo(5, 434);
		bordiInterni.lineTo(186, 434);
		curva(bordiInterni, 186, 434, 195, 425, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(195, 321);
		curva(bordiInterni, 195, 321, 186, 312, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(17, 312);
		curva(bordiInterni, 17, 312, 14, 309, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(14, 19);
		curva(bordiInterni, 14, 19, 17, 16, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(472, 16);
		curva(bordiInterni, 472, 16, 481, 25, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(481, 136);
		curva(bordiInterni, 481, 136, 490, 145, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(500, 145);
		
		bordiInterni.lineTo(1000-490, 145);
		curva(bordiInterni, 1000-490, 145, 1000-481, 136, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(1000-481, 25);
		curva(bordiInterni, 1000-481, 25, 1000-472, 16, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(1000-17, 16);
		curva(bordiInterni, 1000-17, 16, 1000-14, 19, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(1000-14, 309);
		curva(bordiInterni, 1000-14, 309, 1000-17, 312, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(1000-186, 312);
		curva(bordiInterni, 1000-186, 312, 1000-195, 321, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(1000-195, 425);
		curva(bordiInterni, 1000-195, 425, 1000-186, 434, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(1000-5, 434);
		
		bordiInterni.lineTo(1000+10, 434);
		bordiInterni.lineTo(1000+10, -10);
		bordiInterni.lineTo(0-10, -10);
		bordiInterni.lineTo(-10, 434);
		bordiInterni.closePath();
	}
	private void creaPorzioneMappaInferiore() {
		//meta' margine in basso esterno
		bordiEsterni.moveTo(0, 513);
		bordiEsterni.lineTo(183, 513);
		curva(bordiEsterni, 183, 513, 186, 516, DirezioneCurva.ORIZZONTALE);	
		bordiEsterni.lineTo(186, 615);
		curva(bordiEsterni, 186, 615, 183, 618, DirezioneCurva.VERTICALE);
		bordiEsterni.lineTo(14, 618);
		curva(bordiEsterni, 14, 618, 5, 627, DirezioneCurva.ORIZZONTALE);
		bordiEsterni.lineTo(5, 986);
		curva(bordiEsterni, 5, 986, 14, 995, DirezioneCurva.VERTICALE);
		bordiEsterni.lineTo(500, 995);
			
		//meta' margine in basso interno
		bordiInterni.moveTo(5, 504);
		bordiInterni.lineTo(186, 504);
		curva(bordiInterni, 186, 504, 195, 513, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(195, 618);
		curva(bordiInterni, 195, 618, 186, 627, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(17, 627);
		curva(bordiInterni, 17, 627, 14, 630, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(14, 784);
		curva(bordiInterni, 14, 784, 23, 793, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(78, 793);
		curva(bordiInterni, 78, 793, 87, 802, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(87, 811);
		curva(bordiInterni, 87, 811, 78, 820, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(23, 820);
		curva(bordiInterni, 23, 820, 14, 829, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(14, 983);
		curva(bordiInterni, 14, 983, 17, 986, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(500, 986);
		
		bordiInterni.lineTo(1000-17, 986);
		curva(bordiInterni, 1000-17, 986, 1000-14, 983, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(1000-14, 829);
		curva(bordiInterni, 1000-14, 829, 1000-23, 820, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(1000-78, 820);
		curva(bordiInterni, 1000-87, 820, 1000-87, 811, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(1000-87, 802);
		curva(bordiInterni, 1000-87, 802, 1000-78, 793, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(1000-23, 793);
		curva(bordiInterni, 1000-23, 793, 1000-14, 784, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(1000-14, 630);
		curva(bordiInterni, 1000-14, 630, 1000-17, 627, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(1000-186, 627);
		curva(bordiInterni, 1000-186, 627, 1000-195, 618, DirezioneCurva.ORIZZONTALE);
		bordiInterni.lineTo(1000-195, 513);
		curva(bordiInterni, 1000-195, 513, 1000-186, 504, DirezioneCurva.VERTICALE);
		bordiInterni.lineTo(1000-5, 504);
		
		bordiInterni.lineTo(1000+10, 504);
		bordiInterni.lineTo(1000+10, 1000+10);
		bordiInterni.lineTo(-10, 1000+10);
		bordiInterni.lineTo(-10, 504);
		bordiInterni.closePath();
		
	}
		
	private void creaOstacoli() {
		creaOstacoloRettangolare(new RoundRectangle2D.Float(84, 86, 111, 59, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(265, 86, 146, 59, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(84, 215, 111, 27, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(265, 504, 39, 123, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(265, 697, 146, 26, 18, 18));
		creaOstacoloTRuotata();
		creaOstacoloLRovesciata();
		creaOstacoloManganello();
		
		creaOstacoliSimmetrici();
		
		creaOstacoloCentrale(new Point(383,215));
		creaOstacoloCentrale(new Point(383,600));
		creaOstacoloCentrale(new Point(383,793));
		creaRettangoloCentrale();	
	}
	
	private void creaOstacoloRettangolare(RoundRectangle2D forma) {
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaOstacoloCentrale(Point partenza) {
		Path2D ostacolo = new Path2D.Float();
		ostacolo.moveTo(partenza.x, partenza.y);
		ostacolo.lineTo(partenza.x+234, partenza.y);
		curva(ostacolo, partenza.x+234,partenza.y, partenza.x+234+9,partenza.y+9,DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(partenza.x+243, partenza.y+18);
		curva(ostacolo, partenza.x+243,partenza.y+18, partenza.x+243-9,partenza.y+18+9,DirezioneCurva.VERTICALE);
		ostacolo.lineTo(partenza.x+145,partenza.y+27);
		curva(ostacolo, partenza.x+145, partenza.y+27, partenza.x+145-9, partenza.y+27+9,DirezioneCurva.ORIZZONTALE);		
		ostacolo.lineTo(partenza.x+136, partenza.y+36+78);
		curva(ostacolo, partenza.x+136, partenza.y+105+9, partenza.x+136-9, partenza.y+105+9+9,DirezioneCurva.VERTICALE);
		ostacolo.lineTo(481+9, partenza.y+123);
		curva(ostacolo, 481+9, partenza.y+123, 481, partenza.y+123-9, DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(481, partenza.y+27+9);
		curva(ostacolo, 481, partenza.y+27+9, 481-9, partenza.y+27, DirezioneCurva.VERTICALE);
		ostacolo.lineTo(partenza.x, partenza.y+27);
		curva(ostacolo,partenza.x, partenza.y+27,  partenza.x-9,partenza.y+18, DirezioneCurva.ORIZZONTALE);
		ostacolo.lineTo(partenza.x-9, partenza.y+9);
		curva(ostacolo,partenza.x-9, partenza.y+9, partenza.x,partenza.y, DirezioneCurva.VERTICALE);
		ostacolo.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(ostacolo), coloreOstacoli));
	}
	
	private void creaOstacoloLRovesciata() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(84, 706);
		curva(forma, 84, 706, 93, 697, DirezioneCurva.VERTICALE);
		forma.lineTo(187, 697);
		curva(forma, 187, 697, 196, 706, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(196, 811);
		curva(forma, 196, 811, 185, 820, DirezioneCurva.VERTICALE);
		forma.lineTo(166, 820);
		curva(forma, 166, 820, 157, 811, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(157, 732);
		curva(forma, 157, 732, 148, 723, DirezioneCurva.VERTICALE);
		forma.lineTo(93, 723);
		curva(forma, 93, 723, 84, 714, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
		
	private void creaOstacoloTRuotata() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(265, 224);
		curva(forma, 265, 224, 274, 215, DirezioneCurva.VERTICALE); 
		forma.lineTo(295, 215);
		curva(forma, 295, 215, 304, 224, DirezioneCurva.ORIZZONTALE); 
		forma.lineTo(304, 303);
		curva(forma, 304, 303, 313, 312, DirezioneCurva.VERTICALE); 
		forma.lineTo(402, 312);
		curva(forma, 402, 312, 411, 321, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(411, 329);
		curva(forma, 411, 329, 402, 338, DirezioneCurva.VERTICALE);
		forma.lineTo(315, 338);
		curva(forma, 313, 338, 304, 347, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(304, 425);
		curva(forma, 304, 425, 295, 434, DirezioneCurva.VERTICALE);
		forma.lineTo(274, 434);
		curva(forma, 274, 434, 265, 425, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaOstacoloManganello() {
		Path2D forma = new Path2D.Float();
		forma.moveTo(84,899);
		curva(forma, 84, 899, 93, 890, DirezioneCurva.VERTICALE);
		forma.lineTo(257, 890);
		curva(forma, 257, 890, 266, 881, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(266, 802);
		curva(forma, 266, 802, 275, 793, DirezioneCurva.VERTICALE);
		forma.lineTo(295, 793);
		curva(forma, 295, 793, 304, 802, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(304, 881);
		curva(forma, 304, 881, 313, 890, DirezioneCurva.VERTICALE);
		forma.lineTo(402, 890);
		curva(forma, 402, 890, 411, 899, DirezioneCurva.ORIZZONTALE);
		forma.lineTo(411, 907);
		curva(forma, 411, 907, 402, 916, DirezioneCurva.VERTICALE);
		forma.lineTo(93, 916);
		curva(forma, 93, 916, 84, 907, DirezioneCurva.ORIZZONTALE);
		forma.closePath();
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(forma), coloreOstacoli));
	}
	
	private void creaRettangoloCentrale() {
		Path2D rettangoloEsterno = new Path2D.Float();
		rettangoloEsterno.moveTo(374, 408);
		rettangoloEsterno.lineTo(500-32-3, 408);
		rettangoloEsterno.moveTo(500+32+3, 408);
		rettangoloEsterno.lineTo(626, 408);
		rettangoloEsterno.lineTo(626, 530);
		rettangoloEsterno.lineTo(374, 530);
		rettangoloEsterno.lineTo(374, 408);
		
		Path2D rettangoloInterno = new Path2D.Float();
		rettangoloInterno.moveTo(378, 412);
		rettangoloInterno.lineTo(500-32-3, 412);
		rettangoloInterno.moveTo(500+32+3, 412);
		rettangoloInterno.lineTo(622, 412);
		rettangoloInterno.lineTo(622, 526);
		rettangoloInterno.lineTo(378, 526);
		rettangoloInterno.lineTo(378, 412);
		
		rettangoloEsterno.append(rettangoloInterno, false);
		
		Path2D cancello = new Path2D.Float();
		cancello.moveTo(500-32-3, 408);
		cancello.lineTo(500+32+3, 408);
		
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(cancello), coloreCancello));
		elencoOstacoli.add(new Ostacolo(at.createTransformedShape(rettangoloEsterno), coloreOstacoli));
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
	
	private HashMap<Point2D, Moneta> monete = new HashMap<>();
	private void creaMonete() {
		int aumentoDimensionePerControllo = 10;
		for(float i = 51; i <= 986-34; i+=((986-35)-(16+35))/28.) { //colonna
			for (float j = 49; j < 1000-40; j+=((1000-14-35)-(14+35))/25.) { //1 riga
				Rectangle2D rettangolo = new Rectangle2D.Float(j-(larghezzaMonetePiccole+aumentoDimensionePerControllo)/2, i-(altezzaMonetePiccole+aumentoDimensionePerControllo)/2, larghezzaMonetePiccole+aumentoDimensionePerControllo, altezzaMonetePiccole+aumentoDimensionePerControllo);
				boolean isContenuto = false;
				for(Ostacolo o : elencoOstacoli) {
					if (o.getShape().intersects(at.createTransformedShape(rettangolo).getBounds2D())) {
						isContenuto = true;
						break;
					}
				}
				if(bordiInterni.intersects(at.createTransformedShape(rettangolo).getBounds2D())) {
					isContenuto = true;
				}		
				if(!isContenuto && (i<=434 || i>=504 || (j>200 && j<1000-200))) {
					rettangolo.setRect(j-larghezzaMonetePiccole/2, i-altezzaMonetePiccole/2, larghezzaMonetePiccole, altezzaMonetePiccole);
					Moneta moneta = new Moneta(10,at.createTransformedShape(rettangolo),Color.yellow); 
					monete.put(at.transform(new Point2D.Float(j,i), null), moneta);	
				}
				
				/*CONTROLLO SUI BORDI
				boolean contenuto = false;
							
				System.out.println(controllo.toString() + " " + contenuto);
				*/
				
			}
		}
		
	}
	public HashMap<Point2D, Moneta> getMonete() {
		return monete;
	}
	
}
