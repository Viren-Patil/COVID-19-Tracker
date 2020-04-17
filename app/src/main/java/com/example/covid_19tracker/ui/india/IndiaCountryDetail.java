package com.example.covid_19tracker.ui.india;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class IndiaCountryDetail extends AppCompatActivity {

    AnyChartView anyChartView;

    TextView tvDetailState, tvDetailConfirmed, tvDetailDeaths, tvDetailRecovered, tvDetailActive;

    String[] name = new String[3];
    int[] val = new int[3];

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


        anyChartView = findViewById(R.id.any_chart_view_state);

        name[0] = "Total Deaths";
        name[1] = "Total Recovered";
        name[2] = "Total Active";

        val[0] = Integer.parseInt(indiaCountry.getmDeaths());
        val[1] = Integer.parseInt(indiaCountry.getmRecovered());
        val[2] = Integer.parseInt(indiaCountry.getmActive());

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
