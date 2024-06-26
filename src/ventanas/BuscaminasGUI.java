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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscaminasGUI extends JFrame {

    private Tablero tablero;
    private Jugador jugador;
    private JButton[][] botones;

    public BuscaminasGUI(int numeroMinas, int dimension) {
        tablero = new Tablero(numeroMinas, dimension);
        jugador = new Jugador(JOptionPane.showInputDialog("Cual es tu nombre?:"));
        Registro registro = new Registro();
        registro.leerArchivos();

        setTitle("Buscaminas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(dimension, dimension));
        botones = new JButton[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setPreferredSize(new Dimension(50, 50));
            }
        }
        
        
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            private int n;

            @Override
            public void run() {
                n++;
                System.out.println(n);
                jugador.registrarTiempo(n);

            }
        };
        
        //en caso de que esta ventana de BuscaminasGUI se cierre, sin finalizar partida.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                timer.cancel();
                setVisible(false);
                registro.setVisible(true);
            }
        });
        
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int fila = i;
                int columna = j;
                Posicion posicion = new Posicion(fila, columna);

                botones[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!tablero.todasCasillasDescubiertas() && !tablero.hayExplosion(posicion)) {
                                if (tablero.estadoInicial()) {
                                    tablero.inicializar(posicion);
                                    timer.schedule(timerTask, 1000, 1000);
                                }
                                tablero.descubrirCasilla(posicion, tablero);
                                jugador.registrarClick();
                                actualizarTablero(dimension);
                            }

                            if (tablero.todasCasillasDescubiertas()) {
                                timer.cancel();
                                JOptionPane.showMessageDialog(null, "Ganaste la partida. :)");
                                jugador.ganar();
                                jugador.registrarDatosDePartida();
                                registro.leerArchivos();
                                registro.setVisible(true);
                                cerrarVentana();

                            } else if (tablero.hayExplosion(posicion)) {
                                timer.cancel();
                                JOptionPane.showMessageDialog(null, "Perdiste la partida. :(");
                                jugador.perder();
                                jugador.registrarDatosDePartida();
                                registro.leerArchivos();
                                registro.setVisible(true);
                                cerrarVentana();
                            }

                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            tablero.colocarBandera(posicion);
                            actualizarTablero(dimension);
                        }
                    }
                });

                panel.add(botones[i][j]);
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
                JButton boton = botones[i][j];
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
        String rutaImagen = "";

        if (!casilla.estaCubierta()) {
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
        } else if (casilla.hayBandera()) {
            rutaImagen = "/imagenes/bandera.png";
            InputStream input = getClass().getResourceAsStream(rutaImagen);
            BufferedImage bufferImage = ImageIO.read(input);
            Icon icono = new ImageIcon(bufferImage);
            boton.setIcon(icono);
        } else if (!casilla.hayBandera()) {
            boton.setIcon(null);
        }

    }

    public void cerrarVentana() {
        setVisible(false); // Oculta la ventana
        dispose(); // Libera los recursos asociados a la ventana
    }
    
}
