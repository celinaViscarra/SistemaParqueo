package com.grupo13.parqueo.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity
public class Usuario {
    @NonNull
    public String usuario;
    @NonNull
    public String contrasena;
    @NonNull
    public String nombre_usuario;

    public Usuario(){}

    public Usuario(String usuario, String contrasena, String nombreUsuario) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre_usuario = nombreUsuario;
    }
}
