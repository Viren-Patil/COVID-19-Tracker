package com.example.covid_19tracker.ui.india;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.MainActivity;
import com.example.covid_19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IndiaFragment extends Fragment {

    RecyclerView rvIndiaCountry;
    ProgressBar progressBar;
    IndiaCountryAdapter indiaCountryAdapter;

    private static final String TAG = IndiaFragment.class.getSimpleName();
    List<IndiaCountry> indiaCountries;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_india, container, false);

        // call view
        rvIndiaCountry = root.findViewById(R.id.rvIndiaCountry);
        progressBar = root.findViewById(R.id.progress_circular_india);
        rvIndiaCountry.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvIndiaCountry.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_divider));
        rvIndiaCountry.addItemDecoration(dividerItemDecoration);

        //call list
        indiaCountries = new ArrayList<>();

        // call Volley method
        getDataFromServer();

        return root;
    }

    private void showRecyclerView() {
        indiaCountryAdapter = new IndiaCountryAdapter(indiaCountries, getActivity());
        rvIndiaCountry.setAdapter(indiaCountryAdapter);

        ItemClickSupport.addTo(rvIndiaCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedIndiaCountry(indiaCountries.get(position));
            }
        });
    }

    private void showSelectedIndiaCountry(IndiaCountry indiaCountry) {
        Intent indiaIndiaCountryDetail = new Intent(getActivity(), IndiaCountryDetail.class);
        indiaIndiaCountryDetail.putExtra("EXTRA_COVID_INDIA", indiaCountry);
        startActivity(indiaIndiaCountryDetail);
    }

    private void getDataFromServer() {
        String url = "https://api.covid19india.org/data.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                String name = "";

                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("statewise");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            if (Integer.parseInt(data.getString("confirmed")) == Integer.parseInt(data.getString("recovered"))) {
                                name = data.getString("state") + " (Corona Free)";
                            }else {
                                name = data.getString("state");
                            }


                            indiaCountries.add(new IndiaCountry(
                                    name, data.getString("recovered"),
                                    data.getString("deaths"), data.getString("active"),
                                    data.getString("confirmed")
                            ));
                        }
                        // Action Bar Title
                        getActivity().setTitle("States & Union Territories of India");
                        showRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse: " + error);
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}
