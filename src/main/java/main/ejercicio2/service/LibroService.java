package main.ejercicio2.service;

import java.util.ArrayList;
import model.Libro;
import java.util.List;
import java.util.stream.Collectors;
import main.ejercicio2.exception.LibroException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("memoria")
public class LibroService {
    private List<Libro> biblioteca = new ArrayList<>();
    private int nextId=1;

    public List<Libro> obtenerLibros(){
        return biblioteca;
    }

    // busca o título de un libro a partir de un ID, se usa nas mensaxes de confirmación da vista
    public String obtenerTituloPorID(int id){
        for (Libro l: biblioteca){
            if (l.getId()==id){
                return l.getTitulo();
            }
        }
        throw new LibroException("No existe un libro con ID "+id);
    }

    // nos devolve solo os libros que teñen o atributo prestado evaluado en false
    public List<Libro> obtenerDisponibles(){
        return biblioteca.stream().filter(l -> !l.isPrestado()).collect(Collectors.toList());
    }

    // nos devolve solo os libros que teñen o atributo prestado evaluado a true
    public List<Libro> obtenerPrestados(){
        return biblioteca.stream().filter(l -> l.isPrestado()).collect(Collectors.toList());
    }

    // Lle pasamos un filtro e nos devolve os libros nos cales o String filtro está contido dentro do atributo titulo, ignorando maiúsculas
    public List<Libro> obtenerFiltradosTitulo(String filtro){
        List<Libro> librosFiltrados= new ArrayList<>();
        for (Libro l: biblioteca){
            if (l.getTitulo().toLowerCase().indexOf(filtro.toLowerCase())>=0){
                librosFiltrados.add(l);
            }
        }
        return librosFiltrados;
    }

    // O mesmo que arriba pero comparando o filtro co autor en lugar do título
    public List<Libro> obtenerFiltradosAutor(String filtro){
        return biblioteca.stream().filter(l -> l.getAutor().toLowerCase().indexOf(filtro.toLowerCase())>=0).collect(Collectors.toList());
    }

    // añadimos un libro a partir de un título e de un autor, tira excepción se algun dos campos está vacío especificando cal deles
    public void agregarLibro(String titulo, String autor){
        if (titulo==null || titulo.trim().isEmpty()){
            throw new LibroException("El título no puede estar vacío");
        }
        if (autor==null || autor.trim().isEmpty()){
            throw new LibroException("El autor no puede estar vacío");
        }
        biblioteca.add(new Libro(nextId++, titulo, autor, false));
    }

    // eliminamos un libro a partir do ID, se non o atopa lanza unha excepción indicando que nono o atopa
    public void eliminarLibro(int id){
        boolean existe=biblioteca.removeIf(l -> l.getId()==id);
        if (!existe){
            throw new LibroException("No hay ningín Libro con ID "+id);
        }
    }

    //  cambiamos o estado de prestado a partir de un ID, lanza un mensaxe de error de noon atopar un libro con ese ID
    public void toggle(int id){
        boolean existe=false;
        for (Libro l : biblioteca){
            if (l.getId()==id){
                existe=true;
                l.setPrestado(!l.isPrestado());
            }
        }
        if (!existe){
            throw new LibroException("No hay un libro con ID "+id);
        }
    }
}
