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
import android.widget.Toast;

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
import java.util.Map;

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
    private Map<String, Double> gastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_graph);

        entries = new ArrayList<>();

        gastoServicesImpl = new GastoServicesImpl(this);

        buildVistas();

        seteoInicialFechas();

        gastosBetweenFechas();

        dibujoGrafico();


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
                //gastos.clear();
                gastosBetweenFechas();
                dibujoGrafico();
            }
        });

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

    private void buildVistas() {

        startDate = (EditText) findViewById(R.id.txtStartDate);
        endDate = (EditText) findViewById(R.id.txtEndDate);
        testBusqueda = (Button) findViewById(R.id.btnTestFechas);
        barChart = (BarChart) findViewById(R.id.chartBar);

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

    private void seteoInicialFechas(){

        //Realizo el seteo inicial de busqueda con
        //los gastos del ultimo mes
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        String fecha = Utilidades.dateToString(calendar.getTime());
        fecha = fecha.substring(0,10);

        startDate.setText(fecha);

        String fecha2 = Utilidades.dateToString(new Date());
        fecha2 = fecha2.substring(0,10);

        endDate.setText(fecha2);

    }

    private void gastosBetweenFechas(){

        String strFecha1 = startDate.getText().toString() + " 00:00";
        String strFecha2 = endDate.getText().toString() + " 23:59";

        Date fecha1 = Utilidades.stringToDate(strFecha1);
        Date fecha2 = Utilidades.stringToDate(strFecha2);
        //Date fecha1 = Utilidades.stringToDate(strFecha1);
        //Date fecha2 = Utilidades.stringToDate(strFecha2);

        gastos = gastoServicesImpl.totalGastosBetweenDatesAndCategorias(fecha1, fecha2);

    }

    private BarEntry cargarDatosGrafico(Categoria categoria, float valorX, float valorY){

        LegendEntry entry = new LegendEntry();
        //entry.formColor = Color.RED;
        entry.label = categoria.toString();
        entries.add(entry);

        //float valorY = gastos.get(categoria.toString()).floatValue();

        return new BarEntry(valorX, valorY );

    }

    private void dibujoGrafico(){

        //CREAMOS LA LISTA CON LOS VALORES DE ENTRADA
        List<BarEntry> entradas = new ArrayList<>();

        //completo el eje x con las categorias
        int ejeX = 0;

        for (Categoria categoria: Categoria.values()){

            if (gastos.containsKey(categoria.toString())){

                entradas.add(cargarDatosGrafico(categoria,(float) ejeX, gastos.get(categoria.toString()).floatValue()));

            } else {
                /*
                en caso de que no haya la categoria en la
                respuesta de la consulta
                se pondrá un cero
                 */

                entradas.add(cargarDatosGrafico(categoria,(float) ejeX,0));
            }

            ejeX += 1;

        }

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

        //legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setFormSize(10f); // set the size of the legend forms/shapes
        legend.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors
        legend.setCustom(entries);

        //vaciaré las legendas
        //para que sean utilizadas nuevamente
        //entries clear

        entries.clear();

        //PONEMOS COLOR A CADA BARRA
        datos.setColors(ColorTemplate.COLORFUL_COLORS);

        //SEPARACION ENTRE BARRAS
        data.setBarWidth(0.9f);



        barChart.setData(data);
        barChart.getLegend().setWordWrapEnabled(true);

        //PONE LAS BARRAS CENTRADAS
        barChart.setFitBars(true);

        barChart.invalidate(); //hace refresh

    }

}
