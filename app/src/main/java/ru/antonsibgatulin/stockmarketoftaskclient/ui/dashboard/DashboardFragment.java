package ru.antonsibgatulin.stockmarketoftaskclient.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.antonsibgatulin.stockmarketoftaskclient.AuthActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.CreateTaskActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.HomeActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.MainActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.databinding.FragmentDashboardBinding;
import ru.antonsibgatulin.stockmarketoftaskclient.include.constant.Constant;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        init();



        return root;
    }


    public void init(){
        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreateTaskActivity.class));

            }
        });

        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.clearData(getActivity(),"user");

                startActivity(new Intent(getActivity(), AuthActivity.class));
                getActivity().finish();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}