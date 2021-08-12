package com.alkemy.java.exception.news;

public class NewsExistsException extends Exception{
    public NewsExistsException(){super("Ya existe una Novedad con ese nombre");}

}
