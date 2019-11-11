package com.sxtsoft.gestiongastos.fragments;

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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.sxtsoft.gestiongastos.Interfaces.GastoServices;
import com.sxtsoft.gestiongastos.Interfaces.impl.GastoServicesImpl;
import com.sxtsoft.gestiongastos.R;
import com.sxtsoft.gestiongastos.database.Utilidades;
import com.sxtsoft.gestiongastos.model.Categoria;
import com.sxtsoft.gestiongastos.ui.dialog.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraficaFragment extends Fragment {
    private static String TAG = "**MainActivity";

    private Description description;
    private BarChart barChart;
    //private List<LegendEntry> entries;
    private List<Double> sumas;

    private GastoServices gastoServicesImpl;
    private TextView startDate;
    private TextView endDate;
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

                Categoria[] categorias = Categoria.values();

                String index = e.toString();
                index = index.substring(10,11);

                Categoria categoria = categorias[Integer.parseInt(index)];

                Log.d("**", "La categoria seleccionada es: " + categoria.toString());

                //llamo a la funcion para construir el siguiente graph

                Date fechaInicial = Utilidades.stringToDate(startDate.getText().toString() + " 00:00");
                Date fechaFin = Utilidades.stringToDate(endDate.getText().toString() + " 23:59");

                gastoServicesImpl.totalGastosBetweenDatesAndTiposGastos(fechaInicial,
                        fechaFin, categoria);

            }

            @Override
            public void onNothingSelected() {

            }
        });



        return view;
    }

    private void buildVistas(View view) {

        startDate = (TextView) view.findViewById(R.id.txtStartDate);
        endDate = (TextView) view.findViewById(R.id.txtEndDate);
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

        coloresGastos = colorMap(gastos); //devuelve un map relacionando la categoría con un alpha de color según "peso"

    }

    private void dibujarGrafico(HashMap<String, Double> gastos ) { //}, String[] tiposGastos){ veré que tal se supone que en el hash está all

        /*
        Esta función será la encargada
        de comenzar el dibujo del grafico
        ya sea para categorias como para
        tipos de gastos (subcategoria)
         */

        //inicia las leyendas que irán en el gráfico
        //contendrá el color y el label
        List<LegendEntry> entries = new ArrayList<>();

        //completo el eje x con las categorias
        int ejeX = 0;

        List<IBarDataSet> bars = new ArrayList<>();

        for (Map.Entry<String, Double> gasto: gastos.entrySet()){

            //CREAMOS LA LISTA CON LOS VALORES DE ENTRADA
            List<BarEntry> entradas = new ArrayList<>();


                entradas.add(new BarEntry((float) ejeX, gasto.getValue().floatValue()));
            //entradas.add(cargarDatosGrafico(categoria,(float) ejeX, gastos.get(categoria.toString()).floatValue()));




            //creo un BarDataSet y coloco cada entrada
            //que corresponde a una barra
            //el dataset puede ser bueno para comparar períodos
            //un dataset es una estructura de datos (algo así como un rango)
            
            BarDataSet dataSet = new BarDataSet(entradas, "");

            //pongo aquí el color en el dataset
            String color = (coloresGastos.get(gasto.getKey()) == null)?"#FF000000":"#" + coloresGastos.get(gasto.getKey());

            dataSet.setColor(Color.parseColor(color)); //color de la barra en función de su "peso"


            bars.add(dataSet);

            ejeX += 1;

        }

        BarData data = new BarData(bars);


        //colocaré las etiquetas
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.isDrawInsideEnabled();


        legend.setFormSize(9f); // set the size of the legend forms/shapes
        legend.setForm(Legend.LegendForm.LINE); // set what type of form/shape should be used
        legend.setTextSize(8f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors
        legend.setCustom(entries);

        //vaciaré las legendas
        //para que sean utilizadas nuevamente
        //entries clear

        entries.clear();



        //SEPARACION ENTRE BARRAS
        data.setBarWidth(0.9f);



        barChart.setData(data);
        barChart.getLegend().setWordWrapEnabled(true);

        //PONE LAS BARRAS CENTRADAS
        barChart.setFitBars(true);

        barChart.invalidate(); //hace refresh

    }

    private void dibujoGrafico(){


        //completo el eje x con las categorias
        int ejeX = 0;

        List<IBarDataSet> bars = new ArrayList<>();

        for (Categoria categoria: Categoria.values()){

            //CREAMOS LA LISTA CON LOS VALORES DE ENTRADA
            List<BarEntry> entradas = new ArrayList<>();


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


            //creo un BarDataSet y coloco cada entrada
            //que corresponde a una barra
            BarDataSet dataSet = new BarDataSet(entradas, "");

            //pongo aquí el color en el dataset
            String color = (coloresGastos.get(categoria.toString()) == null)?"#FF000000":"#" + coloresGastos.get(categoria.toString());

            dataSet.setColor(Color.parseColor(color)); //color de la barra en función de su "peso"


            bars.add(dataSet);

            ejeX += 1;

        }

        BarData data = new BarData(bars);


        //colocaré las etiquetas
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.isDrawInsideEnabled();


        legend.setFormSize(9f); // set the size of the legend forms/shapes
        legend.setForm(Legend.LegendForm.LINE); // set what type of form/shape should be used
        legend.setTextSize(8f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors
        legend.setCustom(entries);

        //vaciaré las legendas
        //para que sean utilizadas nuevamente
        //entries clear

        entries.clear();



        //SEPARACION ENTRE BARRAS
        data.setBarWidth(0.9f);



        barChart.setData(data);
        barChart.getLegend().setWordWrapEnabled(true);

        //PONE LAS BARRAS CENTRADAS
        barChart.setFitBars(true);

        barChart.invalidate(); //hace refresh

    }

    private LegendEntry listaLeyendas(Map<String, String> colores, String datoX){
        /*
        Esta función devolverá la lista de leyendas
        que contendrán los labels y los colores
         */

        //instancio una leyenda
        LegendEntry entry = new LegendEntry();

        /*
        según el modelo actual contempla dibujar todas las categorías
        tengan datos o nop...por tanto
        Aunque en el caso de recibir subcategorias por espacio
        no debería recibir valores vacios (cero)
        no se mostrarán en la gráfica
         */

        String color = (colores.get(datoX) == null)?"#FF000000":"#" + colores.get(datoX);



    }
    private BarEntry cargarDatosGrafico(Categoria categoria, float valorX, float valorY){

        /*
        defino una barra con su leyenda en esta función
        Devuelvo un bar
         */

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



    private Map<String, String> buildColorMap(Map<String, Double> valores){

        /*
        Esta función deberá relacionar el String (sea lo que sea)
        con el valor y darle un "peso"
        100% para el valor mas alto
        10% el más bajo
         */



        Set<Map.Entry<String, Double>> set = valores.entrySet();
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
