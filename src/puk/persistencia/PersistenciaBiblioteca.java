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
        boolean hayLibrosFormatoAntiguo = false;

        if (!Files.exists(rutaArchivo)) {
            try {
                Files.createFile(rutaArchivo);

                System.out.println(
                        "Archivo creado: "
                        + rutaArchivo.toAbsolutePath()
                );

            } catch (IOException e) {
                System.out.println("No se pudo crear biblioteca.txt");
                e.printStackTrace();
            }

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

                String[] datos = linea.split(";", -1);
                Libro libro;

                // Formato nuevo 2.0 con ID:
                // id;titulo;autor;isbn;anioPublicacion;anioEdicion;idioma;original;fisico
                if (datos.length == 9) {

                    int id = Integer.parseInt(datos[0]);
                    String titulo = datos[1];
                    String autor = datos[2];
                    String isbn = datos[3];
                    int anioPublicacion = Integer.parseInt(datos[4]);
                    int anioEdicion = Integer.parseInt(datos[5]);
                    String idioma = datos[6];
                    boolean original = Boolean.parseBoolean(datos[7]);
                    boolean fisico = Boolean.parseBoolean(datos[8]);

                    libro = new Libro(
                            id,
                            titulo,
                            autor,
                            isbn,
                            anioPublicacion,
                            anioEdicion,
                            idioma,
                            original,
                            fisico
                    );

                    // Formato antiguo con ID:
                    // id;titulo;autor;isbn;anioPublicacion;anioEdicion;original;fisico
                } else if (datos.length == 8) {

                    int id = Integer.parseInt(datos[0]);
                    String titulo = datos[1];
                    String autor = datos[2];
                    String isbn = datos[3];
                    int anioPublicacion = Integer.parseInt(datos[4]);
                    int anioEdicion = Integer.parseInt(datos[5]);
                    String idioma = "Sin especificar";
                    boolean original = Boolean.parseBoolean(datos[6]);
                    boolean fisico = Boolean.parseBoolean(datos[7]);

                    libro = new Libro(
                            id,
                            titulo,
                            autor,
                            isbn,
                            anioPublicacion,
                            anioEdicion,
                            idioma,
                            original,
                            fisico
                    );

                    hayLibrosFormatoAntiguo = true;

                    // Formato antiguo sin ID:
                    // titulo;autor;isbn;anioPublicacion;anioEdicion;original;fisico
                } else if (datos.length == 7) {

                    String titulo = datos[0];
                    String autor = datos[1];
                    String isbn = datos[2];
                    int anioPublicacion = Integer.parseInt(datos[3]);
                    int anioEdicion = Integer.parseInt(datos[4]);
                    String idioma = "Sin especificar";
                    boolean original = Boolean.parseBoolean(datos[5]);
                    boolean fisico = Boolean.parseBoolean(datos[6]);

                    libro = new Libro(
                            titulo,
                            autor,
                            isbn,
                            anioPublicacion,
                            anioEdicion,
                            idioma
                    );

                    libro.setOriginal(original);
                    libro.setFisico(fisico);

                    hayLibrosFormatoAntiguo = true;

                    // Formato antiguo simplificado:
                    // titulo;autor;isbn;anioPublicacion;anioEdicion
                } else if (datos.length == 5) {

                    String titulo = datos[0];
                    String autor = datos[1];
                    String isbn = datos[2];
                    int anioPublicacion = Integer.parseInt(datos[3]);
                    int anioEdicion = Integer.parseInt(datos[4]);
                    String idioma = "Sin especificar";

                    libro = new Libro(
                            titulo,
                            autor,
                            isbn,
                            anioPublicacion,
                            anioEdicion,
                            idioma
                    );

                    hayLibrosFormatoAntiguo = true;

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
        System.out.println(
                "Total libros: "
                + biblioteca.getTotalLibros()
        );

        for (Libro libro : biblioteca.getLibros()) {
            System.out.println(
                    libro.getId()
                    + " - "
                    + libro.getTitulo()
            );
        }

        try (BufferedWriter escritor = Files.newBufferedWriter(
                rutaArchivo,
                StandardCharsets.UTF_8)) {

            for (Libro libro : biblioteca.getLibros()) {

                System.out.println(
                        "Escribiendo: "
                        + libro.getTitulo()
                );

                escritor.write(
                        libro.getId() + ";"
                        + libro.getTitulo() + ";"
                        + libro.getAutor() + ";"
                        + libro.getIsbn() + ";"
                        + libro.getAnioPublicacion() + ";"
                        + libro.getAnioEdicion() + ";"
                        + libro.getIdioma() + ";"
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
