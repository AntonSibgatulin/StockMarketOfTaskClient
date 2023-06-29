package ru.antonsibgatulin.stockmarketoftaskclient.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.antonsibgatulin.stockmarketoftaskclient.HomeActivity;
import ru.antonsibgatulin.stockmarketoftaskclient.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);


        View root = binding.getRoot();

        //HomeActivity.appBarLayout.setVisibility(View.VISIBLE);
        //HomeActivity.toolBarText.setText("Notification");


       return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}