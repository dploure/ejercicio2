package model;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private boolean prestado;

    public Libro(int id, String titulo, String autor, boolean prestado) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.prestado = prestado;
    }

    @Override
    public String toString(){
        return "Título: "+titulo+", autor: "+autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }
    
}
