package puk.modelo;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String isbn;
    private int anioPublicacion;
    private int anioEdicion;
    private String idioma;
    private boolean original;
    private boolean fisico;

    private static int contador = 1;

    public static void reiniciarContador() {
        contador = 1;
    }

    public Libro(
            String titulo,
            String autor,
            String isbn,
            int anioPublicacion,
            int anioEdicion,
            String idioma) {

        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn == null ? "" : isbn;
        this.anioPublicacion = anioPublicacion;
        this.anioEdicion = anioEdicion;
        this.idioma = idioma;
        this.id = contador;
        contador++;
        this.original = true;
        this.fisico = true;
    }

    public Libro(
            int id,
            String titulo,
            String autor,
            String isbn,
            int anioPublicacion,
            int anioEdicion,
            String idioma,
            boolean original,
            boolean fisico) {

        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn == null ? "" : isbn;
        this.anioPublicacion = anioPublicacion;
        this.anioEdicion = anioEdicion;
        this.idioma = idioma;
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

    public String getIdioma() {
        return idioma;
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

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {

        String fotocopiado
                = original ? "Original" : "Fotocopiado";

        String digital
                = fisico ? "Físico" : "Digital";

        String textoIsbn
                = isbn.isBlank() ? "Sin ISBN" : isbn;

        return "Libro: "
                + "\nNúmero de registro: " + id
                + "\nTítulo: " + titulo
                + "\nAutor: " + autor
                + "\nISBN: " + textoIsbn
                + "\nAño de publicación: " + anioPublicacion
                + "\nAño de la edición: " + anioEdicion
                + "\nIdioma: " + idioma
                + "\nFormato(original): " + fotocopiado
                + "\nFormato(soporte): " + digital;
    }
}