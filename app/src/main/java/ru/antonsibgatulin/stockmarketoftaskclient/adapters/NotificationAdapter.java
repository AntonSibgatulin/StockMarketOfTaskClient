package ru.antonsibgatulin.stockmarketoftaskclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.antonsibgatulin.stockmarketoftaskclient.R;
import ru.antonsibgatulin.stockmarketoftaskclient.include.notification.NotificationModel;

public class NotificationAdapter extends ArrayAdapter<NotificationModel> {



    private List<NotificationModel> notificationModels;
    private Context context;


    public NotificationAdapter(@NonNull Context context, int resource,@NonNull List<NotificationModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.notificationModels = objects;
    }

    @Nullable
    @Override
    public NotificationModel getItem(int position) {
        return notificationModels.get(position);
    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NotificationModel notificationModel = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_notification,parent,false);
        }


        TextView mainText = convertView.findViewById(R.id.mainText);
        TextView textSecond =  convertView.findViewById(R.id.textSecond);

        mainText.setText(notificationModel.getMainText());
        textSecond.setText(notificationModel.getText());

        return convertView;
    }
}
