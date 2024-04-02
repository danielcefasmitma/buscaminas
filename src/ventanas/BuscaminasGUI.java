package ventanas;

import buscaminas.*;
import buscaminas.Casilla;
import buscaminas.Jugador;
import buscaminas.Posicion;
import buscaminas.Tablero;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class BuscaminasGUI extends JFrame {

    private Tablero tablero;
    private Jugador jugador;
    private JButton[][] buttons;

    public BuscaminasGUI(int numeroMinas, int dimension) {
        tablero = new Tablero(numeroMinas, dimension);
        jugador = new Jugador(JOptionPane.showInputDialog("Cual es tu nombre?:"));

        setTitle("Buscaminas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(dimension, dimension));
        buttons = new JButton[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(50, 50));
            }
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int fila = i;
                int columna = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Posicion posicion = new Posicion(fila, columna);
                        if (!tablero.todasCasillasDescubiertas() && !tablero.hayExplosion(posicion)) {
                            if (tablero.estadoInicial()) {
                                tablero.inicializar(posicion);
                            }
                            tablero.descubrirCasilla(posicion, tablero);
                            jugador.registrarMovimiento(posicion);

                            actualizarTablero(dimension);

                        }

                        if (tablero.todasCasillasDescubiertas()) {
                            JOptionPane.showMessageDialog(null, "Ganaste la partida. :)");

                        } else if (tablero.hayExplosion(posicion)) {
                            JOptionPane.showMessageDialog(null, "Perdiste la partida. :(");
                        }
                    }
                });
                panel.add(buttons[i][j]);
            }
        }
        add(panel);
        pack();
        setVisible(true);
    }

    private void actualizarTablero(int dimension) {
        Casilla[][] casillas = tablero.getCasillas();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                JButton boton = buttons[i][j];
                Casilla casilla = casillas[i][j];
                try {
                    establecerIconos(casilla, boton);
                } catch (IOException e) {
                    System.out.println("Error Entrada/Salida");
                }
            }
        }
    }

    public void establecerIconos(Casilla casilla, JButton boton) throws IOException {
        if (!casilla.estaCubierta()) {
            String rutaImagen = "";
            if (casilla.hayNumero()) {
                switch (casilla.getNumeroMinasAlrededor()) {
                    case 1:
                        rutaImagen = "/imagenes/1.png";
                        break;
                    case 2:
                        rutaImagen = "/imagenes/2.png";
                        break;
                    case 3:
                        rutaImagen = "/imagenes/3.png";
                        break;
                    case 4:
                        rutaImagen = "/imagenes/4.png";
                        break;
                    case 5:
                        rutaImagen = "/imagenes/5.png";
                        break;

                }
                InputStream input = getClass().getResourceAsStream(rutaImagen);
                BufferedImage bufferImage = ImageIO.read(input);
                Icon icono = new ImageIcon(bufferImage);
                boton.setIcon(icono);
                boton.setDisabledIcon(icono);
                boton.setEnabled(false);
            } else if (casilla.hayExplosion()) {
                rutaImagen = "/imagenes/mina.png";
                InputStream input = getClass().getResourceAsStream(rutaImagen);
                BufferedImage bufferImage = ImageIO.read(input);
                Icon icono = new ImageIcon(bufferImage);
                boton.setIcon(icono);
                boton.setDisabledIcon(icono);
                boton.setEnabled(false);
            } else if (casilla.estaVacio()) {
                boton.setEnabled(false);
            }
        }

    }

}
