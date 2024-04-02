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
    
    public Jugador(String nombre) {
          
        this.nombre = nombre;
        archivo = obtenerArchivoJugador();
    }

    public File obtenerArchivoJugador() {
        
        File archivoJugador;
        if (nombreJugadorExiste()) {
            archivoJugador = getArchivoJugador();
        } else {
            archivoJugador = nuevoArchivoJugador();
        }
        return archivoJugador;
    }

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

    public void registrarMovimiento(Posicion posicion) {
        try {
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo, true)));
            br.write(nombre + ": descubrio casilla " + "["+posicion.fila()+","+posicion.columna()+"]");
            br.newLine();
            br.close();
        } catch (Exception e) {

        }
    }
}
