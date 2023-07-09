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
import ru.antonsibgatulin.stockmarketoftaskclient.include.dilog.Dilog;
import ru.antonsibgatulin.stockmarketoftaskclient.include.user.User;

public class MessagerAdapter extends ArrayAdapter<Dilog> {

    private Context context;
    private List<Dilog> dilogList;

    private User me;

    public MessagerAdapter(@NonNull Context context, int resource, @NonNull List<Dilog> objects, User me) {
        super(context, resource, objects);
        this.context = context;
        this.dilogList = objects;
        this.me = me;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Dilog dilog = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_message_preview,parent,false);
        }

        TextView firstCharacter = convertView.findViewById(R.id.firstCharacter);

        TextView nameSurname = convertView.findViewById(R.id.nameSurname);
        TextView status = convertView.findViewById(R.id.status);


        User dataUser = null;
        if(dilog.getUser_1().getId()!=me.getId()){
            dataUser = dilog.getUser_1();
        }else{
            dataUser = dilog.getUser_2();
        }

        firstCharacter.setText(dataUser.getProfile().getName().split("")[0]);
        nameSurname.setText(dataUser.getProfile().getName()+" "+dataUser.getProfile().getSurname());
        status.setText("Security");


        return convertView;
    }
}
