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

        System.out.println("================================");
        System.out.println(rutaArchivo.toAbsolutePath());
        System.out.println("================================");
    }

    public GestionBiblioteca cargar() {
        
        Libro.reiniciarContador();

        GestionBiblioteca biblioteca = new GestionBiblioteca();

        if (!Files.exists(rutaArchivo)) {
            return biblioteca;
        }

        try (BufferedReader lector = Files.newBufferedReader(
                rutaArchivo,
                StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                String[] datos = linea.split(";");
                Libro libro;

                if (datos.length == 8) {

                    // Formato normal con ID:
                    // id;titulo;autor;isbn;anioPublicacion;anioEdicion;original;fisico
                    int id = Integer.parseInt(datos[0]);
                    String titulo = datos[1];
                    String autor = datos[2];
                    String isbn = datos[3];
                    int anioPublicacion = Integer.parseInt(datos[4]);
                    int anioEdicion = Integer.parseInt(datos[5]);
                    boolean original = Boolean.parseBoolean(datos[6]);
                    boolean fisico = Boolean.parseBoolean(datos[7]);

                    libro = new Libro(
                            id,
                            titulo,
                            autor,
                            isbn,
                            anioPublicacion,
                            anioEdicion,
                            original,
                            fisico
                    );

                } else if (datos.length == 7) {

                    // Formato de importación sin ID:
                    // titulo;autor;isbn;anioPublicacion;anioEdicion;original;fisico
                    String titulo = datos[0];
                    String autor = datos[1];
                    String isbn = datos[2];
                    int anioPublicacion = Integer.parseInt(datos[3]);
                    int anioEdicion = Integer.parseInt(datos[4]);
                    boolean original = Boolean.parseBoolean(datos[5]);
                    boolean fisico = Boolean.parseBoolean(datos[6]);

                    libro = new Libro(
                            titulo,
                            autor,
                            isbn,
                            anioPublicacion,
                            anioEdicion
                    );

                    libro.setOriginal(original);
                    libro.setFisico(fisico);

                } else if (datos.length == 5) {

                    // Formato de importación simplificado:
                    // titulo;autor;isbn;anioPublicacion;anioEdicion
                    String titulo = datos[0];
                    String autor = datos[1];
                    String isbn = datos[2];
                    int anioPublicacion = Integer.parseInt(datos[3]);
                    int anioEdicion = Integer.parseInt(datos[4]);

                    libro = new Libro(
                            titulo,
                            autor,
                            isbn,
                            anioPublicacion,
                            anioEdicion
                    );

                } else {

                    System.out.println("Línea incorrecta: " + linea);
                    continue;
                }

                biblioteca.añadirLibro(libro);
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar la biblioteca.");
            e.printStackTrace();
        }

        System.out.println("=== CARGAR ===");
        System.out.println(
                "Total libros cargados: "
                + biblioteca.getTotalLibros()
        );

        for (Libro libro : biblioteca.getLibros()) {
            System.out.println(
                    libro.getId()
                    + " - "
                    + libro.getTitulo()
            );
        }

        return biblioteca;
    }

    public void guardar(GestionBiblioteca biblioteca) {

        System.out.println("=== GUARDAR ===");
        System.out.println("Ruta: " + rutaArchivo.toAbsolutePath());
        System.out.println("Total libros: " + biblioteca.getTotalLibros());

        for (Libro libro : biblioteca.getLibros()) {
            System.out.println(libro.getId() + " - " + libro.getTitulo());
        }

        try (BufferedWriter escritor = Files.newBufferedWriter(rutaArchivo, StandardCharsets.UTF_8)) {

            for (Libro libro : biblioteca.getLibros()) {

                System.out.println("Escribiendo: " + libro.getTitulo());

                escritor.write(
                        libro.getId() + ";"
                        + libro.getTitulo() + ";"
                        + libro.getAutor() + ";"
                        + libro.getIsbn() + ";"
                        + libro.getAnioPublicacion() + ";"
                        + libro.getAnioEdicion() + ";"
                        + libro.isOriginal() + ";"
                        + libro.isFisico()
                );

                escritor.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
