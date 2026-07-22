/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package puk.aplicacion;

import puk.modelo.GestionBiblioteca;
import puk.modelo.Libro;
import puk.persistencia.PersistenciaBiblioteca;

public class Main {

    public static void main(String[] args) {

        PersistenciaBiblioteca persistencia =
                new PersistenciaBiblioteca("biblioteca.txt");

        GestionBiblioteca biblioteca = persistencia.cargar();

        System.out.println("=== Biblioteca al iniciar ===");
        System.out.println(biblioteca.mostrarRegistro());

        if (biblioteca.getTotalLibros() == 0) {

            System.out.println("La biblioteca está vacía.");
            System.out.println("Añadiendo libros de prueba...\n");

            biblioteca.añadirLibro(new Libro(
                    "Mort",
                    "Terry Pratchett",
                    "9788497592475",
                    1987));

            biblioteca.añadirLibro(new Libro(
                    "El imperio final",
                    "Brandon Sanderson",
                    "9788498726138",
                    2006));

            biblioteca.añadirLibro(new Libro(
                    "El Hobbit",
                    "J. R. R. Tolkien",
                    "9788445073803",
                    1937));

            persistencia.guardar(biblioteca);

            System.out.println("Libros guardados correctamente.");
        }

        System.out.println("\n=== Biblioteca al finalizar ===");
        System.out.println(biblioteca.mostrarRegistro());
    }
}