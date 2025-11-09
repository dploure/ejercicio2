package main.ejercicio2.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import main.ejercicio2.exception.LibroException;
import main.ejercicio2.service.LibroService;
import model.Libro;

import org.springframework.ui.Model;


@Controller
public class LibroController {
    @Autowired
    @Qualifier("memoria")
    private LibroService libroService;

    // E a raíz a que pode recibir opcionalmente un filtro de autor, un filtro de título, ou un boolean sobre se quero que mostre os dispoñibles 
    // ou os prestados, e acorde á existencia e valores de estos parámetros de url, lle pide ao servicio que lle devolva a lista correspondente
    @GetMapping("/")
    public String paginaPrincipal(
        Model modelo,
        @RequestParam(required=false) String mensaje,
        @RequestParam(required=false) Boolean disponibles, 
        @RequestParam(required=false) String filtroTitulo,
        @RequestParam(required=false) String filtroAutor
        ){

        // Se se lle pasa mensaxe llo pasa á vista
        if (mensaje!=null){
            modelo.addAttribute("mensaje", mensaje);
        }

        // Por defecto a lista de libros é a lista completa
        List<Libro> libros=libroService.obtenerLibros();
        // se temos filtro de título pedimoslle o listado filtrado ao servicio
        if (filtroTitulo!=null){
            libros=libroService.obtenerFiltradosTitulo(filtroTitulo);
        }
        // o mesmo se temos filtro de autor
        if (filtroAutor!=null){
            libros=libroService.obtenerFiltradosAutor(filtroAutor);
        }
        
        // se nos pasan parámetro de disponibles miramos que valor toma e pedimos ao servicio o listaod correspondente
        if (disponibles!=null){
            
            if (disponibles){
                libros=libroService.obtenerDisponibles();
            }
            else{
                libros=libroService.obtenerPrestados();
            }
        }

        // engadimos o listado de libros ao modelo
        modelo.addAttribute("libros", libros);
        return "vistaLibros";
    }

    // Tratamos de engadir un libro, e usamos redirectAttributes para engadir un mensaxe de confirmación codificado correctamente, se o servicio
    // tira excepción, engadimos a mensaxe de error na vista e en ambos casos rediriximos á raíz
    @GetMapping("/add")
    public String añadirLibro(@RequestParam String titulo, @RequestParam String autor, RedirectAttributes redirectAttributes){
        try{
            libroService.agregarLibro(titulo, autor);
        //    redirectAttributes.addAttribute("mensaje", "Libro añadido correctamente");
        //    return "redirect:/";
            return "redirect:/?mensaje=Libro añadido correctamente";
    
        } catch (LibroException e){
            redirectAttributes.addAttribute("mensaje",e.getMessage());
            return "redirect:/";
        }
    }

    // Facemos o mesmo que en /add pero a maiores usamos o título do libro para que a mensaxe sexa mais clara
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

    // Mesma lóxica que no anterior endpoint
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
