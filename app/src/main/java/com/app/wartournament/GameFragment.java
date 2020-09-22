package com.app.wartournament;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class GameFragment  extends Fragment {
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private List<slidermodel> slidermodelList;
    ProgressDialog progressDialog;
    sliderAdapter viewPagerAdapter;
    RelativeLayout game_start;
    ShimmerFrameLayout shimmer_view_container;
    NestedScrollView nestedScrollView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

       //slidercode

        viewPager =  view.findViewById(R.id.viewPager);
        game_start =  view.findViewById(R.id.game_start);
        shimmer_view_container =  view.findViewById(R.id.shimmer_view_container);
        nestedScrollView =  view.findViewById(R.id.nestedScrollView);

        sliderDotspanel = view.findViewById(R.id.SliderDots);

        loadsliderdata();

        game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MatchActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void setslider() {
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        shimmer_view_container.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
           if(getActivity() != null){

               getActivity().runOnUiThread(new Runnable() {
                   @Override
                   public void run() {

                       if(viewPager.getCurrentItem() == 0){
                           viewPager.setCurrentItem(1);
                       } else if(viewPager.getCurrentItem() == 1){
                           viewPager.setCurrentItem(2);
                       } else {
                           viewPager.setCurrentItem(0);
                       }

                   }
               });
           }
        }
    }


    public void loadsliderdata() {
        String url = "http://www.wartournament.com/Api/Manage/SliderList";
        // Creating string request with post method.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try{
                            JSONObject obj = new JSONObject(ServerResponse);
                            slidermodelList = new ArrayList<>();
                            if(obj.getString("Message").trim().equals("Success")) {
                                JSONArray jsonArray = new JSONArray(obj.getString("ResultRows"));
                                for (int i = 0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                      Log.d("serverrescheck",object.getString("Images"));
                                     slidermodel slidermodel = new slidermodel(object.getString("Images"));
                                     slidermodelList.add(slidermodel);
                                }
                                 viewPagerAdapter = new sliderAdapter(slidermodelList);
                                viewPager.setAdapter(viewPagerAdapter);
                                setslider();

                            }

                                }
                        catch (Exception e) {
                            Toast.makeText(getActivity(), "ex : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("serverrescheck","error:  "+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(volleyError.getMessage() == null) {
                            Log.d("checkign","error : call from null");
                            loadsliderdata();
                        }
                        else{
                        }
                        Toast.makeText(getActivity(), "error :  "+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



}
