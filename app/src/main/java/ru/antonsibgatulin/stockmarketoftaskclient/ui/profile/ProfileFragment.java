package ru.antonsibgatulin.stockmarketoftaskclient.ui.profile;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.antonsibgatulin.stockmarketoftaskclient.HomeActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.adapters.ActionTaskAdapter;
import ru.antonsibgatulin.stockmarketoftaskclient.databinding.ActivityProfileBinding;
import ru.antonsibgatulin.stockmarketoftaskclient.databinding.FragmentHomeBinding;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;
import ru.antonsibgatulin.stockmarketoftaskclient.include.tasks.ActionTask;
import ru.antonsibgatulin.stockmarketoftaskclient.include.user.User;

public class ProfileFragment extends Fragment {

    private static final String userPreferencesName = "user";
    private ActivityProfileBinding binding;
    private ActionTaskAdapter actionTaskAdapter;
    private ArrayList<ActionTask> actionTasks;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = ActivityProfileBinding.inflate(inflater, container, false);
        try {
            actionTasks = new ArrayList<>();
            actionTaskAdapter = new ActionTaskAdapter(getContext(), actionTasks);

            User user = Constant.fromStringToUser(Constant.getUserData(getContext()));

            setData(user);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        View root = binding.getRoot();
       init();
        return root;
    }


    public void init(){


       binding.posts.setAdapter(actionTaskAdapter);
        binding.posts.setVerticalScrollBarEnabled(false);

        //setListViewHeightBasedOnChildren(binding.posts);



        getMe();
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);

            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.MATCH_PARENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public void getMe() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GET_ME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    User user = Constant.fromStringToUser(response);
                    Constant.saveData(getActivity(), "user", "userData", response);
                    setData(user);


                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", Constant.getToken(getContext()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void setData(User user) {
        binding.nameTextView.setText(user.getProfile().getName() + " " + user.getProfile().getSurname());
        binding.emailTextView.setText(user.getEmail());
        actionTasks.clear();
        for (ActionTask actionTask:user.getActionTasks()){
            actionTasks.add(actionTask);
        }
        actionTaskAdapter.notifyDataSetChanged();




      /*  ViewGroup.LayoutParams params = binding.posts.getLayoutParams();
        params.height = 785 * actionTasks.size();

        binding.posts.setLayoutParams(params);
        binding.posts.requestLayout();


       */
        setListViewHeightBasedOnChildren();

    }

    public void setListViewHeightBasedOnChildren() {
        ListAdapter listAdapter = binding.posts.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(binding.posts.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, binding.posts);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = binding.posts.getLayoutParams();
        params.height = totalHeight + (binding.posts.getDividerHeight() * (listAdapter.getCount() - 1))+120;
        binding.posts.setLayoutParams(params);
        binding.posts.requestLayout();
    }


}
