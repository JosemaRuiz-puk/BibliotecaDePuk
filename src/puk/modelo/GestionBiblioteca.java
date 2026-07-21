/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puk.modelo;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author utpuk
 */
public class GestionBiblioteca {

    private ArrayList<Libro> libros;

    public GestionBiblioteca() {
        this.libros = new ArrayList<>();
    }

    public String añadirLibro(Libro l) {
        libros.add(l);
        return "Libro añadido al registro";
    }

    public ArrayList<Libro> buscarLibroPorAutor(String autor) {
        ArrayList<Libro> resultado = new ArrayList<>();

        for (Libro libro : libros) {
            if (libro.getAutor().equalsIgnoreCase(autor)) {
                resultado.add(libro);
            }
        }

        resultado.sort((l1, l2)
                -> Integer.compare(
                        l1.getAnioPublicacion(),
                        l2.getAnioPublicacion()
                )
        );

        return resultado;
    }

    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }

        return null;
    }

    public String eliminarLibro(int id) {

        Iterator<Libro> it = libros.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getId() == id) {
                it.remove();
                return "Libro eliminado del registro";
            }

        }
        return "Libro no encontrado en el registro";

    }

    public String mostrarRegistro() {
        if (libros.isEmpty()) {
            return "No hay registros";
        }

        ArrayList<Libro> librosOrdenados = new ArrayList<>(libros);

        librosOrdenados.sort((l1, l2)
                -> Integer.compare(l1.getId(), l2.getId())
        );

        StringBuilder resultado = new StringBuilder();

        for (Libro libro : librosOrdenados) {
            resultado.append(libro)
                    .append("\n")
                    .append("----------------------------------------")
                    .append("\n");
        }

        return resultado.toString();
    }

}
