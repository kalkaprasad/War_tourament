package com.app.wartournament;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAcitivity extends AppCompatActivity {
    TextView txt_signup;
    GoogleSignInClient mGoogleSignInClient;
    LinearLayout facebookLogin;
    CardView cv_google;
    EditText et_email,et_password;
    RelativeLayout lyt_sign_in;
    ProgressDialog progressDialog;
    CoordinatorLayout parent_lyt;
    Button payment;
    TextView txt_show;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);
        et_password = findViewById(R.id.et_pwd);
        et_email = findViewById(R.id.et_email);
        txt_signup = findViewById(R.id.txt_signup);
        lyt_sign_in = findViewById(R.id.lyt_sign_in);
        cv_google = findViewById(R.id.cv_google);
        parent_lyt = findViewById(R.id.parent_lyt);
        facebookLogin=(LinearLayout)findViewById(R.id.FacebookLogin);
        txt_show = findViewById(R.id.txt_show);
        payment=(Button)findViewById(R.id.OpenPay);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
//        et_email.setText("9621613255");
//        et_password.setText("12341234");

      //  Login();
         //google login
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginAcitivity.this,paymentGatway.class);
                startActivity(in);
            }
        });

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(LoginAcitivity.this);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                LoginManager.getInstance().logInWithReadPermissions(LoginAcitivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("djvbdfjbv", "facebook:onSuccess:" + loginResult);
                        Profile profile = Profile.getCurrentProfile();
                        Log.d("djvbdfjbv", "facebook:onSuccess:" + profile.getId());
                        String avatar = ImageRequest.getProfilePictureUri(profile.getId(), 250, 250).toString();
                        Log.d("avtar", "avtarimages" +avatar);
                        // this is the for faebook crdentials start
                        SharedPreferences sharedPreferences = getSharedPreferences("facebooklogin",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("logged",true);
                        editor.putString("name",profile.getName());
                        editor.putString("profile_pic",avatar);
                        editor.commit();
                        // end
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("djvbdfjbv", "facebook:onCancel");
                        Toast.makeText(LoginAcitivity.this, "Cancel by user", Toast.LENGTH_SHORT).show();
                        // ...
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("djvbdfjbv", "facebook:onError", error);
                        Toast.makeText(LoginAcitivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        // ...
                    }
                });
                //Snackbar.make((View) parent_lyt, (CharSequence) "Facebook are clicked ", Snackbar.LENGTH_SHORT).show();
            }
        });

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
                if (LoginAcitivity.this.et_password.getInputType() == 144) {
                    LoginAcitivity.this.txt_show.setText("Show");
                    LoginAcitivity.this.et_password.setInputType(129);
                } else {
                    LoginAcitivity.this.txt_show.setText("Hide");
                    LoginAcitivity.this.et_password.setInputType(144);
                }
                LoginAcitivity.this.et_password.setSelection(LoginAcitivity.this.et_password.getText().length());
            }
        });

        lyt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(et_email.getText().toString().isEmpty()){
                  Snackbar.make((View) parent_lyt, (CharSequence) "Enter Username or Email Id or Mobile No", Snackbar.LENGTH_SHORT).show();
              }
              else  if(et_password.getText().toString().isEmpty()){
                  Snackbar.make((View) parent_lyt, (CharSequence) "Enter Password", Snackbar.LENGTH_SHORT).show();
              }
              else{
              Login(et_email.getText().toString(),et_password.getText().toString());
//                Intent i = new Intent(LoginAcitivity.this,MainActivity.class);
//                startActivity(i);
              }
            }
        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginAcitivity.this,ResigsterationActivity.class);
                startActivity(i);
            }
        });
    }




    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("smdbcdbsc", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {   FirebaseUser user = mAuth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("smdbcdbsc", "signInWithCredential:success");
                            Toast.makeText(LoginAcitivity.this, "facebook login success", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginAcitivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("smdbcdbsc", "signInWithCredential:failure", task.getException());
                            Snackbar.make((View) parent_lyt, (CharSequence) "Login Success", Snackbar.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }




    public void CheckExistinggoogleuser(final String uname, final String email, final String fname,String lname ) {
        String url = "http://www.wartournament.com/api/manage/UserAuthentication";
        progressDialog = new ProgressDialog(LoginAcitivity.this);
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
                                SharedPreferences sharedPreferences = getSharedPreferences("logininfo",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("logged",true);
                                editor.commit();
                                Intent i = new Intent(LoginAcitivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                                progressDialog.dismiss();
                            }
                            else{
                                //          Register(firstname,lastname,username,eMail,mobileNumber,countryCode,password,promocode);
                                GoogleSignInOptions gso = new GoogleSignInOptions.
                                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                        build();
                                GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(getApplicationContext(),gso);
                                googleSignInClient.signOut();
                                Toast.makeText(LoginAcitivity.this, "Your are not registered yet", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoginAcitivity.this);
        requestQueue.add(stringRequest);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String[] name;
            name = account.getDisplayName().split(" ");
            String[]  uid = account.getEmail().split("@");
            if(name.length>1){
                CheckExistinggoogleuser(uid[0],account.getEmail(),name[0],name[1]);
            }else{
                CheckExistinggoogleuser(uid[0],account.getEmail(),name[0],"");

            }
            //
//            CheckExistinggoogleuser(uid[0],account.getEmail(),account.getDisplayName());         //   Toast.makeText(this, ""+account.getDisplayName(), Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
         //   Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Login(final String email, final String password) {
        String url = "http://www.wartournament.com/Api/Manage/UserLogin";
        progressDialog = new ProgressDialog(LoginAcitivity.this);
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
                         JSONObject obj = new JSONObject(ServerResponse);

                         if(obj.getString("Message").trim().equals("Success")){
                             JSONArray jsonArray = new JSONArray(obj.getString("ResultRows"));
                             JSONObject object = jsonArray.getJSONObject(0);
                             SharedPreferences sharedPreferences = getSharedPreferences("logininfo",MODE_PRIVATE);
                             SharedPreferences.Editor editor = sharedPreferences.edit();
                             editor.putString("FName",""+object.getString("FName"));
                             editor.putString("Lname",""+object.getString("Lname"));
                             editor.putString("MobileNo",""+object.getString("MobileNo"));
                             editor.putString("Email",""+object.getString("Email"));
                             editor.putString("UserName",""+object.getString("UserName"));
                             editor.putString("Password",""+object.getString("Password"));
                             editor.putString("Password",""+object.getString("Password"));
                             editor.putBoolean("logged",true);
                             editor.commit();
                             Intent i = new Intent(LoginAcitivity.this,MainActivity.class);
                             startActivity(i);
                             finish();
                             Log.d("serverrescheck","  "+ServerResponse);
                         }
                         else{
                             Toast.makeText(LoginAcitivity.this, "Incorrect Information", Toast.LENGTH_SHORT).show();
                         }
                     }
                     catch (Exception e) {
                         progressDialog.dismiss();
                         Toast.makeText(LoginAcitivity.this, "ex : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                         Log.d("serverrescheck","error:  "+e.getMessage());
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
                        Toast.makeText(LoginAcitivity.this, "error :  "+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Email", email);
                params.put("Password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginAcitivity.this);
        requestQueue.add(stringRequest);
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
}
