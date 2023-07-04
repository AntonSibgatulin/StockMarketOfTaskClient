package ru.antonsibgatulin.stockmarketoftaskclient.ui.notifications;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.antonsibgatulin.stockmarketoftaskclient.HomeActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.adapters.NotificationAdapter;
import ru.antonsibgatulin.stockmarketoftaskclient.databinding.FragmentNotificationsBinding;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.notification.NotificationModel;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private NotificationAdapter notificationAdapter;

    private List<NotificationModel> notificationModels;

    private ProgressDialog progressDialog;

    private RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        requestQueue = Volley.newRequestQueue(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        View root = binding.getRoot();
        notificationModels = new ArrayList<>();


        notificationAdapter = new NotificationAdapter(getContext(), R.layout.fragment_notification, notificationModels);
        binding.listView.setAdapter(notificationAdapter);
        getNotification();
        //HomeActivity.appBarLayout.setVisibility(View.VISIBLE);
        //HomeActivity.toolBarText.setText("Notification");


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void getNotification() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.NOTIFICATION_LOAD + "?token=" + Constant.getToken(getContext()), response -> {

            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    NotificationModel notificationModel = new NotificationModel(jsonObject.getString("taskType"), jsonObject.getString("text"), jsonObject.getLong("time"));
                    notificationModels.add(notificationModel);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            notificationAdapter.notifyDataSetChanged();

            progressDialog.dismiss();
        },
                error -> {
                    error.printStackTrace();
                    progressDialog.dismiss();
                }) {


        };

        requestQueue.add(stringRequest);


    }
}