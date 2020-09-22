package com.app.wartournament;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResigsterationActivity extends AppCompatActivity {
    TextView txt_sign_in;
    ProgressDialog progressDialog;
    public EditText et_coupon;
    /* access modifiers changed from: private */
    public EditText et_coupon_social;
    private EditText et_email;
    private EditText et_fname;
    private EditText et_lname;
    private EditText et_phn;
    AlertDialog alertDialog;
    public CountryCodePicker ccp;
    CardView cv_google;
    /* access modifiers changed from: private */
    public EditText et_phnone_botom;
    /* access modifiers changed from: private */
    public EditText et_pwd;
    private EditText et_uname;
    RelativeLayout lyt_signup;
    TextView txt_show;
    String firstname,lastname,username,mobileNumber,eMail,password,promocode,countryCode;
    public CoordinatorLayout parent_lyt;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigsteration);
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_uname = findViewById(R.id.et_uname);
        et_phn = findViewById(R.id.et_phn);
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        et_coupon = findViewById(R.id.et_coupon);
        lyt_signup = findViewById(R.id.lyt_signup);
        parent_lyt = findViewById(R.id.parent_lyt);
        txt_show = findViewById(R.id.txt_show);
        cv_google = findViewById(R.id.cv_google);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);


        txt_sign_in = findViewById(R.id.txt_sign_in);
        txt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  ResigsterationActivity.super.onBackPressed();
            }
        });

        //google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        cv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 1);
            }
        });




        txt_show.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ResigsterationActivity.this.et_pwd.getInputType() == 144) {
                    ResigsterationActivity.this.txt_show.setText("Show");
                    ResigsterationActivity.this.et_pwd.setInputType(129);
                } else {
                    ResigsterationActivity.this.txt_show.setText("Hide");
                    ResigsterationActivity.this.et_pwd.setInputType(144);
                }
                ResigsterationActivity.this.et_pwd.setSelection(ResigsterationActivity.this.et_pwd.getText().length());
            }
        });

        lyt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = et_fname.getText().toString().trim();
                lastname = et_lname.getText().toString().trim();
                username = et_uname.getText().toString().trim();
                eMail = et_email.getText().toString().trim();
                mobileNumber = et_phn.getText().toString().trim();
                countryCode = ccp.getSelectedCountryCode().toString().trim();
                password = et_pwd.getText().toString().trim();
                promocode = et_coupon.getText().toString().trim();
                if(!validatefirstname() || !validatelastname() || !validateusername() || !validateemail() || !validatecountrycode() || !validatemobilenumber() || !validatepassword()){
                    lyt_signup.setEnabled(true);
                }
                else{
                    if(promocode.isEmpty()){
                        promocode = "";
                    }
//                    Toast.makeText(ResigsterationActivity.this, ""+firstname+" "+lastname+""+username+" "+eMail+" "+mobileNumber+" "+countryCode+" "+password+" "+promocode, Toast.LENGTH_SHORT).show();
                    CheckExistinguser(username,eMail,mobileNumber,firstname,lastname,countryCode,password,promocode);
                }

