package com.app.wartournament;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class GameDetailsActivity extends AppCompatActivity {
    TextView title,type,version,map,matchType,fee,startdate,fullPrizeText;
    WebView listRules;
    ImageView expandedImage;
  //  String title,type,version,map,matchtype,fee,startdate,PerKill,Winner,Runnner1,Runnner2,PerCoin,TotalPrize,AboutMatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        listRules = findViewById(R.id.listRules);
        title = findViewById(R.id.title);
        type = findViewById(R.id.type);
        version = findViewById(R.id.version);
        map = findViewById(R.id.map);
        matchType = findViewById(R.id.matchType);
        fee = findViewById(R.id.fee);
        startdate = findViewById(R.id.startdate);
        fullPrizeText = findViewById(R.id.fullPrizeText);
        expandedImage = findViewById(R.id.expandedImage);

        title.setText(getIntent().getStringExtra("title"));
        type.setText(getIntent().getStringExtra("type"));
        version.setText(getIntent().getStringExtra("version"));
        map.setText(getIntent().getStringExtra("map"));
        matchType.setText(getIntent().getStringExtra("matchtype"));
        fee.setText(getIntent().getStringExtra("fee"));
        startdate.setText(getIntent().getStringExtra("startdate"));

        listRules.getSettings().setLightTouchEnabled(true);
        listRules.getSettings().setJavaScriptEnabled(true);
        listRules.getSettings().setGeolocationEnabled(true);
        listRules.setSoundEffectsEnabled(true);
        listRules.loadData(getIntent().getStringExtra("aboutmatch"),
                "text/html", "UTF-8");
        listRules.getSettings().setJavaScriptEnabled(true);



        Log.d("chekcress",getIntent().getStringExtra("aboutmatch"));
        Picasso.get().load("http://www.wartournament.com/Content/SliderImage/"+getIntent().getStringExtra("img")).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                expandedImage.setBackground(new BitmapDrawable(bitmap));
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


        fullPrizeText.setText("1 coin = "+getIntent().getStringExtra("percoin")+" Rupees\n\n"+"Winner - "+
                getIntent().getStringExtra("winner")+" PlayCoins\nRunner Up 1 - "+
                getIntent().getStringExtra("Runnner1")+" PlayCoins\nRunner Up 2 - "
                +getIntent().getStringExtra("Runnner2")+" PlayCoins\nPerKill - "+
                getIntent().getStringExtra("PerKill")+" PlayCoins");
    }
}
