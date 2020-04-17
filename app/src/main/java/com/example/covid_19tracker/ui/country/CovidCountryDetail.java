package com.example.covid_19tracker.ui.country;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.covid_19tracker.R;

import java.util.ArrayList;
import java.util.List;

public class CovidCountryDetail extends AppCompatActivity {

    AnyChartView anyChartView;

    TextView tvDetailCountryName, tvDetailTotalCases, tvDetailTodayCases, tvDetailTotalDeaths,
            tvDetailTodayDeaths, tvDetailTotalRecovered, tvDetailTotalActive, tvDetailTotalCritical;

    String[] name = new String[3];
    int[] val = new int[3];


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


        anyChartView = findViewById(R.id.any_chart_view);

        name[0] = "Total Deaths";
        name[1] = "Total Recovered";
        name[2] = "Total Active";

        val[0] = covidCountry.getmDeaths();
        val[1] = covidCountry.getmRecovered();
        val[2] = covidCountry.getmActive();

        // calling method for pie chart
        setupPieChart();


    }

    public void setupPieChart() {

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < name.length; i++) {
            dataEntries.add(new ValueDataEntry(name[i], val[i]));
        }

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        pie.background().fill("#1a1a1a");

        pie.data(dataEntries);
        anyChartView.setChart(pie);

    }
}