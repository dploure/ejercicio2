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

    public String obtenerTituloPorID(int id){
        for (Libro l: biblioteca){
            if (l.getId()==id){
                return l.getTitulo();
            }
        }
        throw new LibroException("No existe un libro con ID "+id);
    }

    public List<Libro> obtenerDisponibles(){
        return biblioteca.stream().filter(l -> !l.isPrestado()).collect(Collectors.toList());
    }

    public List<Libro> obtenerPrestados(){
        return biblioteca.stream().filter(l -> l.isPrestado()).collect(Collectors.toList());
    }

    public List<Libro> obtenerFiltradosTitulo(String filtro){
        List<Libro> librosFiltrados= new ArrayList<>();
        for (Libro l: biblioteca){
            if (l.getTitulo().toLowerCase().indexOf(filtro.toLowerCase())>=0){
                librosFiltrados.add(l);
            }
        }
        return librosFiltrados;
    }

    public List<Libro> obtenerFiltradosAutor(String filtro){
        return biblioteca.stream().filter(l -> l.getAutor().toLowerCase().indexOf(filtro.toLowerCase())>=0).collect(Collectors.toList());
    }

    public void agregarLibro(String titulo, String autor){
        if (titulo==null || titulo.trim().isEmpty()){
            throw new LibroException("El título no puede estar vacío");
        }
        if (autor==null || autor.trim().isEmpty()){
            throw new LibroException("El autor no puede estar vacío");
        }
        biblioteca.add(new Libro(nextId++, titulo, autor, false));
    }

    public void eliminarLibro(int id){
        boolean existe=biblioteca.removeIf(l -> l.getId()==id);
        if (!existe){
            throw new LibroException("No hay ningín Libro con ID "+id);
        }
    }

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
