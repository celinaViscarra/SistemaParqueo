package com.grupo13.parqueo.utilidades;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermisoService {
    public static void verificarPermiso(Context ctx){
        String[] permisos = retrievePermissions(ctx);
        //Inicializamos la variable con true
        boolean granted = true;
        for(String pivote:permisos){
            //Cambiamos el valor de granted, si llega a haber algun permiso que no haya sido concedido, granted pasa a ser false
            //de lo contrario, seguira siendo true
            granted = (ActivityCompat.checkSelfPermission((Activity) ctx,pivote) != PackageManager.PERMISSION_GRANTED) ? false: granted;
        }
        if (!granted) {
            // Si no tenemos permisos concedidos abrimos un dialogo para pedirlos al usuario
            ActivityCompat.requestPermissions(
                    (Activity) ctx,
                    permisos,
                    1
            );
        }
    }
    public static String[] retrievePermissions(Context context) {
        try {
            return context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS)
                    .requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("This should have never happened.", e);
        }
    }
}