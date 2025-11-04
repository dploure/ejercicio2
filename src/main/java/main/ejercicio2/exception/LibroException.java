package main.ejercicio2.exception;

public class LibroException extends RuntimeException{

    public LibroException(String mensajeError){
        super(mensajeError);
    }
}
