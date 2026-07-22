/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puk.persistencia;

import java.nio.file.Path;
import java.nio.file.Paths;
import puk.modelo.GestionBiblioteca;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.IOException;
import puk.modelo.Libro;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;

public class PersistenciaBiblioteca {

    private Path rutaArchivo;

    public PersistenciaBiblioteca(String nombreArchivo) {
        this.rutaArchivo = Paths.get(nombreArchivo);
    }

    public GestionBiblioteca cargar() {

        GestionBiblioteca biblioteca = new GestionBiblioteca();

        if (!Files.exists(rutaArchivo)) {
            return biblioteca;
        }

        try (BufferedReader lector = Files.newBufferedReader(rutaArchivo)) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";");
                //Reconstruir el dato con las lineas del archivo

                int id = Integer.parseInt(datos[0]);
                String titulo = datos[1];
                String autor = datos[2];
                String isbn = datos[3];
                int anioPublicacion = Integer.parseInt(datos[4]);
                boolean original = Boolean.parseBoolean(datos[5]);
                boolean fisico = Boolean.parseBoolean(datos[6]);

                Libro libro = new Libro(
                        id,
                        titulo,
                        autor,
                        isbn,
                        anioPublicacion,
                        original,
                        fisico
                );

                biblioteca.añadirLibro(libro);

            }

        } catch (IOException e) {
            System.out.println("Error al cargar la biblioteca.");
        }

        return biblioteca;
    }

    public void guardar(GestionBiblioteca biblioteca) {

        try (BufferedWriter escritor = Files.newBufferedWriter(rutaArchivo, StandardCharsets.UTF_8)) {

            for (Libro libro : biblioteca.getLibros()) {

                escritor.write(
                        libro.getId() + ";"
                        + libro.getTitulo() + ";"
                        + libro.getAutor() + ";"
                        + libro.getIsbn() + ";"
                        + libro.getAnioPublicacion() + ";"
                        + libro.isOriginal() + ";"
                        + libro.isFisico()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar la biblioteca.");
        }
    }

}
