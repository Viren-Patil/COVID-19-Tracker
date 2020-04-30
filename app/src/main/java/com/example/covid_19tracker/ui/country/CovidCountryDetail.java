package com.example.covid_19tracker.ui.country;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.covid_19tracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class CovidCountryDetail extends AppCompatActivity {

    TextView tvDetailCountryName, tvDetailTotalCases, tvDetailTodayCases, tvDetailTotalDeaths,
            tvDetailTodayDeaths, tvDetailTotalRecovered, tvDetailTotalActive, tvDetailTotalCritical;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_detail);

        // call view
        tvDetailCountryName = findViewById(R.id.tvDetailCountryName);
        tvDetailTotalCases = findViewById(R.id.tvDetailTotalCases);
        tvDetailTodayCases = findViewById(R.id.tvDetailTodayCases);
        tvDetailTotalDeaths = findViewById(R.id.tvDetailTotalDeaths);
        tvDetailTodayDeaths = findViewById(R.id.tvDetailTodayDeaths);
        tvDetailTotalRecovered = findViewById(R.id.tvDetailTotalRecovered);
        tvDetailTotalActive = findViewById(R.id.tvDetailTotalActive);
        tvDetailTotalCritical = findViewById(R.id.tvDetailTotalCritical);


        // call Covid Country
        CovidCountry covidCountry = getIntent().getParcelableExtra("EXTRA_COVID");

        // set text view
        tvDetailCountryName.setText(covidCountry.getmCovidCountry());
        tvDetailTotalCases.setText(Integer.toString(covidCountry.getmCases()));
        tvDetailTodayCases.setText(covidCountry.getmTodayCases());
        tvDetailTotalDeaths.setText(Integer.toString(covidCountry.getmDeaths()));
        tvDetailTodayDeaths.setText(covidCountry.getmTodayDeaths());
        tvDetailTotalRecovered.setText(Integer.toString(covidCountry.getmRecovered()));
        tvDetailTotalActive.setText(Integer.toString(covidCountry.getmActive()));
        tvDetailTotalCritical.setText(covidCountry.getmCritical());

        PieChart pieChart = findViewById(R.id.pie_chart);

        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(covidCountry.getmDeaths(), "Total Deaths"));
        entries.add(new PieEntry(covidCountry.getmRecovered(), "Total Recovered"));
        entries.add(new PieEntry(covidCountry.getmActive(), "Total Active"));

        PieDataSet set = new PieDataSet(entries, "Statistics");
        PieData data = new PieData(set);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.animateXY(1000, 1000);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setHoleRadius(30);
        pieChart.setUsePercentValues(true);
        pieChart.setTransparentCircleRadius(20);
        pieChart.setHoleColor(Color.parseColor("#101010"));
        pieChart.setEntryLabelTextSize(10);

        pieChart.setData(data);
        pieChart.invalidate();

    }


}