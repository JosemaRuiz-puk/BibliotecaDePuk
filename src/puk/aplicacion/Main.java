/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package puk.aplicacion;

import puk.modelo.GestionBiblioteca;
import puk.persistencia.PersistenciaBiblioteca;
import puk.vista.VentanaPrincipal;
import puk.vista.VentanaCarga;

public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new VentanaCarga().setVisible(true);
        });

    }
}
