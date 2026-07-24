/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package puk.aplicacion;

import puk.modelo.GestionBiblioteca;
import puk.persistencia.PersistenciaBiblioteca;
import puk.vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        PersistenciaBiblioteca persistencia
                = new PersistenciaBiblioteca("biblioteca.txt");

        GestionBiblioteca biblioteca = persistencia.cargar();

        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal(biblioteca,persistencia).setVisible(true);
        });

    }
}
