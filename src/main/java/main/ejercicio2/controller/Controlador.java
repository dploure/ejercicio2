package main.ejercicio2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import model.Libro;
import java.util.ArrayList;


@Controller
public class Controlador {
    ArrayList<Libro> biblioteca = new ArrayList<>();
    private int indice=1;

    @GetMapping("/")
    public String paginaPrincipal(Model modelo){
        modelo.addAttribute("libros", biblioteca);
        return "index";
    }

    @GetMapping("/add")
    public String añadirLibro(@RequestParam String titulo, @RequestParam String autor, Model model){
        biblioteca.add(new Libro(indice++, titulo, autor, false));
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String borrarLibro(@PathVariable int id){
        biblioteca.removeIf(l -> l.getId()==id);
        return "redirect:/";
    }

}
