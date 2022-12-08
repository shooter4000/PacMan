package it.unibs.pajc;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Fantasma extends Personaggio{

	private Color coloreFantasma;
	private static Color COLORE_PUPILLA_OCCHI = Color.white;
	private static Color COLORE_IRIDE_OCCHI = Color.blue;
	private static double RAGGIO_PUPILLE = SIZE_FORMA_GRAFICA/6.;
	private static double DELTA_X_IRIDI = RAGGIO_PUPILLE/2.;
	private static double DELTA_Y_IRIDI = RAGGIO_PUPILLE/2.;
	
	private AffineTransform at = new AffineTransform();
	
	public Fantasma(Color colore, Point2D posCentro, Direzioni direzione) {
		super(posCentro, direzione);
		coloreFantasma = colore;
		at.translate(posCentro.getX(), posCentro.getY());
		costruisciFantasma();
	}

	
	private void costruisciFantasma() {
		setFormaComputazionale(costruisciCorpoFantasma(SIZE_FORMA_COMPUTAZIONALE));
		resettaFormaGrafica();
		addFormaGrafica(new Forma(costruisciCorpoFantasma(SIZE_FORMA_GRAFICA), coloreFantasma));
		addFormaGrafica(new Forma(costruisciPupilleFantasma(SIZE_FORMA_GRAFICA), COLORE_PUPILLA_OCCHI));
		addFormaGrafica(new Forma(costruisciIridiFantasma(SIZE_FORMA_GRAFICA), COLORE_IRIDE_OCCHI));
		
	}
	
	private Shape costruisciIridiFantasma(int dimensione) {
		Path2D iridi = new Path2D.Float();
		Direzioni direzione = getDirezione();
		Ellipse2D irideDx = new Ellipse2D.Float();
		double xCentroDx = dimensione/2.*1.25/3.;
		double yCentroDx = dimensione/2.*1/3.;
		double raggio = dimensione/12.;
		irideDx.setFrameFromCenter(xCentroDx+DELTA_X_IRIDI *direzione.getVersoreX(),
				yCentroDx+DELTA_Y_IRIDI *direzione.getVersoreY(),
				xCentroDx+raggio+DELTA_X_IRIDI *direzione.getVersoreX(),
				yCentroDx+raggio+DELTA_Y_IRIDI *direzione.getVersoreY());
		
		Ellipse2D irideSx = new Ellipse2D.Float();
		irideSx.setFrameFromCenter(-xCentroDx+DELTA_X_IRIDI *direzione.getVersoreX(),
				yCentroDx+DELTA_Y_IRIDI *direzione.getVersoreY(),
				-xCentroDx+raggio+DELTA_X_IRIDI *direzione.getVersoreX(),
				yCentroDx+raggio+DELTA_Y_IRIDI *direzione.getVersoreY());
		
		iridi.append(irideDx, false);
		iridi.append(irideSx, false);
		return at.createTransformedShape(iridi);
		
	}

	private Shape costruisciPupilleFantasma(int dimensione) {
		Path2D occhi = new Path2D.Float();
		Ellipse2D occhioDx = new Ellipse2D.Float();
		double xCentroDx = dimensione/2.*1.25/3.;
		double yCentroDx = dimensione/2.*1/3.;
		double raggio = dimensione/6.;
		occhioDx.setFrameFromCenter(xCentroDx, yCentroDx, xCentroDx+raggio, yCentroDx+raggio);
		
		Ellipse2D occhioSx = new Ellipse2D.Float();
		occhioSx.setFrameFromCenter(-xCentroDx, yCentroDx, -xCentroDx+raggio, yCentroDx+raggio);
		
		occhi.append(occhioDx, false);
		occhi.append(occhioSx, false);
		return at.createTransformedShape(occhi);
	}


	private Shape costruisciCorpoFantasma(int dimensione) {
		Path2D fantasma = new Path2D.Float();
		fantasma.moveTo(-dimensione/2., 0);
		
		fantasma.curveTo(-dimensione/2., 0.552284749831 * dimensione/2., 
				-0.552284749831 * dimensione/2., dimensione/2.,
				0, dimensione/2.);
		
		fantasma.curveTo(0.552284749831 * dimensione/2, dimensione/2., 
				dimensione/2., 0.552284749831 * dimensione/2.,
				dimensione/2., 0);
		fantasma.lineTo(dimensione/2., -dimensione/2.);
		fantasma.lineTo(dimensione/2.*2/3., -dimensione/2.*2/3.);
		fantasma.lineTo(dimensione/2.*1/3., -dimensione/2.);
		fantasma.lineTo(0, -dimensione/2.*2/3.);
		fantasma.lineTo(-dimensione/2.*1/3., -dimensione/2.);
		fantasma.lineTo(-dimensione/2.*2/3., -dimensione/2.*2/3.);
		fantasma.lineTo(-dimensione/2., -dimensione/2.);
		fantasma.closePath();

		return at.createTransformedShape(fantasma);
	}

	@Override
	public void stepNext() {
		// TODO Auto-generated method stub
		muoviFantasma();
		
	}
	private void muoviFantasma() {
		Direzioni direzione = getDirezione();
		Point2D posCentro = getPosCentro();
		posCentro.setLocation(posCentro.getX() + getVelocita() * direzione.getVersoreX(), posCentro.getY() + getVelocita() * direzione.getVersoreY());
		setPosCentro(posCentro);
		costruisciFantasma();
	}
	
	@Override
	public Shape simulaProssimaPosizione() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
