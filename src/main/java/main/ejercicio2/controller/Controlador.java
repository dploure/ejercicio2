package main.ejercicio2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import model.Libro;
import java.util.Arrays;
import java.util.List;

@Controller
public class Controlador {

    @GetMapping("/")
    public String paginaPrincipal(Model modelo){
        List<Libro> biblioteca= Arrays.asList(
            new Libro(1, "Los Miserables", "Víctor Hugo", false),
            new Libro(2, "Ensayo sobre la ceguera", "José Saramago", false),
            new Libro(3, "1984", "George Orwell", false),
            new Libro(4, "Cementerio de animales", "Stephen King", false)
        );
        modelo.addAttribute("libros", biblioteca);
        return "index";
    }

}
