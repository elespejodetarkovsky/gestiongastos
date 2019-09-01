package com.sxtsoft.gestiongastos;

import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.List;

public class frmGraph extends AppCompatActivity {

    private static String TAG = "**MainActivity";

    private float[] yData = {25,3f, 10,6f, 44,31f, 53,4f};
    private String[] xData = {"SUMINISTROS","SUMINISTROS2","SUMINISTROS3","TRANSPORTES"};
    private Description description;
    private BarChart barChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_graph);

        //pieChart = (PieChart) findViewById(R.id.chartPie);
        barChart = (BarChart) findViewById(R.id.chartBar);

        description = new Description();

        description.setText("Gastos por suministro");

        //CREAMOS LA LISTA CON LOS VALORES DE ENTRADA
        List<BarEntry> entradas = new ArrayList<>();

        entradas.add(new BarEntry(0f,2));
        entradas.add(new BarEntry(1f,4));
        entradas.add(new BarEntry(2f, 6));
        entradas.add(new BarEntry(3f, 8));
        entradas.add(new BarEntry(4f,3));
        entradas.add(new BarEntry(5f, 1));


        //MANDAMOS LOS DATOS PARA CREAR LA GRAFICA
        BarDataSet datos = new BarDataSet(entradas,"grafico barras");

        BarData data = new BarData(datos);

        //List<LegendEntry> legendEntries = new ArrayList<LegendEntry>();

        //LegendEntry legendEntry = new LegendEntry()

        //legendEntries.add(0,)
        //Legend legend = new Legend();
        //legend.setEntries();

        //PONEMOS COLOR A CADA BARRA
        datos.setColors(ColorTemplate.COLORFUL_COLORS);

        //SEPARACION ENTRE BARRAS
        data.setBarWidth(0.9f);



        barChart.setData(data);

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



//        addDataSet();
//
//        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                Log.d(TAG, e.toString());
//                Log.d(TAG, h.toString());
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });

    }

    private void addDataSet(){

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for(int i = 0; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //creo el data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Gastos euros");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //agregar colores
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

//        pieDataSet.setColors(colors);
//
//        //agregar etiquetas al grafico
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.CIRCLE);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
//
//        //creo pie data object
//        PieData pieData = new PieData(pieDataSet);
//        pieChart.setData(pieData);
//        pieChart.invalidate();


    }
}
