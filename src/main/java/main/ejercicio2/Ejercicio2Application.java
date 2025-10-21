package main.ejercicio2;
/**
 * 2. Mini biblioteca de libros
Crear una aplicación para gestionar libros. Cada libro tiene un título, un autor y un estado prestado (booleano). La aplicación debe permitir:
1.​ Mostrar lista de libros (inicialmente estática).
2.​ Añadir libros predefinidos con enlaces /add?titulo=...&autor=....
3.​ Eliminar libros por ID con /delete/{id}.
4.​ Cambiar estado prestado/no prestado mediante /toggle/{id}.
5.​ Mostrar visualmente los libros prestados con CSS.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ejercicio2Application {

	public static void main(String[] args) {
		SpringApplication.run(Ejercicio2Application.class, args);
	}

}
