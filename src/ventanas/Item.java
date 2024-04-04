/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ventanas;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Daniel
 */
public class Item extends JPanel{
    public Item(){
        setLayout(new GridBagLayout());
        
        
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(20,25, 20, 25);
        JLabel lbNumMinas = new JLabel("Minas");
        lbNumMinas.setSize(new Dimension(100,50));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;
        add(lbNumMinas, constraints);
        constraints.weighty = 0.0;
        
        JLabel lbCarita = new JLabel("Carita");
        lbCarita.setSize(new Dimension(100,50));
        constraints.gridx = 1;//POSICION QUE OCUPA COMPONENTE
        constraints.gridy = 0;//POSICION QUE OCUPA COMPONENTE
        constraints.gridwidth = 1;//ESTIRA FILA
        constraints.gridheight = 1;//ESTIRA COLUMNA
        constraints.fill = GridBagConstraints.NORTH;
        constraints.weighty = 1.0; //ESTIRA COMPONENTE
        add(lbCarita, constraints);
        constraints.weighty = 0.0;
        
        JLabel lbTemporizador = new JLabel("Temporizador");
        lbTemporizador.setSize(new Dimension(100,50));
        constraints.gridx = 2;
        constraints.gridy = 0;    
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weighty = 1.0;
        add(lbTemporizador, constraints);
       
    }
    
    
    public static void main(String args[]){
        JFrame marco = new JFrame();
        marco.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        marco.add(new Item());
        marco.setMinimumSize(new Dimension(400,100));
        marco.setVisible(true);
    }
}
