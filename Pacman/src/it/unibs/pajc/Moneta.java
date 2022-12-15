package it.unibs.pajc;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D.Float;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Moneta {

	private static final int LARGHEZZA_MONETE_PICCOLE = 8;
	private static final int ALTEZZA_MONETE_PICCOLE = 8;
	private static final Color COLORE_MONETA_PICCOLA = Color.yellow;
	
	private static final int LARGHEZZA_MONETE_GRANDI = 24;
	private static final int ALTEZZA_MONETE_GRANDI = 24;
	private static final Color COLORE_MONETA_GRANDE = Color.yellow;
	
	private static final int SIZE_CILIEGIA_GRAFICA = 30;
	private static final int SIZE_CILIEGIA_COMPUTAZIONALE = 70;
	private static final Color COLORE_CILIEGIA = Color.red;
	private static final Color COLORE_GAMBO_CILIEGIA = new Color(180, 123, 66);
	private static final Color COLORE_RIFLESSO_CILIEGIA = Color.white;
	
	public static HashMap<String, Function3<Double, Point2D, AffineTransform, Moneta>> elencoPossibiliMonete = new HashMap<>();
	static {
		elencoPossibiliMonete.put("GRANDE", Moneta::creaMonetaGrande);
		elencoPossibiliMonete.put("PICCOLA", Moneta::creaMonetaPiccola);
		elencoPossibiliMonete.put("CILIEGIA", Moneta::creaCiliegia);
	}
	
	public static void aggiungiMoneta(String testo, Function3<Double, Point2D, AffineTransform, Moneta> azione) {
		elencoPossibiliMonete.put(testo, azione);
	}
	
    private double valore;
    private Point2D coordCentro;
    private ArrayList<Forma> formaGrafica;
    private Forma formaComputazionale;
    
    private Moneta(double valore, Point2D coordCentro, ArrayList<Forma> formaGrafica, Forma formaComputazionale) {
    	this.valore = valore;
    	this.coordCentro = coordCentro;
    	this.formaGrafica = formaGrafica;
    	this.formaComputazionale = formaComputazionale;
    }
    
    public double getValore() {
        return valore;
    }
    
    public ArrayList<Forma> getFormaGrafica() {
		return formaGrafica;
	}
    
    public Forma getFormaComputazionale() {
		return formaComputazionale;
	}
    
    private static Moneta creaMonetaGrande(Double valore, Point2D coordCentro, AffineTransform at) {
    	ArrayList<Forma> formaGrafica = new ArrayList<>();
    	Forma formaComputazionale;
    	
    	RoundRectangle2D forma = new RoundRectangle2D.Float((float)(coordCentro.getX()-LARGHEZZA_MONETE_GRANDI/2.), (float)(coordCentro.getY()-ALTEZZA_MONETE_GRANDI/2.),
    			LARGHEZZA_MONETE_GRANDI, ALTEZZA_MONETE_GRANDI, 18, 18);
    	
    	formaGrafica.add(new Forma(at.createTransformedShape(forma), COLORE_MONETA_GRANDE));
    	formaComputazionale = new Forma(at.createTransformedShape(forma).getBounds2D(), COLORE_MONETA_GRANDE);
    	return new Moneta(valore, coordCentro, formaGrafica, formaComputazionale);
    }
    
    private static Moneta creaMonetaPiccola(Double valore, Point2D coordCentro, AffineTransform at) {
    	ArrayList<Forma> formaGrafica = new ArrayList<>();
    	Forma formaComputazionale;
    	
    	RoundRectangle2D forma = new RoundRectangle2D.Float((float)(coordCentro.getX()-LARGHEZZA_MONETE_PICCOLE/2.), (float)(coordCentro.getY()-ALTEZZA_MONETE_PICCOLE/2.),
    			LARGHEZZA_MONETE_PICCOLE, ALTEZZA_MONETE_PICCOLE, 18, 18);
    	
    	formaGrafica.add(new Forma(at.createTransformedShape(forma), COLORE_MONETA_PICCOLA));
    	formaComputazionale = new Forma(at.createTransformedShape(forma).getBounds2D(), COLORE_MONETA_PICCOLA);
    	return new Moneta(valore, coordCentro, formaGrafica, formaComputazionale);
    }
    
    private static Moneta creaCiliegia(Double valore, Point2D coordCentro, AffineTransform at) {
    	ArrayList<Forma> formaGrafica = new ArrayList<>();
    	Forma formaComputazionale;
    	
    	Arc2D ciliegiaSx = new Arc2D.Float();
    	ciliegiaSx.setArcByCenter(coordCentro.getX() - SIZE_CILIEGIA_GRAFICA/4.,
    			coordCentro.getY(),
    			SIZE_CILIEGIA_GRAFICA/4., 0, 360, Arc2D.CHORD);
  
    	Arc2D eliminaParteCiliegiaSx = new Arc2D.Float();
    	eliminaParteCiliegiaSx.setArcByCenter(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30,
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30,
    			SIZE_CILIEGIA_GRAFICA/3., 90, 110, Arc2D.PIE);
    	
    	Arc2D ciliegiaDx = new Arc2D.Float();
    	ciliegiaDx.setArcByCenter(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30,
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30,
    			SIZE_CILIEGIA_GRAFICA/4., 0, 360, Arc2D.CHORD);
    	
    	Path2D riflessoCiliegiaSx = new Path2D.Float();
    	riflessoCiliegiaSx.moveTo(coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/2.-1/16.), coordCentro.getY());
    	riflessoCiliegiaSx.curveTo(coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/2.-1/16.),
    			coordCentro.getY() + 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getX() - SIZE_CILIEGIA_GRAFICA/4. - 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getX() - SIZE_CILIEGIA_GRAFICA/4., coordCentro.getY() + SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.));
    	riflessoCiliegiaSx.curveTo(coordCentro.getX() - SIZE_CILIEGIA_GRAFICA/4. - 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1.5/8.),
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/2.-1/16.),
    			coordCentro.getY() + 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1.5/8.),
    			coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/2.-1/16.), coordCentro.getY());
    	riflessoCiliegiaSx.closePath();
    	
    	Path2D riflessoCiliegiaDx = new Path2D.Float();
    	riflessoCiliegiaDx.moveTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 - SIZE_CILIEGIA_GRAFICA*3/16., coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30);
    	riflessoCiliegiaDx.curveTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 - SIZE_CILIEGIA_GRAFICA*3/16.,
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30. + 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 - 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30 + SIZE_CILIEGIA_GRAFICA*3/16.,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30, coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30 + SIZE_CILIEGIA_GRAFICA*3/16.);
    	riflessoCiliegiaDx.curveTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 - 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1.5/8.),
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30 + SIZE_CILIEGIA_GRAFICA*3/16.,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 - SIZE_CILIEGIA_GRAFICA*3/16.,
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30 + 0.552284749831*SIZE_CILIEGIA_GRAFICA*(1/4.-1.5/8.),
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 - SIZE_CILIEGIA_GRAFICA*3/16., coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30);
    	riflessoCiliegiaDx.closePath();
    	
    	Path2D gamboSx = new Path2D.Float();
    	gamboSx.moveTo(coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.), coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/8.);
    	gamboSx.curveTo(coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/8. - SIZE_CILIEGIA_GRAFICA/8.,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2. - SIZE_CILIEGIA_GRAFICA/4.,
    			coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2. ,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2., coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2.);
    	gamboSx.lineTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2, coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2. + SIZE_CILIEGIA_GRAFICA/16.);
    	
    	gamboSx.curveTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2 - SIZE_CILIEGIA_GRAFICA/4.,
    			coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2. + SIZE_CILIEGIA_GRAFICA/16.,
    			coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.),
    			coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/8. + SIZE_CILIEGIA_GRAFICA/8.- SIZE_CILIEGIA_GRAFICA/8. ,
    			coordCentro.getX() - SIZE_CILIEGIA_GRAFICA*(1/4.-1/16.), coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/8. );
    	gamboSx.closePath();
    	
    	Path2D gamboDx = new Path2D.Float();
    	gamboDx.moveTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 , coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30  - SIZE_CILIEGIA_GRAFICA/8.);
    	gamboDx.curveTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30,
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30 - SIZE_CILIEGIA_GRAFICA/8. - SIZE_CILIEGIA_GRAFICA/8.,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2. - SIZE_CILIEGIA_GRAFICA/4.,
    			coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2. ,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2., coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2.);
    
    	gamboDx.lineTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2, coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2. + SIZE_CILIEGIA_GRAFICA/16.);
    	
    	gamboDx.curveTo(coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2 - SIZE_CILIEGIA_GRAFICA/4.,
    			coordCentro.getY() - SIZE_CILIEGIA_GRAFICA/2. + SIZE_CILIEGIA_GRAFICA/16.,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 + SIZE_CILIEGIA_GRAFICA/16.,
    			coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30  - SIZE_CILIEGIA_GRAFICA/8. ,
    			coordCentro.getX() + SIZE_CILIEGIA_GRAFICA/2.*11/30 /*+ SIZE_CILIEGIA_GRAFICA/16.*/, coordCentro.getY() + SIZE_CILIEGIA_GRAFICA/2.*13/30  - SIZE_CILIEGIA_GRAFICA/8.);
    	gamboDx.closePath();
    	
    	formaGrafica.add(new Forma(at.createTransformedShape(ciliegiaSx), COLORE_CILIEGIA));
    	formaGrafica.add(new Forma(at.createTransformedShape(eliminaParteCiliegiaSx), PnlAreaGioco.COLORE_SFONDO));
    	formaGrafica.add(new Forma(at.createTransformedShape(ciliegiaDx), COLORE_CILIEGIA));
    	formaGrafica.add(new Forma(at.createTransformedShape(riflessoCiliegiaSx), COLORE_RIFLESSO_CILIEGIA));
    	formaGrafica.add(new Forma(at.createTransformedShape(riflessoCiliegiaDx), COLORE_RIFLESSO_CILIEGIA));
    	formaGrafica.add(new Forma(at.createTransformedShape(gamboSx), COLORE_GAMBO_CILIEGIA));
    	formaGrafica.add(new Forma(at.createTransformedShape(gamboDx), COLORE_GAMBO_CILIEGIA));
    	formaComputazionale = new Forma(at.createTransformedShape(new Rectangle2D.Float((float)(coordCentro.getX() - SIZE_CILIEGIA_COMPUTAZIONALE/2.),
    			(float)(coordCentro.getY() - SIZE_CILIEGIA_COMPUTAZIONALE/2.), SIZE_CILIEGIA_COMPUTAZIONALE, SIZE_CILIEGIA_COMPUTAZIONALE)),
    			COLORE_CILIEGIA);
    	return new Moneta(valore, coordCentro, formaGrafica, formaComputazionale);
    }

}