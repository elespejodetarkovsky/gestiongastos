package com.sxtsoft.gestiongastos.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.GastoServicesImpl;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.ui.dialog.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class GraficaFragment extends Fragment {
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
    private Map<String, String> coloresGastos;

    public GraficaFragment(){


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grafico, null);

        entries = new ArrayList<>();

        gastoServicesImpl = new GastoServicesImpl(view.getContext());

        buildVistas(view);

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



        return view;
    }

    private void buildVistas(View view) {

        startDate = (EditText) view.findViewById(R.id.txtStartDate);
        endDate = (EditText) view.findViewById(R.id.txtEndDate);
        testBusqueda = (Button) view.findViewById(R.id.btnTestFechas);
        barChart = (BarChart) view.findViewById(R.id.chartBar);

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

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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

        coloresGastos = colorCategoriaMap(gastos); //devuelve un map relacionando la categoría con un alpha de color según "peso"

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

        /*
        crearé un BarDataSet por cada categoría...para comparar en el tiempo
        luego me servirá
        ...será un grupo por cada categoría...
        más info en
        https://github.com/PhilJay/MPAndroidChart/wiki/Setting-Data
         */

//        for (BarEntry entrada: entradas){
//            //cargaré una entrada a cada bardataset
//            //para poder colorearlo por separado
//
//        }

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

        datos.setColors(new int[]{Color.BLUE, Color.RED, Color.GREEN});



        //SEPARACION ENTRE BARRAS
        data.setBarWidth(0.9f);



        barChart.setData(data);
        barChart.getLegend().setWordWrapEnabled(true);

        //PONE LAS BARRAS CENTRADAS
        barChart.setFitBars(true);

        barChart.invalidate(); //hace refresh

    }

    private BarEntry cargarDatosGrafico(Categoria categoria, float valorX, float valorY){

        //defino una barra con su leyenda en esta función

        LegendEntry entry = new LegendEntry();

        //String color = "#" + coloresGastos.get(categoria.toString());

        /*
        según el modelo actual contempla dibujar todas las categorías
        tengan datos o nop...por tanto
         */

        String color = (coloresGastos.get(categoria.toString()) == null)?"#FF000000":"#" + coloresGastos.get(categoria.toString());

        entry.formColor = Color.parseColor(color);

        entry.label = categoria.toString();
        entries.add(entry);

        //float valorY = gastos.get(categoria.toString()).floatValue();

        return new BarEntry(valorX, valorY );

    }


    private Map<String, String> colorCategoriaMap(Map<String, Double> valores){

        /*
        Esta funcion me devolverá un color relacionado con la
        categoria y según el peso (valor) un alfa
        100% para el valor mas alto
        10% el más bajo
         */


        Set<Map.Entry<String, Double>> set = valores.entrySet();
        //List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>((Collection<? extends Map.Entry<String, Double>>) set);
        List<Map.Entry<String, Double>> list = new ArrayList<>(set);

        Collections.sort( list, new Comparator<Map.Entry<String, Double>>()
        {

            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );


        int valorMaximo = list.get(0).getValue().intValue();
        //int valorMin = list.get((list.size()-1)).getValue().intValue();

        //obtengo el recurso de color en un string hexa
        int colorPrimario = R.color.colorPrimary;

        String colorPrimary = getResources().getString((int) colorPrimario);
        colorPrimary = colorPrimary.substring(3);

        //Inicio el set

        Map<String, String> mapColores = new HashMap<>();

        for(Map.Entry<String, Double> entry:list){

            //aquí debería cambiar el valor por el del color correspondiente a esa categoria
            //mayor ---> menor

            String colorKey = alphaPorPeso(valorMaximo, entry.getValue().intValue()) + //alpha Code
                            colorPrimary;

            mapColores.put(entry.getKey(), colorKey);

            Log.d("**", Integer.toHexString(colorPrimario));
            Log.d("**", "" + colorPrimario);
            Log.d("**",entry.getKey()+" ==== "+entry.getValue());
        }


        return mapColores;
    }

    private String alphaPorPeso(int valorMaximo, int valorEvaluar){
        /*
        esta función devolverá un string en hex
        para acoplarlo al string(hex) obtenido
        y corresponderá al alpha
         */


        int alphaMax = 255;
        int alphaMin = 25;
        int valorAlpha;

        try{

            valorAlpha = (valorEvaluar * alphaMax) / valorMaximo;

        }catch (Exception e){

            e.getMessage();
            valorAlpha = alphaMax;

        }

        if (valorAlpha < 25){
            valorAlpha = 25;
        }

        String valorHex = Integer.toHexString(valorAlpha);

        return valorHex;

    }

}
