package com.example.covid_19tracker.ui.india;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.MainActivity;
import com.example.covid_19tracker.R;
import com.example.covid_19tracker.utilities.StateSubscriptionWorker;
import com.example.covid_19tracker.utilities.Tools;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IndiaFragment extends Fragment {

    RecyclerView rvIndiaCountry;
    ProgressBar progressBar;
    IndiaCountryAdapter indiaCountryAdapter;
//    private ArrayList<String> statesList;
    private HashMap<String, IndiaCountry> statesMap;

    private static final String TAG = IndiaFragment.class.getSimpleName();
    List<IndiaCountry> indiaCountries;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_india, container, false);

        // init
//        statesList = new ArrayList<>();
        statesMap = new HashMap<>();

        // call view
        rvIndiaCountry = root.findViewById(R.id.rvIndiaCountry);
        progressBar = root.findViewById(R.id.progress_circular_india);
        rvIndiaCountry.setLayoutManager(new LinearLayoutManager(getActivity()));

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvIndiaCountry.getContext(), DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_divider));
//        rvIndiaCountry.addItemDecoration(dividerItemDecoration);

        //call list
        indiaCountries = new ArrayList<>();

        // call Volley method
        getDataFromServer();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notification_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notifications:

                final String[] states = statesMap.keySet().toArray(new String[]{});

                View statesView = getLayoutInflater().inflate(R.layout.layout_state_subscription_selector, null, false);
                ListView statesListView = (ListView) statesView.findViewById(R.id.states_list_view);
                statesListView.setAdapter(new StatesAdapter(getContext(), android.R.layout.simple_list_item_1, states));

                final ArrayList<String> selectedStates = new ArrayList<>();
                new AlertDialog.Builder(getActivity())
                        .setTitle("Notifications")
                        .setMultiChoiceItems(statesMap.keySet().toArray(new String[]{}), null, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) selectedStates.add(states[which]);
                                        else selectedStates.remove(states[which]);
                                    }
                                })
                        .setPositiveButton(R.string.hook, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Tools.Companion.clear(getContext());
                                int i = 0;
                                for (String selectedState : selectedStates) {
                                    Tools.Companion.cache(
                                            getContext(),
                                            selectedState,
                                            Integer.parseInt(statesMap.get(selectedState).getmConfirmed()),
                                            i++
                                    );
                                }

                                // enqueue state subscription notification work only when network is present
                                if (selectedStates.size() != 0) {
                                    Constraints constraints = new Constraints.Builder()
                                            .setRequiredNetworkType(NetworkType.CONNECTED)
                                            .build();
                                    WorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(StateSubscriptionWorker.class, 2, TimeUnit.HOURS)
                                            .setConstraints(constraints)
                                            .build();
                                    WorkManager.getInstance(getContext()).enqueue(uploadWorkRequest);
                                    Toast.makeText(getContext(), "Subscriptions updated", Toast.LENGTH_SHORT).show();
                                } else Toast.makeText(getContext(), "Subscriptions cleared", Toast.LENGTH_SHORT).show();


                                // [revisit] remove
//                                SharedPreferences preferences = getContext().getSharedPreferences("state_subscription", Context.MODE_PRIVATE);
//                                Map<String, ?> allSavedStates = preferences.getAll();
//                                StringBuilder op = new StringBuilder();
//                                for (String key : allSavedStates.keySet())
//                                    op.append(key).append(":").append(allSavedStates.get(key)).append("\n");
//                                Toast.makeText(getContext(), op.toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //
                            }
                        })
                        .create()
                        .show();


//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                PendingIntent pendingIntent = PendingIntent.getActivity(
//                        getContext(), 0, intent, 0);
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                        getContext(), getString(R.string.channel_id))
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setContentTitle(state)
//                        .setContentText("Confirmed cases: 3202 (+5 in 4h)")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setContentIntent(pendingIntent)
//                        .setAutoCancel(true);
//                NotificationManagerCompat notificationManager =
//                        NotificationManagerCompat.from(getContext());
//                notificationManager.notify(121, builder.build());


                // Integer.parseInt(Tools.Companion.fetch(getContext(), state, Tools.Companion.getTYPE_NOTIFICATION_ID()))
//                covidCountries.clear();
//                progressBar.setVisibility(View.VISIBLE);
//                getDataFromServerSortAlphabet();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("statewise");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            IndiaCountry thisState = new IndiaCountry(
                                    data.getString("state"), data.getString("recovered"),
                                    data.getString("deaths"), data.getString("active"),
                                    data.getString("confirmed")
                            );

                            statesMap.put(data.getString("state"), thisState);
//                            statesList.add(data.getString("state"));


                            indiaCountries.add(thisState);
                        }

                        // Action Bar Title
                        getActivity().setTitle("States & Union Territories of India");

                        // set has option menu as true because we have menu
                        setHasOptionsMenu(true);

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

    // adapter for states selector
    private class StatesAdapter extends ArrayAdapter<String> {

        private String[] states;

        public StatesAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
            super(context, resource, objects);
            states = objects;
        }

        @NotNull
        @Override
        public View getView(int position, View convertView, @NotNull ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_checkbox, container, false);
            }
            ((TextView) convertView.findViewById(R.id.state_name)).setText(states[position]);
            return convertView;
        }
    }

}
