package ru.antonsibgatulin.stockmarketoftaskclient.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.adapters.TaskAdapter;
import ru.antonsibgatulin.stockmarketoftaskclient.databinding.FragmentHomeBinding;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.Task;

public class HomeFragment extends Fragment {

    private int position = 0;

    private static final String lastSearch = "lastSearch";
    private FragmentHomeBinding binding;
    private TaskAdapter taskAdapter = null;
    private ArrayList<Task> list = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initLastData();

        taskAdapter = new TaskAdapter(list, getContext());

        binding.listView.setAdapter(taskAdapter);
        binding.listView.setVerticalScrollBarEnabled(false);

        updateData();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void updateData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.STOCK_MARKET_MAIN+""+position,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d("DEBUG",jsonArray.toString());
                        Constant.saveData(getActivity(),"user",lastSearch,jsonArray.toString());
                        initLastData();
                        taskAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                },
                error -> {
                    error.printStackTrace();
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                map.put("token",Constant.getToken(getContext()));

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    public void initLastData() {


        String lastJsonData = Constant.getUserDataFromData(getContext(), lastSearch);

        try {

            if(lastJsonData == null) return;
            JSONArray jsonArray = new JSONArray(lastJsonData);
            list.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                String data = jsonArray.getJSONObject(i).toString();
                Task task = null;
                try {
                    task = Constant.fromStringToTask(data);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                list.add(task);

            }
            this.list  = list;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}