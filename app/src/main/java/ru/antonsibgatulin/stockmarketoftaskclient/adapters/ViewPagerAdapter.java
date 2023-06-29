package ru.antonsibgatulin.stockmarketoftaskclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import ru.antonsibgatulin.stockmarketoftaskclient.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;


    public ViewPagerAdapter(Context context) {
        this.context = context;
    }


    private int images[] = {
            R.drawable.logo,
            R.drawable.computer_logo,
            R.drawable.logo,
    };


    private String titles[] = {
            "Create",
            "Earn",
            "Do it"
    };

    private String descs[] = {
            "Description 1 dbksfjkv fdshgjfkgjk snjfdsnk gfnjdsnfkgjksndkfng kdsjfndks fjkgn ksdnfg nsdknfjg nsdk fngjnsdfk ggkfdnsjk nfdksngjkfn gksndjkfk gnsdfjkgn jkgjk sfdn ",
            "Description 2 dbksfjkv fdshgjfkgjk snjfdsnk gfnjdsnfkgjksndkfng kdsjfndks fjkgn ksdnfg nsdknfjg nsdk fngjnsdfk ggkfdnsjk nfdksngjkfn gksndjkfk gnsdfjkgn jkgjk sfdn ",
            "Description 3 dbksfjkv fdshgjfkgjk snjfdsnk gfnjdsnfkgjksndkfng kdsjfndks fjkgn ksdnfg nsdknfjg nsdk fngjnsdfk ggkfdnsjk nfdksngjkfn gksndjkfk gnsdfjkgn jkgjk sfdn "
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_pager,container,false);

        ImageView imageView = view.findViewById(R.id.image);
        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.desc);

        imageView.setImageResource(images[position]);

        title.setText(titles[position]);
        desc.setText(descs[position]);

        container.addView(view);
        return view;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }



}
