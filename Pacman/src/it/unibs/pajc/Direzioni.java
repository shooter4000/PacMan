package it.unibs.pajc;

public enum Direzioni {

	SOPRA(0,1, 90), DESTRA(1,0, 0), SOTTO(0,-1, 270), SINISTRA(-1,0, 180);
	
	private int versoreX;
	private int versoreY;
	private double angolo;
	
	private Direzioni(int versoreX, int versoreY, double angolo) {
		this.versoreX = versoreX;
		this.versoreY = versoreY;
		this.angolo = angolo;
	}
	
	public int getVersoreX() {
		return versoreX;
	}
	
	public int getVersoreY() {
		return versoreY;
	}
	
	public double getAngolo() {
		return angolo;
	}
}
