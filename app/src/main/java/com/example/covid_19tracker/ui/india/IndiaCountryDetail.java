package com.example.covid_19tracker.ui.india;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.anychart.AnyChart;
//import com.anychart.AnyChartView;
//import com.anychart.chart.common.dataentry.DataEntry;
//import com.anychart.chart.common.dataentry.ValueDataEntry;
//import com.anychart.charts.Pie;
//import com.anychart.enums.Align;
//import com.anychart.enums.LegendLayout;
import com.example.covid_19tracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class IndiaCountryDetail extends AppCompatActivity {

//    AnyChartView anyChartView;

    TextView tvDetailState, tvDetailConfirmed, tvDetailDeaths, tvDetailRecovered, tvDetailActive;

//    String[] name = new String[3];
//    int[] val = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_country_detail);

        // call view
        tvDetailState = findViewById(R.id.tvDetailState);
        tvDetailRecovered = findViewById(R.id.tvDetailRecovered);
        tvDetailDeaths = findViewById(R.id.tvDetailDeaths);
        tvDetailActive = findViewById(R.id.tvDetailActive);
        tvDetailConfirmed = findViewById(R.id.tvDetailConfirmed);

        // call Covid Country
        IndiaCountry indiaCountry = getIntent().getParcelableExtra("EXTRA_COVID_INDIA");

        // set text view
        tvDetailState.setText(indiaCountry.getmState());
        tvDetailRecovered.setText(indiaCountry.getmRecovered());
        tvDetailDeaths.setText(indiaCountry.getmDeaths());
        tvDetailActive.setText(indiaCountry.getmActive());
        tvDetailConfirmed.setText(indiaCountry.getmConfirmed());


        PieChart pieChart = findViewById(R.id.pie_chart_state);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(Integer.parseInt(indiaCountry.getmDeaths()), "Total Deaths"));
        entries.add(new PieEntry(Integer.parseInt(indiaCountry.getmRecovered()), "Total Recovered"));
        entries.add(new PieEntry(Integer.parseInt(indiaCountry.getmActive()), "Total Active"));

        PieDataSet set = new PieDataSet(entries, "Statistcs");
        PieData data = new PieData(set);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.animateXY(1000, 1000);
        pieChart.getLegend().setEnabled(false);
        pieChart.setHoleRadius(30);
        pieChart.setUsePercentValues(true);
        pieChart.setTransparentCircleRadius(20);
        pieChart.setHoleColor(Color.parseColor("#101010"));
        pieChart.setEntryLabelTextSize(10);
        pieChart.invalidate();


    }

}