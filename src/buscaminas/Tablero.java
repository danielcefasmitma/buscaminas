package buscaminas;

import java.util.Random;

public class Tablero {

	private int dimension;
	private int numeroMinas;
	private Casilla[][] casillas;
	private boolean estadoInicialTablero;
	private int numCasillasDescubiertas;
	private  Mina mina;

	public Tablero(int numeroMinas, int dimension) {
		estadoInicialTablero = true;
		this.numeroMinas = numeroMinas;
		this.dimension = dimension;
		numCasillasDescubiertas = 0;
		mina = new Mina();
		casillas = new Casilla[dimension][dimension];               
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				casillas[i][j] = new Casilla();
			}
		}
	}

	public void inicializar(Posicion posicion) {
		this.posicionarMinas(posicion.fila(), posicion.columna());
		this.posicionarNumeros();
	}

	private void posicionarMinas(int fila, int columna) {
		Random random = new Random();
		boolean posicionValida = false;
		int numeroMinasPuestas = 0;
		while (numeroMinasPuestas < numeroMinas) {
			int nuevaFila = random.nextInt(dimension);
			int nuevaColumna = random.nextInt(dimension);
			posicionValida = nuevaFila < fila - 1 || fila + 1 < nuevaFila && 
                                         nuevaColumna < columna - 1 || columna + 1 < nuevaColumna;
			if (posicionValida && !casillas[nuevaFila][nuevaColumna].hayMina()) {
				casillas[nuevaFila][nuevaColumna].colocarMina(mina);
				numeroMinasPuestas++;
			}
		}
	}

	private void posicionarNumeros() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (!casillas[i][j].hayMina()) {
					casillas[i][j].colocarNumero(contarMinasAlrededor(i, j));
				}
			}
		}
	}

	private int contarMinasAlrededor(int fila, int columna) {
		int contadorMinas = 0;
		for (int filaActual = fila - 1; filaActual <= fila + 1; filaActual++) {
			for (int columnaActual = columna - 1; columnaActual <= columna + 1; columnaActual++) {
				if (esPosicionValida(new Posicion(filaActual, columnaActual))
						&& hayMina(new Posicion(filaActual, columnaActual))) {
					contadorMinas++;
				}
			}
		}
		return contadorMinas;
	}

	public boolean esPosicionValida(Posicion posicion) {
		return posicion.fila() >= 0 && posicion.fila() < dimension && posicion.columna() >= 0
				&& posicion.columna() < dimension;
	}

	public boolean hayMina(Posicion posicion) {
		return casillas[posicion.fila()][posicion.columna()].hayMina();
	}

	public boolean estadoInicial() {
		return estadoInicialTablero;
	}
	
	public boolean hayExplosion(Posicion posicion) {
		return casillas[posicion.fila()][posicion.columna()].hayExplosion();

	}

	public boolean todasCasillasDescubiertas() {
		return numCasillasDescubiertas + numeroMinas == dimension * dimension;
	}

	public void descubrirCasilla(Posicion posicion, Tablero tablero) {
		estadoInicialTablero = false;
		int fila = posicion.fila();
		int columna = posicion.columna();
		Casilla casilla = casillas[fila][columna];
		if (casilla.estaCubierta() && !casillas[fila][columna].hayBandera()) {
			casilla.quitarCubierta();
			numCasillasDescubiertas++;
			if (casilla.hayMina()) {
				mina.explotarEnTodasCasillas(casillas);
			} else if (casilla.estaVacio()) {
				recorrerAlrededorDeCasillaVacia(posicion, tablero);
			}
		}
	}
	
	private void recorrerAlrededorDeCasillaVacia(Posicion posicion, Tablero tablero) {
	    int fila = posicion.fila();
	    int columna = posicion.columna();
	    
	    if (esPosicionValida(new Posicion(fila, columna)) && !casillas[fila][columna].recorrida()) {
	        Casilla casillaCentral = casillas[fila][columna];
	        casillaCentral.marcar();
	        if (casillaCentral.estaVacio()) {
	            for (int filaActual = fila - 1; filaActual <= fila + 1; filaActual++) {
	                for (int columnaActual = columna - 1; columnaActual <= columna + 1; columnaActual++) {
	                    if (esPosicionValida(new Posicion(filaActual, columnaActual))) {
	                        Casilla casillaAlrededor = casillas[filaActual][columnaActual];
	                        if (!casillaAlrededor.recorrida()) {
	                        	descubrirCasilla(new Posicion(filaActual, columnaActual), tablero);
	                        }
	                    }
	                }
	            }
	        }
	    }
	}	
	
        public Casilla[][] getCasillas(){
            return casillas;
        }
        
	public void mostrar() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				casillas[i][j].mostrar();
			}
			new GestorIO().out("\n");
		}
	}

    public void colocarBandera(Posicion posicion) {
        casillas[posicion.fila()][posicion.columna()].activarBandera();
    }

    public boolean haybandera(Posicion posicion) {
        return casillas[posicion.fila()][posicion.columna()].hayBandera();
    }
    
}
