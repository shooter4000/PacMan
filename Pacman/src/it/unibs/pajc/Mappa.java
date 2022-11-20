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
		creaMonete();
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
		bordi.lineTo(183, 425);
		curva(bordi, 183, 425, 186, 422, DirezioneCurva.ORIZZONTALE);		
		bordi.lineTo(186, 324);
		curva(bordi, 186, 324, 183, 321, DirezioneCurva.VERTICALE);
		bordi.lineTo(14, 321);
		curva(bordi, 14, 321, 5, 312, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(5 , 16);
		curva(bordi, 5, 16, 14, 7, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 7);
		
		//meta' margine in alto interno
		bordi.moveTo(5, 434);
		bordi.lineTo(186, 434);
		curva(bordi, 186, 434, 195, 425, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(195, 321);
		curva(bordi, 195, 321, 186, 312, DirezioneCurva.VERTICALE);
		bordi.lineTo(17, 312);
		curva(bordi, 17, 312, 14, 309, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(14, 21);
		curva(bordi, 14, 21, 17, 16, DirezioneCurva.VERTICALE);
		bordi.lineTo(472, 16);
		curva(bordi, 472, 16, 481, 25, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(481, 136);
		curva(bordi, 481, 136, 490, 145, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 145);
	}
	private void creaPorzioneMappaInferiore() {
		//meta' margine in basso esterno
		bordi.moveTo(5, 513);
		bordi.lineTo(183, 513);
		curva(bordi, 183, 513, 186, 516, DirezioneCurva.ORIZZONTALE);	
		bordi.lineTo(186, 615);
		curva(bordi, 186, 615, 183, 618, DirezioneCurva.VERTICALE);
		bordi.lineTo(14, 618);
		curva(bordi, 14, 618, 5, 627, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(5, 986);
		curva(bordi, 5, 986, 14, 995, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 995);
			
		//meta' margine in basso interno
		bordi.moveTo(5, 504);
		bordi.lineTo(186, 504);
		curva(bordi, 186, 504, 195, 513, DirezioneCurva.ORIZZONTALE);
		bordi.lineTo(195, 618);
		curva(bordi, 195, 618, 186, 627, DirezioneCurva.VERTICALE);
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
		bordi.lineTo(14, 983);
		curva(bordi, 14, 983, 19, 986, DirezioneCurva.VERTICALE);
		bordi.lineTo(500, 986);
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
		for(int i = 51-4 ; i < 984-37; i+=((312-35-4)-(16+35-4))/7) { //colonna
			for (int j = 49-4 ; j < 1000-40; j+=((481-35-4)-(14+35-4))/11) { //1 riga
				Rectangle2D rettangolo = new Rectangle2D.Float(j, i, 8, 8);
				boolean isContenuto = false;
				for(Ostacolo o : elencoOstacoli) {
					if (o.getShape().intersects(at.createTransformedShape(rettangolo).getBounds2D())) {
						isContenuto = true;
						break;
					}
				}
				if(!isContenuto) {
					rettangolo.setRect(j, i, 8, 8);
					Moneta moneta = new Moneta(10,at.createTransformedShape(rettangolo),Color.yellow);
					monete.put(at.transform(new Point(j,i), null), moneta);	
				}
				
				/*CONTROLLO SUI BORDI
				boolean contenuto = false;
					if(bordi.intersects(at.createTransformedShape(rettangolo).getBounds2D())) {
						contenuto = true;
					}				
				System.out.println(controllo.toString() + " " + contenuto);
				*/
				
			}
		}
		
	}
	public HashMap<Point2D, Moneta> getMonete() {
		return monete;
	}
	
}
