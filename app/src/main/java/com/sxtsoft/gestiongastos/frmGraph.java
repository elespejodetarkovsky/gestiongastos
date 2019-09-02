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

        //colocaré las etiquetas
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setFormSize(10f); // set the size of the legend forms/shapes
        legend.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        //incorporo el LegendEntry
        LegendEntry[] legendEntries = new LegendEntry[]{new LegendEntry("Bank", Legend.LegendForm.DEFAULT, 10f, 2f, null, Color.RED),
                new LegendEntry("Bank", Legend.LegendForm.DEFAULT, 10f, 2f, null, Color.RED),
                new LegendEntry("Bank", Legend.LegendForm.DEFAULT, 10f, 2f, null, Color.RED),
                new LegendEntry("Bank", Legend.LegendForm.DEFAULT, 10f, 2f, null, Color.RED),
                new LegendEntry("Bank", Legend.LegendForm.DEFAULT, 10f, 2f, null, Color.RED),
                new LegendEntry("Bank", Legend.LegendForm.DEFAULT, 10f, 2f, null, Color.RED)};


        // set custom labels and colors
        legend.setCustom(legendEntries);

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

    }
}
