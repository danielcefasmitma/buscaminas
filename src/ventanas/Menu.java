package ventanas;

import buscaminas.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {

    private JButton btnFacil;
    private JButton btnMedio;
    private JButton btnDificil;

    public Menu() {
        setTitle("Buscaminas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTitulo = new JLabel("Buscaminas", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem menuItem = new JMenuItem("Registrar Jugador");
        menu.add(menuItem);
        menuBar.add(menu);

        JPanel panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 100, 20, 100); // Espaciado entre los botones

        btnFacil = new JButton("Fácil");
        btnFacil.setBackground(Color.CYAN);
        btnFacil.setPreferredSize(new Dimension(150, 40));
        btnFacil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscaminasGUI(5, 8);
            }
        });
        panelBotones.add(btnFacil, gbc);

        gbc.gridy++;
        btnMedio = new JButton("Medio");
        btnMedio.setBackground(Color.YELLOW);
        btnMedio.setPreferredSize(new Dimension(150, 40));
        btnMedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscaminasGUI(15, 12);
            }
        });
        panelBotones.add(btnMedio, gbc);

        gbc.gridy++;
        btnDificil = new JButton("Difícil");
        btnDificil.setBackground(Color.RED);
        btnDificil.setPreferredSize(new Dimension(150, 40));
        btnDificil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BuscaminasGUI(20, 16);
            }
        });
        panelBotones.add(btnDificil, gbc);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        setJMenuBar(menuBar);
        add(panelPrincipal);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        }
        );
    }
}
