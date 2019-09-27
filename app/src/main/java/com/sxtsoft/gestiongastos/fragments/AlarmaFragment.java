package com.sxtsoft.gestiongastos.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxtsoft.gestiongastos.Adapters.AdapterRvAlarmas;
import com.sxtsoft.gestiongastos.AltaAlarma;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.Alarma;

import java.util.List;

public class AlarmaFragment extends Fragment implements AdapterRvAlarmas.OnAlarmasListener {

    private FloatingActionButton addAlarm;
    private RecyclerView rvAlarmas;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterRvAlarmas adapterRvAlarmas;
    private List<Alarma> alarmas;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarma, null);

        addAlarm = (FloatingActionButton) view.findViewById(R.id.btnAddAlarma);
        rvAlarmas = (RecyclerView) view.findViewById(R.id.rvAlarmas);

        rvAlarmas.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvAlarmas.setLayoutManager(layoutManager);

        adapterRvAlarmas = new AdapterRvAlarmas(getContext(), alarmas, this);
        rvAlarmas.setAdapter(adapterRvAlarmas);

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AltaAlarma.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void OnAlarmasClick(int position, long idAlarma) {

    }
}
