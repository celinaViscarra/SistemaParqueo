package com.grupo13.parqueo.utilidades;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.grupo13.parqueo.modelo.Ubicacion;

import java.util.List;

public class ReconocimientoVoz {

    String[] comandos = {"parqueo", "encontrar parqueo", "parqueo mas cercano", "parqueo más cercano", "parking", "find parking"};

    String entradaUsuario;

    public ReconocimientoVoz(String entradaUsuario) {
        this.entradaUsuario = entradaUsuario;
    }

    public boolean esComandoValido(){
        boolean encontrado = false;
        for (String c: comandos){
            if (c.equals(entradaUsuario)) {
                encontrado = true;
                break;
            }
        }
        return  encontrado;
    }

    public Ubicacion getRespuesta(LatLng miUbicacion, List<Ubicacion> parqueos){
        float distanciaMasCercana = Float.MAX_VALUE;
        Ubicacion masCercana = null;

        for (Ubicacion parqueo: parqueos){

            float r1 = (float)Math.pow((parqueo.latitud - miUbicacion.latitude), 2);
            float r2 = (float)Math.pow((parqueo.longitud - miUbicacion.longitude), 2);
            float r3 = r1 + r2;
            float distancia = (float) Math.sqrt(r3);

            if(distancia < distanciaMasCercana){
                distanciaMasCercana = distancia;
                masCercana = parqueo;
            }
        }
        return  masCercana;
    }
}
