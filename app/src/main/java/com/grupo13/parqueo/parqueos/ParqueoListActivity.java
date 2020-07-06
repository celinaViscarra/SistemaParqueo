package com.grupo13.parqueo.parqueos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.grupo13.parqueo.ControlBD;
import com.grupo13.parqueo.R;
import com.grupo13.parqueo.modelo.Ubicacion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParqueoListActivity extends AppCompatActivity {

    @BindView(R.id.rvParqueos)
    RecyclerView rvParqueos;

    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueo_list);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        Ubicacion ubicacion = (Ubicacion) getIntent().getExtras().get("UBICACION");
        getSupportActionBar().setTitle("Parqueos de: "+ubicacion.nombre_ubicacion);
        ParqueoListAdapter parqueoListAdapter = new ParqueoListAdapter(helper.parqueoDao().obtenerParqueosPorUbicacion(ubicacion.id_ubicacion));
        rvParqueos.setAdapter(parqueoListAdapter);
        rvParqueos.setLayoutManager(new LinearLayoutManager(this));

    }
}