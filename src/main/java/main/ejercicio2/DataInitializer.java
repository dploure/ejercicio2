package main.ejercicio2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import main.ejercicio2.service.LibroService;

// Inizializa os datos 
@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    LibroService libroService;

    public void run(String... args){
        libroService.agregarLibro("1984", "George Orwell");
        libroService.agregarLibro("La llamada de los salvaje", "Jack London");
        libroService.agregarLibro("Los miserables", "Víctor Hugo");
        libroService.agregarLibro("100 años de soledad", "Gabriel García Márquez");
        libroService.agregarLibro("Ensayo sobre la ceguera", "José Saramago");
    }
}
