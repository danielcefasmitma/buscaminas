package buscaminas;

public class Mina {
	private boolean explosion;
	
	public Mina() {
		explosion = false;
	}
	
	public void explotarEnTodasCasillas(Casilla[][] casillas) {
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas.length; j++) {
				if(casillas[i][j].hayMina()) {
					casillas[i][j].quitarCubierta();
				}
			}
			new GestorIO().out("\n");
		}

		explosion = true;
	}
	
	public boolean hayExplosion() {
		return explosion;
	}
}
