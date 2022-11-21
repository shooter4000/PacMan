package it.unibs.pajc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PnlAreaGioco extends JPanel {
	
	PacmanModel model = new PacmanModel();
	
	public PnlAreaGioco() {

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double scaleFactor = Math.min(getWidth(), getHeight())/1000.;
		
				g2.scale(scaleFactor, -scaleFactor);
		g2.translate(500.,-500.);
		
		g2.setStroke(new BasicStroke(5));
		
		g2.setColor(Color.black);
		g2.fillRect(-500,-500, 1000,1000);
		
		g2.setColor(model.getMappa().getColoreBordi());
		g2.draw(model.getMappa().getBordi());
		g2.setColor(Color.white);
		g2.fillRect(500, -1000, 2000, 2000);
		for(Ostacolo o: model.getMappa().getElencoOstacoli()) {
			g2.setColor(o.getColore());
			g2.draw(o.getShape());
		}
		
		for(Moneta o: model.getMappa().getMonete().values()) {
			g2.setColor(o.getColore());
			g2.fill(o.getForma());
		}
	}
	
}
