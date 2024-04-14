package buscaminas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class Jugador {

    private String nombre;
    private String rutaDirectorio = System.getProperty("user.dir") + "/src/logJugadores/";
    private static File archivo;
    private int numeroClicks;
    private int tiempoJugado;
    private String estadoFinalPartida;
    
    public Jugador(String nombre) {
        this.nombre = nombre;
        archivo = obtenerArchivoJugador();
        numeroClicks = 0;
        tiempoJugado = 0;
        estadoFinalPartida = "";
    }

    //En caso de que el archivo del jugador existe se trabaja con ese archivo.
    //si no existe el archivo entonces se crea uno nuevo con el nombre del jugador.
    public File obtenerArchivoJugador() {       
        File archivoJugador;
        if (nombreJugadorExiste()) {
            archivoJugador = getArchivoJugador();
        } else {
            archivoJugador = nuevoArchivoJugador();
        }
        return archivoJugador;
    }
    
    //Verifica si el nombre del jugador como archivo txt en la carpeta logJugadores
    public boolean nombreJugadorExiste() {     
        File directorio = new File(rutaDirectorio);
        File[] files = directorio.listFiles();   
        boolean hayArchivoJugador = false;
        for (int i = 0; files != null && !hayArchivoJugador && i < files.length; i++) {
            String nombreJugador = nombre + ".txt";
            hayArchivoJugador = nombreJugador.equals(files[i].getName());
        }
        return hayArchivoJugador;
    }
    
    //En caso no hay un archivo con el nombre del jugador, se crea uno nuevo en la carpeta logJugadores
    public File nuevoArchivoJugador() {
        File archivo = null;
        try {
            archivo = new File(rutaDirectorio + nombre + ".txt");
            archivo.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return archivo;
    }
    
    //Aqui devuelve el archivo del jugador.
    public File getArchivoJugador() {
        File directorio = new File(rutaDirectorio);
        File[] files = directorio.listFiles();
       
        boolean hayArchivoJugador=false;
        int i = 0;
        String nombreJugador = nombre+".txt";
        while(!hayArchivoJugador && i < files.length){      
            hayArchivoJugador = nombreJugador.equals(files[i].getName());
            if(!hayArchivoJugador){
                i++;
            }
        }
        return files[i];
    }
    
    //Despues de que la partida del usuario haya terminado se escriben los datos de la partida en un documento txt.
    //Estos archivos se encuentran en logJugadores.
    public void registrarDatosDePartida(){
        try {
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo, false)));
            br.write(nombre);
            br.newLine();
            br.write(""+getNumeroClicks());
            br.newLine();
            br.write(""+getTiempoJugado());
            br.newLine();
            br.write(estadoFinalPartida);
            br.close();
        } catch (Exception e) {
            System.out.println("Error al escribir datos del jugador");
        }
    }
    
    public void registrarClick(){
        numeroClicks++;
    }
    public int getNumeroClicks(){
        return numeroClicks;
    }

    public void registrarTiempo(int tiempo) {
        tiempoJugado = tiempo;
    }
    
    public int getTiempoJugado(){
        return tiempoJugado;
    }
       
    public void perder(){
        estadoFinalPartida = "Perdió";
    }
    
    public void ganar(){
        estadoFinalPartida = "Ganó";
    }
    
}
