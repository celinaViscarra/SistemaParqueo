package com.grupo13.parqueo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.grupo13.parqueo.modelo.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class UbicacionListaFragment extends Fragment {
    ListView lvLista;
    private static final String ARG_PARAM1 = "paramLista";
    private static final String ARG_MAIN = "main";

    private ArrayList<Ubicacion> paramLista;
    private MainActivity main;

    public UbicacionListaFragment() {
        // Required empty public constructor
    }

    public static UbicacionListaFragment newInstance(ArrayList<Ubicacion> paramLista, MainActivity main) {
        UbicacionListaFragment fragment = new UbicacionListaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, paramLista);
        args.putSerializable(ARG_MAIN, main);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramLista = (ArrayList<Ubicacion>) getArguments().getSerializable(ARG_PARAM1);
            main = (MainActivity) getArguments().getSerializable(ARG_MAIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ubicacion_lista, container, false);
        if(paramLista != null){
            lvLista = (ListView) view.findViewById(R.id.lvLista);
            ArrayList<String> nombres = new ArrayList<>();
            for(Ubicacion pivote: paramLista)
                nombres.add(pivote.nombre_ubicacion);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nombres);
            lvLista.setAdapter(adapter);
            lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    main.seleccionarUbi(paramLista.get(position));
                }
            });
        }
        return view;
    }
}