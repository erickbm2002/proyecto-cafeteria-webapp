package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index"; // Esto carga index.html
    }
    
    @GetMapping("/home")
    public String home() {
        return "index"; // Esto también carga index.html
    }
    
    @GetMapping("/menu")
    public String menu() {
        return "menu"; // Esto carga menu.html
    }
    
    // Puedes agregar más rutas para las otras páginas
    @GetMapping("/contacto")
    public String contacto() {
        return "contacto"; // Para futura página de contacto
    }
    
    @GetMapping("/ubicacion")
    public String ubicacion() {
        return "ubicacion"; // Para futura página de ubicación
    }
    
    @GetMapping("/galeria")
    public String galeria() {
        return "galeria"; // Para futura página de galería
    }
    
    @GetMapping("/presentacion")
    public String presentacion() {
        return "presentacion"; // Para futura página de presentación
    }
}