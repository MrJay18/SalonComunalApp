package com.grupo3.saloncomunal.models;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasenaEncriptada;

    public Usuario() {}

    public Usuario(int id, String nombre, String correo, String contrasenaEncriptada) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaEncriptada = contrasenaEncriptada;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasenaEncriptada() { return contrasenaEncriptada; }
    public void setContrasenaEncriptada(String contrasenaEncriptada) { this.contrasenaEncriptada = contrasenaEncriptada; }
}
