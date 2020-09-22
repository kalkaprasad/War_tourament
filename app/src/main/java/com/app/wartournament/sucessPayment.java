package com.app.wartournament;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class sucessPayment extends AppCompatActivity {

    TextView successmsg;
    RequestQueue requestQueue;
    String name,email,phone,address,ammount,transcationID;
    ImageView successpaymentimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess_payment);
        successmsg=findViewById(R.id.successmsg);
        requestQueue= Volley.newRequestQueue(this);
        successpaymentimage= findViewById(R.id.sucesspay);
        // this is the for the aimate the  success animation image view..
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        successpaymentimage.startAnimation(animation);
        successmsg.startAnimation(animation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation((float) 10.0);
        actionBar.setTitle("Payment  Status.");
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#c33764"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);



    }
}
