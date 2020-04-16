package com.example.covid_19tracker.ui.india;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_19tracker.R;

public class IndiaCountryDetail extends AppCompatActivity {

    TextView tvDetailState, tvDetailConfirmed, tvDetailDeaths, tvDetailRecovered, tvDetailActive;

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

    }
}
