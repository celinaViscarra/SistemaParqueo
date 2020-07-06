package com.grupo13.parqueo.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey
    @NonNull
    public String usuario;
    @NonNull
    public String contrasena;
    @NonNull
    public String nombre_usuario;
    public String url_imagen;

    public Usuario(){}

    public Usuario(String usuario, String contrasena, String nombreUsuario) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre_usuario = nombreUsuario;
    }
}
