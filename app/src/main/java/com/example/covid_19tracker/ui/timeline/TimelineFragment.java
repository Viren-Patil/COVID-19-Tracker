package com.example.covid_19tracker.ui.timeline;

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
import com.example.covid_19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineFragment extends Fragment {

    RecyclerView rvTimeline;
    ProgressBar progressBar;

    private static final String TAG = TimelineFragment.class.getSimpleName();
    ArrayList<IndiaTimeline> indiaTimelines;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);

        //call view
        rvTimeline = root.findViewById(R.id.rvTimeline);
        progressBar = root.findViewById(R.id.progress_circular_timeline);
        rvTimeline.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTimeline.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_divider));
        rvTimeline.addItemDecoration(dividerItemDecoration);

        //Action bar title
        getActivity().setTitle("India's Timeline (2020)");

        //call Volley method
        getTimelineDataFromServer();

        return root;
    }

    private void showRecyclerView(){
        IndiaTimelineAdapter indiaTimelineAdapter = new IndiaTimelineAdapter(indiaTimelines);
        rvTimeline.setAdapter(indiaTimelineAdapter);
    }

    private void getTimelineDataFromServer() {
        String url1 = "https://api.covid19india.org/data.json";

        indiaTimelines = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("cases_time_series");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data1 = jsonArray.getJSONObject(i);
                            indiaTimelines.add(new IndiaTimeline(data1.getString("date"), data1.getString("totalconfirmed"), data1.getString("totaldeceased")));
                        }
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
                        Log.e(TAG, "onResponse: "+error);
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
