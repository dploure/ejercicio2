package main.ejercicio2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import main.ejercicio2.exception.LibroException;
import main.ejercicio2.service.LibroService;
import org.springframework.ui.Model;


@Controller
public class LibroController {
    @Autowired
    @Qualifier("memoria")
    private LibroService libroService;

    @GetMapping("/")
    public String paginaPrincipal(
        Model modelo,
        @RequestParam(required=false) String mensaje,
        @RequestParam(required=false) Boolean disponibles, 
        @RequestParam(required=false) String filtroTitulo,
        @RequestParam(required=false) String filtroAutor
        ){
        if (mensaje!=null){
            modelo.addAttribute("mensaje", mensaje);
        }
        modelo.addAttribute("libros", libroService.obtenerLibros());
        if (filtroTitulo!=null){
            modelo.addAttribute("libros", libroService.obtenerFiltradosTitulo(filtroTitulo));
        }
        if (filtroAutor!=null){
            modelo.addAttribute("libros", libroService.obtenerFiltradosAutor(filtroAutor));
        }

        if (disponibles!=null){

            if (disponibles){
                modelo.addAttribute("libros", libroService.obtenerDisponibles());
            }
            else{
                modelo.addAttribute("libros", libroService.obtenerPrestados());
            }
        }
        return "vistaLibros";
    }

    @GetMapping("/add")
    public String añadirLibro(@RequestParam String titulo, @RequestParam String autor, RedirectAttributes redirectAttributes){
        try{
            libroService.agregarLibro(titulo, autor);
            redirectAttributes.addAttribute("mensaje", "Libro añadido correctamente");
            return "redirect:/";
        } catch (LibroException e){
            redirectAttributes.addAttribute("mensaje",e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/delete/{id}")
    public String borrarLibro(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            String libro=libroService.obtenerTituloPorID(id);
            libroService.eliminarLibro(id);
            redirectAttributes.addAttribute("mensaje","\""+libro+"\" eliminado correcramente");
            return "redirect:/";
        } catch (LibroException e){
            redirectAttributes.addAttribute("mensaje",e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/toggle/{id}")
    public String cambiarEstado(@PathVariable int id, RedirectAttributes redirectAttributes){
        try{
            libroService.toggle(id);
            String libro=libroService.obtenerTituloPorID(id);
            redirectAttributes.addAttribute("mensaje","Estado de \""+libro+"\" modificado correctamente");
            return "redirect:/";
        } catch (LibroException e){
            redirectAttributes.addAttribute("mensaje",e.getMessage());
            return "redirect:/";
        }
    }
    
}
