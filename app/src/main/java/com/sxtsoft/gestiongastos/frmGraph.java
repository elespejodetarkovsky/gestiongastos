package com.sxtsoft.gestiongastos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.GastoServicesImpl;
import com.sxtsoft.gestiongastos.database.DataBaseHelperGasto;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.ui.dialog.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class frmGraph extends AppCompatActivity {

    private static String TAG = "**MainActivity";

    private Description description;
    private BarChart barChart;
    private List<LegendEntry> entries;
    private List<Double> sumas;

    private LegendEntry[] legendEntries;

    private GastoServices gastoServicesImpl;
    private EditText startDate;
    private EditText endDate;
    private Button testBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_graph);

        entries = new ArrayList<>();
        sumas = new ArrayList<>();

        gastoServicesImpl = new GastoServicesImpl(this);

        startDate = (EditText) findViewById(R.id.txtStartDate);
        endDate = (EditText) findViewById(R.id.txtEndDate);
        testBusqueda = (Button) findViewById(R.id.btnTestFechas);


        //agrego el listener del datePicker (start y end)
        //********************************************************
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(0);
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(1);
            }
        });

        testBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("**","fecha1 " + startDate.getText().toString());
                Log.d("**","fecha2 " + endDate.getText().toString());

                String strFecha1 = startDate.getText().toString() + " 00:00";
                String strFecha2 = endDate.getText().toString() + " 23:59";

                Date fecha1 = Utilidades.stringToDate(strFecha1);
                Date fecha2 = Utilidades.stringToDate(strFecha2);

                gastoServicesImpl.totalGastosBetweenDatesAndCategorias(fecha1, fecha2);
            }
        });


        //CREAMOS LA LISTA CON LOS VALORES DE ENTRADA
        List<BarEntry> entradas = new ArrayList<>();

        //completo el eje x con las categorias
        int ejeX = 0;
        for (Categoria categoria: Categoria.values()){

            LegendEntry entry = new LegendEntry();
            //entry.formColor = Color.RED;
            entry.label = categoria.toString();
            entries.add(entry);

            //puedo incluir la suma aquí en una lista
            sumas.add(gastoServicesImpl.SumaGastosByCategoria(categoria));

            entradas.add(new BarEntry((float) ejeX, sumas.get(ejeX).floatValue()));

            ejeX =+ 1;

        }


        barChart = (BarChart) findViewById(R.id.chartBar);

//        description = new Description();
//
//        description.setText("Gastos por suministro");


        //MANDAMOS LOS DATOS PARA CREAR LA GRAFICA
        BarDataSet datos = new BarDataSet(entradas,"grafico barras");

        BarData data = new BarData(datos);

        //colocaré las etiquetas
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.isDrawInsideEnabled();

 //       legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        legend.setFormSize(10f); // set the size of the legend forms/shapes
//        legend.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
//        legend.setTextSize(12f);
//        legend.setTextColor(Color.BLACK);
//        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
//        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis


        // set custom labels and colors
        legend.setCustom(entries);

        //PONEMOS COLOR A CADA BARRA
        datos.setColors(ColorTemplate.COLORFUL_COLORS);

        //SEPARACION ENTRE BARRAS
        data.setBarWidth(0.9f);



        barChart.setData(data);
        barChart.getLegend().setWordWrapEnabled(true);

        //PONE LAS BARRAS CENTRADAS
        barChart.setFitBars(true);

        barChart.invalidate(); //hace refresh

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("**", e.toString());
                Log.d("**", h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void showDatePickerDialog(final int tag) {
        final DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero

                final String selectedDate = day + "-" + (month+1) + "-" + year;

                switch (tag){
                    case 0:
                        startDate.setText(selectedDate);
                        break;
                    case 1:
                        endDate.setText(selectedDate);
                        break;
                }
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

}
