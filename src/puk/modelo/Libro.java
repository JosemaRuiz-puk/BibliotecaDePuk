/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puk.modelo;

/**
 *
 * @author utpuk
 */
public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private int anioPublicacion;
    private int anioEdicion;
    private boolean original;
    private boolean fisico;
    private static int contador = 1;
    
    public static void reiniciarContador() {
    contador = 1;
}

    public Libro(String titulo, String autor, String isbn, int anioPublicacion, int anioEdicion) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.anioEdicion = anioEdicion;
        this.id = contador;
        contador++;
        this.original = true;
        this.fisico = true;
    }

    public Libro(int id, String titulo, String autor, String isbn,
            int anioPublicacion, int anioEdicion, boolean original, boolean fisico) {

        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.anioEdicion = anioEdicion;
        this.original = original;
        this.fisico = fisico;

        if (id >= contador) {
            contador = id + 1;
        }
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public int getAnioEdicion() {
        return anioEdicion;
    }

    public boolean isOriginal() {
        return original;
    }

    public boolean isFisico() {
        return fisico;
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public void setFisico(boolean fisico) {
        this.fisico = fisico;
    }

    @Override
    public String toString() {
        String fotocopiado = original ? "Original" : "Fotocopiado";
        String digital = fisico ? "Físico" : "Digital";
        return "Libro: "
                + "\nNúmero de registro: " + id
                + "\nTítulo: " + titulo
                + "\nAutor: " + autor
                + "\nISBN: " + isbn
                + "\nAño de publicación: " + anioPublicacion
                + "\nAño de la edición: " + anioEdicion
                + "\nFormato(original): " + fotocopiado
                + "\nFormato(soporte): " + digital;
    }

}
