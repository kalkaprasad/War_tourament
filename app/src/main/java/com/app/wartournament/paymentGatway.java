package com.app.wartournament;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class paymentGatway extends
AppCompatActivity implements  PaymentResultListener{

    String  name="karan";
    String email ="kp89@gmail.com";
    int amount=50;
    String phone="9140356115";
    String TransctionId="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gatway);
        Checkout.preload(paymentGatway.this);
    }
    public void PaymentStart(View view) {
        startPayment();
    }


    public void startPayment()
    {

        Log.e("startPayment", "Bhai chal rha h " );
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.app_icon);
        final Activity activity = paymentGatway.this;
        try
        {
            JSONObject options = new JSONObject();
            options.put("name", "War Game");
            options.put("currency", "INR");

            int ammount= 100;
            options.put("amount", ammount);
            checkout.open(activity, options);

            JSONObject preFill= new JSONObject();
            preFill.put("email",email);
            preFill.put("phone",phone);
            options.put("preFill",preFill);
        }
        catch(Exception e)
        {
            Toast.makeText(activity, "Something Error,  please try Again", Toast.LENGTH_SHORT).show();
//            return false;
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorPayId) {
       String  success_id=razorPayId;
//        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
//        final String id=preferences.getString("id",null);
        Toast.makeText(this, "Payment done Successfully.", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(paymentGatway.this,sucessPayment.class);
//
//        String names=name.getText().toString().trim();
//        String phones=phone.getText().toString().trim();
//        String emails= email.getText().toString().trim();
//        String addresses=address.getText().toString().trim();


        //  here transfer the data into the another screen..
//        intent.putExtra("transid",success_id);
//        intent.putExtra("name", names);
//        intent.putExtra("contact",phones);
//        intent.putExtra("email", emails);
//
//        intent.putExtra("fees",feeamount);
//        intent.putExtra("address", addresses);
//        intent.putExtra("id",uniqueId.getText().toString().trim().toUpperCase());
//        intent.putExtra("course",course.getText().toString().trim());
        startActivity(intent);
        finish();

    }
    @Override
    public void onPaymentError(int i, String s) {
        // AlertMessage("Your Payment Failed ..");
        Toast.makeText(this, "payment failed... ", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(this,failurePayment.class);
        startActivity(intent);
        finish();
    }

}