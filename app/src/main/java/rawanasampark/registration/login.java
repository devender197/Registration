package rawanasampark.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sandeepdeora on 10/5/2016.
 */
public class login  extends ActionBarActivity {
        public static final String LOGIN_URL = "http://192.168.42.147/societapp/login.php";
        private EditText editTextUsername;
        private EditText editTextPassword;
        TextView register;
        private Button buttonLogin;
        String usercode=null;
        JSONObject c;
        String imageURL;
        private String username;
        private String password;
        String Gcm_Token;
        String success;
        private String selectedFilePath;
        int upper=1,lower=5;
        JSONArray arr;
        String[] id;
        Bitmap bitmap;
        Uri selectedFileUri;
        String strMyImagePath = null;
        String desc[];
        String url[];
        String contact[];
        String Longitute[];
        String Latitude[];
        String address[];
        private ProgressDialog pDialog;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            getSupportActionBar().hide();
            editTextUsername = (EditText) findViewById(R.id.editText);
            editTextPassword = (EditText) findViewById(R.id.editText2);
            buttonLogin = (Button) findViewById(R.id.button);
            register = (TextView) findViewById(R.id.Register);
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userLogin1();

                }
            });



        }


        private void userLogin1() {
            username = editTextUsername.getText().toString().trim();
            password = editTextPassword.getText().toString().trim();
            JSONObject jsonObject=new JSONObject();
            //##################################
            try
            {

                jsonObject.put("usr_name", username);
                jsonObject.put("usr_pass", password);
                jsonObject.put("GCM", Gcm_Token);
                Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.teamlogin), jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("error", "getBlockedUsers Response: " + response);
                    try {
                        JSONArray jArray = response.getJSONArray("result");
                        c= jArray.getJSONObject(0);
                        usercode = c.getString("usercode");
                        success = c.getString("success");
                        Log.d("usercode recieved",usercode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(usercode!=null){
                        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS logintable(usercode VARCHAR, username VARCHAR,userpass VARCHAR);");
                        if(editTextUsername.getText().toString().matches("")==false && editTextPassword.getText().toString().matches("")==false) {
                            db.execSQL("INSERT INTO logintable VALUES('" + usercode + "','" + editTextUsername.getText().toString() +"','"+editTextPassword.getText().toString()+"')");
                            Log.d("query","INSERT INTO logintable VALUES('" + usercode + "','" + editTextUsername.getText().toString() +"','"+editTextPassword.getText().toString()+"')");
                        }





                        Intent in = new Intent(login.this,MainActivity.class);
                        startActivity(in);
                        finish();
                        Toast.makeText(login.this, " Working", Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(login.this, "Invalid Password", Toast.LENGTH_LONG).show();
                        editTextUsername.setText("");
                        editTextPassword.setText("");
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.d("error",error.toString());
                    Toast.makeText(login.this, "Invalid Password", Toast.LENGTH_LONG).show();
                }


            });
            mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
        }




    }



