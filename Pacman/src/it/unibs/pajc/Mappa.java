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
	
	private ArrayList<Forma> elencoOstacoliComputazionali = new ArrayList<>();
	private ArrayList<Forma> elencoOstacoliGrafici = new ArrayList<>();
	
	private ArrayList<Moneta> monete = new ArrayList<>();
	
	//private Path2D bordiEsterni = new Path2D.Float();
	//private Path2D bordiInterni = new Path2D.Float();
	//private List<Ostacolo> elencoOstacoli = new ArrayList<>();
		
	private final Color COLORE_BORDI = new Color(83, 88, 254); 
	private final Color COLORE_OSTACOLI = new Color(83, 88, 254);
	private final Color COLORE_CANCELLO = new Color(240, 176, 233);
	
	private final double VALORE_MONETA_PICCOLA = 10;
	private final double VALORE_MONETA_GRANDE = 100;
	
	public Mappa(AffineTransform at) {
		this.at = at;
		atSimmetriaY.translate(1000, 0);
		atSimmetriaY.scale(-1, 1);
		
		creaBordi();
		creaOstacoli();
		creaMonete();
	}
	
	public ArrayList<Forma> getElencoOstacoliGrafici() {
		return elencoOstacoliGrafici;
	}

	public ArrayList<Forma> getElencoOstacoliComputazionali() {
		return elencoOstacoliComputazionali;
	}
	
	private void creaBordi() {
		
		Path2D bordiEsterniGrafici = new Path2D.Float();
		Path2D bordiInterniGrafici = new Path2D.Float();
		
		creaPorzioneMappaSuperiore(bordiEsterniGrafici, bordiInterniGrafici, true);
		creaPorzioneMappaInferiore(bordiEsterniGrafici, bordiInterniGrafici, true);
		
		bordiEsterniGrafici.append(bordiEsterniGrafici.createTransformedShape(atSimmetriaY), false);
		
		elencoOstacoliGrafici.add(new Bordo((Path2D)at.createTransformedShape(bordiEsterniGrafici),COLORE_BORDI));
		elencoOstacoliGrafici.add(new Bordo((Path2D)at.createTransformedShape(bordiInterniGrafici), COLORE_BORDI));
		
		
		Path2D bordiInterniComputazionali = new Path2D.Float();
		
		creaPorzioneMappaSuperiore(null, bordiInterniComputazionali, false);
		creaPorzioneMappaInferiore(null, bordiInterniComputazionali, false);
		elencoOstacoliComputazionali.add(new Bordo((Path2D)at.createTransformedShape(bordiInterniComputazionali), COLORE_BORDI));
	}
	
	private enum DirezioneCurva{
		//indica la direzione iniziale prima di percorrere la curva
		ORIZZONTALE,
		VERTICALE;
		
	}
	
	private void curva(Path2D path, double x1, double y1, double x2, double y2, DirezioneCurva direzione, boolean bevel) {
		if(bevel) {
			switch(direzione) {
			case ORIZZONTALE:
				path.curveTo(x1, y1, x2, y1, x2, y2);
				break;
			case VERTICALE:
				path.curveTo(x1, y1, x1, y2, x2, y2);
				break;
			}
		}else {
			switch(direzione) {
			case ORIZZONTALE:
				path.lineTo(x2, y1);
				path.lineTo(x2, y2);
				break;
			case VERTICALE:
				path.lineTo(x1, y2);
				path.lineTo(x2, y2);
				break;
			}
			
		}
		
	}
	
	private void creaPorzioneMappaSuperiore(Path2D bordiEsterni, Path2D bordiInterni, boolean bevelAngoli) {
		//meta' margine in alto esterno
		if(bordiEsterni!=null) {
			bordiEsterni.moveTo(0, 425);
			bordiEsterni.lineTo(183, 425);
			curva(bordiEsterni, 183, 425, 186, 422, DirezioneCurva.ORIZZONTALE, bevelAngoli);		
			bordiEsterni.lineTo(186, 324);
			curva(bordiEsterni, 186, 324, 183, 321, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiEsterni.lineTo(14, 321);
			curva(bordiEsterni, 14, 321, 5, 312, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiEsterni.lineTo(5 , 16);
			curva(bordiEsterni, 5, 16, 14, 7, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiEsterni.lineTo(500, 7);
		}
		
				
		//meta' margine in alto interno
		if(bordiInterni!=null) {
			bordiInterni.moveTo(5, 434);
			bordiInterni.lineTo(186, 434);
			curva(bordiInterni, 186, 434, 195, 425, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(195, 321);
			curva(bordiInterni, 195, 321, 186, 312, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(17, 312);
			curva(bordiInterni, 17, 312, 14, 309, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(14, 19);
			curva(bordiInterni, 14, 19, 17, 16, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(472, 16);
			curva(bordiInterni, 472, 16, 481, 25, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(481, 136);
			curva(bordiInterni, 481, 136, 490, 145, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(500, 145);
			
			bordiInterni.lineTo(1000-490, 145);
			curva(bordiInterni, 1000-490, 145, 1000-481, 136, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(1000-481, 25);
			curva(bordiInterni, 1000-481, 25, 1000-472, 16, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(1000-17, 16);
			curva(bordiInterni, 1000-17, 16, 1000-14, 19, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(1000-14, 309);
			curva(bordiInterni, 1000-14, 309, 1000-17, 312, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(1000-186, 312);
			curva(bordiInterni, 1000-186, 312, 1000-195, 321, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(1000-195, 425);
			curva(bordiInterni, 1000-195, 425, 1000-186, 434, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(1000-5, 434);
			
			bordiInterni.lineTo(1000+10, 434);
			bordiInterni.lineTo(1000+10, -10);
			bordiInterni.lineTo(0-10, -10);
			bordiInterni.lineTo(-10, 434);
			bordiInterni.closePath();
		}
		
	}
	private void creaPorzioneMappaInferiore(Path2D bordiEsterni, Path2D bordiInterni, boolean bevelAngoli) {
		//meta' margine in basso esterno
		if(bordiEsterni!=null) {
			bordiEsterni.moveTo(0, 513);
			bordiEsterni.lineTo(183, 513);
			curva(bordiEsterni, 183, 513, 186, 516, DirezioneCurva.ORIZZONTALE, bevelAngoli);	
			bordiEsterni.lineTo(186, 615);
			curva(bordiEsterni, 186, 615, 183, 618, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiEsterni.lineTo(14, 618);
			curva(bordiEsterni, 14, 618, 5, 627, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiEsterni.lineTo(5, 986);
			curva(bordiEsterni, 5, 986, 14, 995, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiEsterni.lineTo(500, 995);
		}
			
		//meta' margine in basso interno
		if(bordiInterni!=null) {
			bordiInterni.moveTo(5, 504);
			bordiInterni.lineTo(186, 504);
			curva(bordiInterni, 186, 504, 195, 513, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(195, 618);
			curva(bordiInterni, 195, 618, 186, 627, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(17, 627);
			curva(bordiInterni, 17, 627, 14, 630, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(14, 784);
			curva(bordiInterni, 14, 784, 23, 793, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(78, 793);
			curva(bordiInterni, 78, 793, 87, 802, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(87, 811);
			curva(bordiInterni, 87, 811, 78, 820, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(23, 820);
			curva(bordiInterni, 23, 820, 14, 829, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(14, 983);
			curva(bordiInterni, 14, 983, 17, 986, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(500, 986);
			
			bordiInterni.lineTo(1000-17, 986);
			curva(bordiInterni, 1000-17, 986, 1000-14, 983, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(1000-14, 829);
			curva(bordiInterni, 1000-14, 829, 1000-23, 820, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(1000-78, 820);
			curva(bordiInterni, 1000-78, 820, 1000-87, 811, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(1000-87, 802);
			curva(bordiInterni, 1000-87, 802, 1000-78, 793, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(1000-23, 793);
			curva(bordiInterni, 1000-23, 793, 1000-14, 784, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(1000-14, 630);
			curva(bordiInterni, 1000-14, 630, 1000-17, 627, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(1000-186, 627);
			curva(bordiInterni, 1000-186, 627, 1000-195, 618, DirezioneCurva.ORIZZONTALE, bevelAngoli);
			bordiInterni.lineTo(1000-195, 513);
			curva(bordiInterni, 1000-195, 513, 1000-186, 504, DirezioneCurva.VERTICALE, bevelAngoli);
			bordiInterni.lineTo(1000-5, 504);
			
			bordiInterni.lineTo(1000+10, 504);
			bordiInterni.lineTo(1000+10, 1000+10);
			bordiInterni.lineTo(-10, 1000+10);
			bordiInterni.lineTo(-10, 504);
			bordiInterni.closePath();
		}
	}
	
	private void creaOstacoli() {
		creaOstacoloRettangolare(new RoundRectangle2D.Float(84, 86, 111, 59, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(265, 86, 146, 59, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(84, 215, 111, 27, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(265, 504, 39, 123, 18, 18));
		creaOstacoloRettangolare(new RoundRectangle2D.Float(265, 697, 146, 26, 18, 18));
		
		elencoOstacoliGrafici.add(new Ostacolo(creaOstacoloTRuotata(true), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(creaOstacoloTRuotata(false), COLORE_OSTACOLI));
		
		elencoOstacoliGrafici.add(new Ostacolo(creaOstacoloLRovesciata(true), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(creaOstacoloLRovesciata(false), COLORE_OSTACOLI));
		
		elencoOstacoliGrafici.add(new Ostacolo(creaOstacoloManganello(true), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(creaOstacoloManganello(false), COLORE_OSTACOLI));
		
		creaOstacoliSimmetrici();
		
		elencoOstacoliGrafici.add(new Ostacolo(creaOstacoloCentrale(new Point(383,215), true), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(creaOstacoloCentrale(new Point(383,215), false), COLORE_OSTACOLI));
		
		elencoOstacoliGrafici.add(new Ostacolo(creaOstacoloCentrale(new Point(383,600), true), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(creaOstacoloCentrale(new Point(383,600), false), COLORE_OSTACOLI));
		
		elencoOstacoliGrafici.add(new Ostacolo(creaOstacoloCentrale(new Point(383,793), true), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(creaOstacoloCentrale(new Point(383,793), false), COLORE_OSTACOLI));
		
		creaRettangoloCentrale();	
	}
	
	private void creaOstacoloRettangolare(RoundRectangle2D forma) {
		elencoOstacoliGrafici.add(new Ostacolo(at.createTransformedShape(forma), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(at.createTransformedShape(forma).getBounds2D(), COLORE_OSTACOLI));
	}
	
	private Shape creaOstacoloCentrale(Point partenza, boolean bevelAngoli) {
		Path2D ostacolo = new Path2D.Float();
		ostacolo.moveTo(partenza.x, partenza.y);
		ostacolo.lineTo(partenza.x+234, partenza.y);
		curva(ostacolo, partenza.x+234,partenza.y, partenza.x+234+9,partenza.y+9,DirezioneCurva.ORIZZONTALE, bevelAngoli);
		ostacolo.lineTo(partenza.x+243, partenza.y+18);
		curva(ostacolo, partenza.x+243,partenza.y+18, partenza.x+243-9,partenza.y+18+9,DirezioneCurva.VERTICALE, bevelAngoli);
		ostacolo.lineTo(partenza.x+145,partenza.y+27);
		curva(ostacolo, partenza.x+145, partenza.y+27, partenza.x+145-9, partenza.y+27+9,DirezioneCurva.ORIZZONTALE, bevelAngoli);		
		ostacolo.lineTo(partenza.x+136, partenza.y+36+78);
		curva(ostacolo, partenza.x+136, partenza.y+105+9, partenza.x+136-9, partenza.y+105+9+9,DirezioneCurva.VERTICALE, bevelAngoli);
		ostacolo.lineTo(481+9, partenza.y+123);
		curva(ostacolo, 481+9, partenza.y+123, 481, partenza.y+123-9, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		ostacolo.lineTo(481, partenza.y+27+9);
		curva(ostacolo, 481, partenza.y+27+9, 481-9, partenza.y+27, DirezioneCurva.VERTICALE, bevelAngoli);
		ostacolo.lineTo(partenza.x, partenza.y+27);
		curva(ostacolo,partenza.x, partenza.y+27,  partenza.x-9,partenza.y+18, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		ostacolo.lineTo(partenza.x-9, partenza.y+9);
		curva(ostacolo,partenza.x-9, partenza.y+9, partenza.x,partenza.y, DirezioneCurva.VERTICALE, bevelAngoli);
		ostacolo.closePath();
		
		return at.createTransformedShape(ostacolo);
	}
	
	private Shape creaOstacoloLRovesciata(boolean bevelAngoli) {
		Path2D forma = new Path2D.Float();
		forma.moveTo(84, 706);
		curva(forma, 84, 706, 93, 697, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(186, 697);
		curva(forma, 186, 697, 195, 706, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.lineTo(195, 811);
		curva(forma, 195, 811, 184, 820, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(166, 820);
		curva(forma, 166, 820, 157, 811, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.lineTo(157, 732);
		curva(forma, 157, 732, 148, 723, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(93, 723);
		curva(forma, 93, 723, 84, 714, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.closePath();
		
		return at.createTransformedShape(forma);
	}
		
	private Shape creaOstacoloTRuotata(boolean bevelAngoli) {
		Path2D forma = new Path2D.Float();
		forma.moveTo(265, 224);
		curva(forma, 265, 224, 274, 215, DirezioneCurva.VERTICALE, bevelAngoli); 
		forma.lineTo(295, 215);
		curva(forma, 295, 215, 304, 224, DirezioneCurva.ORIZZONTALE, bevelAngoli); 
		forma.lineTo(304, 303);
		curva(forma, 304, 303, 313, 312, DirezioneCurva.VERTICALE, bevelAngoli); 
		forma.lineTo(402, 312);
		curva(forma, 402, 312, 411, 321, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.lineTo(411, 329);
		curva(forma, 411, 329, 402, 338, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(315, 338);
		curva(forma, 315, 338, 304, 347, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.lineTo(304, 425);
		curva(forma, 304, 425, 295, 434, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(274, 434);
		curva(forma, 274, 434, 265, 425, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.closePath();
		
		return at.createTransformedShape(forma);
	}
	
	private Shape creaOstacoloManganello(boolean bevelAngoli) {
		Path2D forma = new Path2D.Float();
		forma.moveTo(84, 899);
		curva(forma, 84, 899, 93, 890, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(256, 890);
		curva(forma, 256, 890, 265, 881, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.lineTo(265, 802);
		curva(forma, 265, 802, 274, 793, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(295, 793);
		curva(forma, 295, 793, 304, 802, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.lineTo(304, 881);
		curva(forma, 304, 881, 313, 890, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(402, 890);
		curva(forma, 402, 890, 411, 899, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.lineTo(411, 907);
		curva(forma, 411, 907, 402, 916, DirezioneCurva.VERTICALE, bevelAngoli);
		forma.lineTo(93, 916);
		curva(forma, 93, 916, 84, 907, DirezioneCurva.ORIZZONTALE, bevelAngoli);
		forma.closePath();
		
		return at.createTransformedShape(forma);
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
		
		elencoOstacoliGrafici.add(new Ostacolo(at.createTransformedShape(cancello), COLORE_CANCELLO));
		elencoOstacoliComputazionali.add(new Ostacolo(at.createTransformedShape(cancello), COLORE_CANCELLO));
		
		elencoOstacoliGrafici.add(new Ostacolo(at.createTransformedShape(rettangoloEsterno), COLORE_OSTACOLI));
		elencoOstacoliComputazionali.add(new Ostacolo(at.createTransformedShape(rettangoloEsterno), COLORE_OSTACOLI));
	}
	
	private void creaOstacoliSimmetrici() {
		AffineTransform atInversa = null;
		try {
			atInversa = at.createInverse();
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		
		int numeroOstacoliGrafici = elencoOstacoliGrafici.size();
		int numeroOstacoliComputazionali = elencoOstacoliComputazionali.size();
		
		for(int i = 0; i<numeroOstacoliGrafici;i++) {
			Forma ipoteticoOstacolo = elencoOstacoliGrafici.get(i);
			if("Ostacolo".equals(ipoteticoOstacolo.getClass().getSimpleName())) {
				Shape forma = atInversa.createTransformedShape(elencoOstacoliGrafici.get(i).getForma());
				Shape formaSimmetrica = atSimmetriaY.createTransformedShape(forma);
				elencoOstacoliGrafici.add(new Ostacolo(at.createTransformedShape(formaSimmetrica), COLORE_OSTACOLI));
			}
		}
		
		for(int i = 0; i<numeroOstacoliComputazionali;i++) {
			Forma ipoteticoOstacolo = elencoOstacoliComputazionali.get(i);
			if("Ostacolo".equals(ipoteticoOstacolo.getClass().getSimpleName())) {
				Shape forma = atInversa.createTransformedShape(elencoOstacoliComputazionali.get(i).getForma());
				Shape formaSimmetrica = atSimmetriaY.createTransformedShape(forma);
				elencoOstacoliComputazionali.add(new Ostacolo(at.createTransformedShape(formaSimmetrica), COLORE_OSTACOLI));
			}
		}
	}
	
	private void creaMonete() {
		int numRiga = 1;
		for(float i = 51; i <= 986-34; i+=((986-35)-(16+35))/28.) {
			int numColonna = 1;
			for (float j = 49; j < 1000-40; j+=((1000-14-35)-(14+35))/25.) {
				if(!verificaIntersezioneMonete(new Point2D.Float(j, i)) && (i<=434 || i>=504 || (j>200 && j<1000-200))) {
					Moneta moneta = null;
					if((numRiga == 3 || numRiga==22) && (numColonna==1 || numColonna==26)) {
							moneta = Moneta.elencoPossibiliMonete.get("CILIEGIA").apply(VALORE_MONETA_GRANDE, new Point2D.Float(j, i), at);
					}else {
							moneta = Moneta.elencoPossibiliMonete.get("PICCOLA").apply(VALORE_MONETA_PICCOLA, new Point2D.Float(j, i), at);
					}
					monete.add(moneta);
				}
				numColonna++;
			}
			numRiga++;
		}
		
	}
	
	private boolean verificaIntersezioneMonete(Point2D coordCentro) {
		float dimensionePerControllo = 18;
		Rectangle2D rettangolo = new Rectangle2D.Float((float)(coordCentro.getX()-dimensionePerControllo/2.), (float)(coordCentro.getY()-dimensionePerControllo/2.), dimensionePerControllo, dimensionePerControllo);
		for(Forma o : elencoOstacoliComputazionali) {
			if (o.getForma().intersects(at.createTransformedShape(rettangolo).getBounds2D())) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Moneta> getMonete() {
		return monete;
	}
	
	public boolean interseca(Shape forma) {
		return elencoOstacoliComputazionali
					.stream()
					.filter(ostacolo -> ostacolo.getForma().intersects(forma.getBounds2D()))
					.count()!=0;
	}
	
}
