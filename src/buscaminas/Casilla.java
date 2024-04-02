package buscaminas;

public class Casilla {
	private boolean cubierta;
	private Mina mina;
	private int numeroMinasAlrededor;
	private boolean recorrida;

	public Casilla() {
		cubierta = true;
		recorrida = false;
		mina = null;
		numeroMinasAlrededor = 0;
	}

	public void colocarMina(Mina mina) {
		this.mina = mina;
	}

	public void colocarNumero(int numeroMinasAlrededor) {
		this.numeroMinasAlrededor = numeroMinasAlrededor;
	}
	
	public void quitarCubierta() {
		cubierta = false;
	}
	
	public void marcar() {
		recorrida = true;
	}
	
	public boolean estaCubierta() {
		return cubierta;
	}

	public boolean hayMina() {
		return mina != null;
	}

	public boolean hayNumero() {
		return numeroMinasAlrededor > 0;
	}

	public boolean hayExplosion() {
		return hayMina() ? mina.hayExplosion() : false;
	}

	public boolean estaVacio() {
		return !hayMina() && !hayNumero();
	}

	public boolean recorrida() {
		return recorrida;
	}

	public void mostrar() {
		GestorIO gestorIO = new GestorIO();
		String item = "#";
		if (!estaCubierta()) {
			if (hayNumero()) {
				item = "" + numeroMinasAlrededor;
			} else if (hayExplosion()) {
				item = "*";
			} else if (estaVacio()) {
				item = " ";
			}
		}

		gestorIO.out("[" + item + "]");

	}
}
