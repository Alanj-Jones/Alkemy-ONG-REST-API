package com.alkemy.java.model;

public enum TypeRoles {

    ADMINISTRATOR("ADMINISTRATOR","El Administrador podra insertar y modificar datos"),
    USER("USER","El Usuario solo tendra acceso de lectura ");

    private String name;
    private String description;

    TypeRoles (String name,String description){
        this.name=name;
        this.description=description;
    }

    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setDescription(String description){
        this.description=description;
    }
}