//                CheckExistinguser(username,eMail,mobileNumber);

            }
        });
    }

    public void CheckExistinguser(final String username, final String eMail, final String mobileNumber, final String firstname, final String lastname, final String countryCode, final String password, final String promocode) {
        String url = "http://www.wartournament.com/api/manage/UserAuthentication";
        progressDialog = new ProgressDialog(ResigsterationActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        // Creating string request with post method.
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try{
                            //   progressDialog.dismiss();
                            //   Toast.makeText(.this, "res : "+ServerResponse, Toast.LENGTH_SHORT).show();
                            //  JSONObject jsonObject = new JSONObject(ServerResponse);
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            JSONObject obj = jsonArray.getJSONObject(0);
                            Log.d("serverrescheck",""+obj.getString("Email")+""+" "+obj.getString("Mobile")+" "+obj.getString("UserId"));
                             if(!obj.getString("Email").equals("Email Not Match")){
                                 Toast.makeText(ResigsterationActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();

                             }
                             else if(!obj.getString("Mobile").equals("Mobile Not Match")) {
                                 Toast.makeText(ResigsterationActivity.this, "Mobile no. already exists", Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();

                             }
                             else if(!obj.getString("UserId").equals("UserName Not Match")) {
                                 Toast.makeText(ResigsterationActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                             }
                             else{
                            Register(firstname,lastname,username,eMail,mobileNumber,countryCode,password,promocode);
                             }
                        }
                        catch (Exception e) {
                            progressDialog.dismiss();
                            // Toast.makeText(LoginAcitivity.this, "ex : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("serverrescheck","e"+e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(volleyError.getMessage() == null) {
                            Log.d("checkign","error : call from null");
                        }
                        else{
                            progressDialog.dismiss();
                        }
                        // Toast.makeText(LoginAcitivity.this, "error :  "+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MobileNo", mobileNumber);
                params.put("Email", eMail);
                params.put("UserName", username);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ResigsterationActivity.this);
        requestQueue.add(stringRequest);
    }


    public void Register(final String firstname, final String lastname, final String username, final String eMail, final String mobileNumber, String countryCode, final String password, final String promocode) {
        String url = "http://www.wartournament.com/Api/Manage/Registration";
        // Creating string request with post method.
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                   Toast.makeText(ResigsterationActivity.this, "res : "+ServerResponse, Toast.LENGTH_SHORT).show();
                   //  JSONObject jsonObject = new JSONObject(ServerResponse);
                        try{
                            JSONObject obj = new JSONObject(ServerResponse);
                            Log.d("chedkdds",ServerResponse);
                            if(obj.getString("Message").trim().equals("Success")){
                                progressDialog.dismiss();
                                et_fname.setText("");
                                et_lname.setText("");
                                et_uname.setText("");
                                et_email.setText("");
                                et_phn.setText("");
                                et_pwd.setText("");
                                et_coupon.setText("");
                                Toast.makeText(ResigsterationActivity.this, "Registration Successfully done", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }
                        else{
                                progressDialog.dismiss();
                                Toast.makeText(ResigsterationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){

                        }
                        Log.d("serverrescheck",username+"  "+ServerResponse);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(volleyError.getMessage() == null) {
                            Log.d("checkign","error : call from null");
                        }
                        else{
                            progressDialog.dismiss();
                        }
                        Toast.makeText(ResigsterationActivity.this, "error :  "+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Fname", firstname);
                params.put("Lname", lastname);
                params.put("MobileNo", mobileNumber);
                params.put("Email", eMail);
                params.put("UserName", username);
                params.put("password", password);
                params.put("PromoCode", promocode);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ResigsterationActivity.this);
        requestQueue.add(stringRequest);
    }


    public void CheckExistinggoogleuser(final String uname, final String email, final String fname, final String lname ) {
        String url = "http://www.wartournament.com/api/manage/UserAuthentication";
        progressDialog = new ProgressDialog(ResigsterationActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        // Creating string request with post method.
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        try{
                            progressDialog.dismiss();
                            JSONArray jsonArray = new JSONArray(ServerResponse);
                            JSONObject obj = jsonArray.getJSONObject(0);
                            Log.d("serverrescheck",""+obj.getString("Email")+""+" "+obj.getString("Mobile")+" "+obj.getString("UserId"));
                            if(!obj.getString("Email").equals("Email Not Match")){
                                GoogleSignInOptions gso = new GoogleSignInOptions.
                                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                        build();
                                GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(getApplicationContext(),gso);
                                googleSignInClient.signOut();
                               Toast.makeText(ResigsterationActivity.this, "Your are already Registered", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else{
                                //          Register(firstname,lastname,username,eMail,mobileNumber,countryCode,password,promocode);
                                showmydailog(uname,email,fname,lname);
                            }
                        }
                        catch (Exception e) {
                            progressDialog.dismiss();
                            // Toast.makeText(LoginAcitivity.this, "ex : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("serverrescheck","e"+e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(volleyError.getMessage() == null) {
                            Log.d("checkign","error : call from null");
                        }
                        else{
                            progressDialog.dismiss();
                        }
                        // Toast.makeText(LoginAcitivity.this, "error :  "+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("MobileNo", "121212121");
                params.put("Email", email);
                params.put("UserName", uname);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ResigsterationActivity.this);
        requestQueue.add(stringRequest);
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String[]  uid = account.getEmail().split("@");
            String[] name;
            name = account.getDisplayName().split(" ");
            if(name.length>1){
                CheckExistinggoogleuser(uid[0],account.getEmail(),name[0],name[1]);
            }else{
                CheckExistinggoogleuser(uid[0],account.getEmail(),name[0],"");

            }
        } catch (ApiException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showmydailog(final String uname, final String email, final String fname, final String lname) {
        AlertDialog.Builder dailog=new AlertDialog.Builder(ResigsterationActivity.this);
        LayoutInflater inflater=LayoutInflater.from(ResigsterationActivity.this);
        View dailogview=inflater.inflate(R.layout.bottom_sheet_phone_number,null);
        dailog.setView(dailogview);
        alertDialog=dailog.create();
        alertDialog.show();
        final EditText et_phone =dailogview.findViewById(R.id.et_phnone_botom);
        final EditText et_code  = dailogview.findViewById(R.id.et_coupon_social);
        CardView card_submit_number = dailogview.findViewById(R.id.card_submit_number);
        card_submit_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_phone.getText().toString().isEmpty()) {
                    Toast.makeText(ResigsterationActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (et_phone.getText().toString().length() >= 5 && et_phone.getText().toString().length() <= 15) {
                    if(et_code.getText().toString().isEmpty()){
                        CheckExistinguser(uname,email,et_phone.getText().toString(),fname,lname,"","","");
                    }
                    else{
                        CheckExistinguser(uname,email,et_phone.getText().toString(),fname,lname,"","",et_code.getText().toString());
                    }
                }
                else{
                    Toast.makeText(ResigsterationActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }




    public boolean validatefirstname() {
        if (this.firstname.isEmpty()) {
            Snackbar.make((View) this.parent_lyt, (CharSequence) "Please enter valid first name.", Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (this.firstname.matches("[a-zA-Z]*")) {
            return true;
        } else {
            Snackbar.make((View) this.parent_lyt, (CharSequence) "Special character are not allow in first name.", Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();
        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(getApplicationContext(),gso);
        googleSignInClient.signOut();
        super.onBackPressed();
    }

    public boolean validatelastname() {
        if (this.lastname.isEmpty()) {
            Snackbar.make((View) this.parent_lyt, (CharSequence) "Please enter valid last name.",  Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (this.lastname.matches("[a-zA-Z]*")) {
            return true;
        } else {
            Snackbar.make((View) this.parent_lyt, (CharSequence) "Special character are not allow in last name.",  Snackbar.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isvalideemail(String str) {
        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    public boolean validateemail() {
        if (!this.eMail.isEmpty() && isvalideemail(this.eMail)) {
            return true;
        }
        Snackbar.make((View) this.parent_lyt, (CharSequence) "Please enter valid email address.",  Snackbar.LENGTH_SHORT).show();
        return false;
    }

    public boolean validateusername() {
        if (!this.username.isEmpty()) {
            return true;
        }
        Snackbar.make((View) this.parent_lyt, (CharSequence) "Please enter valid Username.",  Snackbar.LENGTH_SHORT).show();
        return false;
    }

    public boolean validatepassword() {
        if (this.password.isEmpty()) {
            Snackbar.make((View) this.parent_lyt, (CharSequence) "Please enter a valid password.",  Snackbar.LENGTH_SHORT).show();
        } else if (this.password.length() >= 8 && this.password.length() <= 20) {
            return true;
        }
        Snackbar.make((View) this.parent_lyt, (CharSequence) "Password length must be 8-20 characters.",  Snackbar.LENGTH_SHORT).show();
        return false;
    }

    public boolean validatepromocode() {
        if (!this.promocode.isEmpty()) {
            return true;
        }
        Snackbar.make((View) this.parent_lyt, (CharSequence) "Please enter valid promo code.",  Snackbar.LENGTH_SHORT).show();
        return false;
    }

    public boolean validatemobilenumber() {
        if (this.mobileNumber.isEmpty()) {
            Snackbar.make((View) this.parent_lyt, (CharSequence) "Please enter a valid mobile number.",  Snackbar.LENGTH_SHORT).show();
        } else if (this.mobileNumber.length() >= 5 && this.mobileNumber.length() <= 15) {
            return true;
        }
        Snackbar.make((View) this.parent_lyt, (CharSequence) "Mobile number length must be 5-15 digits.",  Snackbar.LENGTH_SHORT).show();
        return false;
    }

    public boolean validatecountrycode() {
        if (this.countryCode.length() >= 1) {
            return true;
        }
        Snackbar.make((View) this.parent_lyt, (CharSequence) "Please pick your country code.",  Snackbar.LENGTH_SHORT).show();
        return false;
    }


}
