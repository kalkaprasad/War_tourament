package com.app.wartournament;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
public class failurePayment extends AppCompatActivity {

    ImageView faildimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure_payment);
        faildimage=findViewById(R.id.failedpayment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Payment  Status.");
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ff512f"));
        actionBar.setBackgroundDrawable(colorDrawable);
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        faildimage.startAnimation(animation);

    }
}
