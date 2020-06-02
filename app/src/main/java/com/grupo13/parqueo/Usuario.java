package com.grupo13.parqueo;

public class Usuario {
    String usuario, contrasena, nombreUsuario;

    public Usuario(String u, String c, String n){
        this.usuario=u;
        this.contrasena=c;
        this.nombreUsuario=n;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
