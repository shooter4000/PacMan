package it.unibs.pajc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PnlAreaGioco extends JPanel implements KeyListener{
	
	PacmanModel model = new PacmanModel();
	
	public PnlAreaGioco() {
		Timer t = new Timer(10, e -> {
			model.stepNext();
			repaint();
		});
		t.start();
		
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(this);
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
		
		for(Personaggio p: model.getElencoPersonaggi()) {
			p.getFormaGrafica().forEach(x -> {
				g2.setColor(x.getColore());
				g2.fill(x.getForma());
			});
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Pacman p = model.getPacman();
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: p.cambiaDirezione(Direzioni.SINISTRA); break;
		case KeyEvent.VK_RIGHT: p.cambiaDirezione(Direzioni.DESTRA); break;
		case KeyEvent.VK_UP: p.cambiaDirezione(Direzioni.SOPRA); break;
		case KeyEvent.VK_DOWN: p.cambiaDirezione(Direzioni.SOTTO); break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
