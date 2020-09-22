package com.app.wartournament;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class sliderAdapter extends PagerAdapter {

    private List<slidermodel> slidermodelList;

    public sliderAdapter(List<slidermodel> slidermodelList) {
        this.slidermodelList = slidermodelList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        final ImageView banner=view.findViewById(R.id.slide);
      //  banner.setImageResource(slidermodelList.get(position).getBanner());
        //Picasso.get().load("https://www.smileeonn.com/productImages/"+slidermodelList.get(position).getBanner()).into(banner);

        Log.d("checkurl",slidermodelList.get(position).getBanner());
        Picasso.get().load("http://www.wartournament.com/Content/SliderImage/"+slidermodelList.get(position).getBanner()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                banner.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


        container.addView(view,0);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return slidermodelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
