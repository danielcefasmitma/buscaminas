package buscaminas;

public class Buscaminas {
	private Tablero tablero;
	private static final int NUMERO_MINAS = 5;
	private static final int DIMENSION = 8;
	GestorIO gestorIO = new GestorIO();
	
	public Buscaminas() {
		tablero = new Tablero(NUMERO_MINAS, DIMENSION);
	}
	
	public void jugar() {
		tablero.mostrar();
		Posicion posicion ;
		do {
			posicion = this.enPosicion();
			if(tablero.estadoInicial()) {
				tablero.inicializar(posicion);
			}
			tablero.descubrirCasilla(posicion, tablero);
			tablero.mostrar();
		}while(!tablero.todasCasillasDescubiertas() && !tablero.hayExplosion(posicion) );
		
		if(tablero.todasCasillasDescubiertas()) {
			gestorIO.out("Ganaste la partida. :)");
		}else {
			gestorIO.out("Perdiste la partida. :(");
		}
	}
	
	private Posicion enPosicion() {
		int fila;
		int columna;	
		do {	
			gestorIO.out("Fila?: ");
			fila = gestorIO.inInt()-1;
			gestorIO.out("Columna?:");
			columna = gestorIO.inInt()-1;
			if(!tablero.esPosicionValida(new Posicion(fila, columna))) {
				gestorIO.out("Pon una posicion válida");
			}
		}while(!tablero.esPosicionValida(new Posicion(fila, columna)));
		
		return new Posicion(fila, columna);
	}
	
	public static void main(String[] args) {
		new Buscaminas().jugar();
	}
}
