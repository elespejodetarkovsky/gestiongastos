package com.sxtsoft.gestiongastos.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sxtsoft.gestiongastos.Adapters.AdapterRvAlarmas;
import com.sxtsoft.gestiongastos.AltaAlarma;
import com.sxtsoft.gestiongastos.Interfaces.AlarmaServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.AlarmaServicesImpl;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.model.Alarma;

import java.util.List;

public class AlarmaFragment extends Fragment {

    private FloatingActionButton addAlarm;
    private RecyclerView rvAlarmas;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterRvAlarmas adapterRvAlarmas;
    private List<Alarma> alarmas;
    private AlarmaServices alarmaServicesImpl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarma, null);

        addAlarm = (FloatingActionButton) view.findViewById(R.id.btnAddAlarma);
        rvAlarmas = (RecyclerView) view.findViewById(R.id.rvAlarmas);

        alarmaServicesImpl = new AlarmaServicesImpl(getContext());

        alarmas = alarmaServicesImpl.getAll();

        rvAlarmas.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvAlarmas.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rvAlarmas.addItemDecoration(dividerItemDecoration);

        adapterRvAlarmas = new AdapterRvAlarmas(getContext(), alarmas);

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvAlarmas);
        adapterRvAlarmas.setOnAlarmaListener(new AdapterRvAlarmas.OnAlarmasListener() {

            @Override
            public void OnAlarmasClick(int position, long idAlarma) {
                Log.d("**","hice click en la lista");
            }
        });

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
    public void onResume() {
        super.onResume();
        Log.d("**","Estoy en onresume");

        adapterRvAlarmas.setAlarmas(alarmaServicesImpl.getAll());
        adapterRvAlarmas.notifyDataSetChanged();

    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            alarmas.remove(viewHolder.getAdapterPosition());
            adapterRvAlarmas.setAlarmas(alarmas);
            adapterRvAlarmas.notifyDataSetChanged();

        }
    };
}
