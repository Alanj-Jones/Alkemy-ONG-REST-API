package com.alkemy.java.exception.slide;

public class NotDeletedSlideException extends Exception{
    public NotDeletedSlideException(){super("El Slide no ha podido ser eliminado de la BD");}
}
