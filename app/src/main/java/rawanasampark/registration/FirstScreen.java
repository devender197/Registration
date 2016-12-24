package rawanasampark.registration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gofusion on 18-Mar-16.
 */
public class FirstScreen extends Activity {
    ImageView logoname,logosign;
    float i=1;
    TextView logo;
    JSONArray arr;
    private static int SPLASH_TIME_OUT = 3000;
    private ProgressDialog pDialog;
    String[] distictstatecode,districtnamehindi,districtname,districtcode,StateCode,StateName,StateNameHindi;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startingscreen);
        logo = (TextView) findViewById(R.id.textView41);
        AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(3000);
        animation1.setStartOffset(500);
        animation1.setFillAfter(true);
        logo.startAnimation(animation1);
        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Districttable(StateCode VARCHAR, districtnamehindi VARCHAR,districtname VARCHAR,districtcode VARCHAR);");
        Cursor c = db.rawQuery("select * from Districttable", null);
        c.moveToFirst();
        int rows = c.getCount();
        if(rows==0) {
            userLogin();
        }
        db.close();
        c.close();


           new Handler().postDelayed(new Runnable()
           {
                @Override
                public void run() {
                    SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS logintable(usercode VARCHAR, username VARCHAR,userpass VARCHAR);");
                    Cursor c = db.rawQuery("select * from logintable", null);
                    c.moveToFirst();
                    int rows = c.getCount();
                    Log.d("rows",rows+"");
                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    Boolean isInternetPresent = cd.isConnectingToInternet();
                    if(isInternetPresent==true)
                    {
                        if (rows != 0)
                        {
                            String sendusercode = c.getString(0);
                            Log.d("user code", sendusercode.toString());
                            Intent in = new Intent(FirstScreen.this, MainActivity.class);
                            in.putExtra("usercode",sendusercode);
                            startActivity(in);
                            finish();
                        }else{
                            Intent in = new Intent(FirstScreen.this, login.class);
                            startActivity(in);
                            finish();
                        }
                    }
                    else
                    {
                        Toast.makeText(FirstScreen.this, "Not Internet Available . Plz Check Your Internet Settings", Toast.LENGTH_SHORT).show();
                    }
                }
            }, SPLASH_TIME_OUT);

        }
    private void userLogin()
    {
            JSONObject jsonObject=new JSONObject();
            pDialog = new ProgressDialog(FirstScreen.this);
            pDialog.setMessage("Loading image. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);

            JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.districtlist), jsonObject, new Response.Listener<JSONObject>() {
            int flag=0;
            @Override
            public void onResponse(JSONObject response) {
                Log.i("error", "getBlockedUsers Response: " + response);
                try{
                    arr = response.getJSONArray("result");
                    Log.d("after arraay result","leeeeeeeeeeeeeeee");
                    if(arr!=null)
                    {
                        JSONObject   c2 = arr.getJSONObject(0);
                        JSONArray arr1 = c2.getJSONArray("value");
                        distictstatecode = new String[arr1.length()];
                        districtnamehindi = new String[arr1.length()];
                        districtname = new String[arr1.length()];
                        districtcode = new String[arr1.length()];
                        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS Districttable(StateCode VARCHAR, districtnamehindi VARCHAR,districtname VARCHAR,districtcode VARCHAR);");

                        for(int j=0;j<arr1.length();j++)
                        {
                            JSONObject   c3 = arr1.getJSONObject(j);
                            distictstatecode[j] = c3.getString("statecode");
                            districtnamehindi[j] = c3.getString("districtnamehindi");
                            districtname[j] =c3.getString("districtname");
                            districtcode[j] = c3.getString("districtcode");
                            db.execSQL("INSERT INTO Districttable VALUES('" + distictstatecode[j] + "','" + districtnamehindi[j] +"','"+districtname[j]+"','"+districtcode[j]+"')");
                            Log.d("Query","INSERT INTO Districttable VALUES('" + distictstatecode[j] + "','" + districtnamehindi[j] +"','"+districtname[j]+"','"+districtcode[j]+"')");
                            flag = 13;
                        }

                    }
                    if(flag==13)
                    {

                        userLogin1();

                    }else
                    {

                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("error",error.toString());
                pDialog.dismiss();
                Toast.makeText(FirstScreen.this, "Invalid Password", Toast.LENGTH_LONG).show();
            }


        });
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(FirstScreen.this).addToRequestQueue(mJsonObjectRequest);
    }

    private void userLogin1() {

        JSONObject jsonObject=new JSONObject();

        Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);




        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.statelist), jsonObject, new Response.Listener<JSONObject>() {
            int flag=0;
            @Override
            public void onResponse(JSONObject response) {
                Log.i("error", "getBlockedUsers Response: " + response);
                try{
                    arr = response.getJSONArray("result");
                    Log.d("after arraay result","leeeeeeeeeeeeeeee");
                    if(arr!=null)
                    {
                        JSONObject   c2 = arr.getJSONObject(0);
                        JSONArray arr1 = c2.getJSONArray("value");
                        StateCode = new String[arr1.length()];
                        StateName = new String[arr1.length()];
                        StateNameHindi = new String[arr1.length()];
                        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                        db.execSQL("CREATE TABLE IF NOT EXISTS Statetable(StateCode VARCHAR, StateName VARCHAR,StateNameHindi VARCHAR);");

                        for(int j=0;j<arr1.length();j++)
                        {
                            JSONObject   c3 = arr1.getJSONObject(j);
                            StateCode[j] = c3.getString("StateCode");
                            StateName[j] = c3.getString("StateName");
                            StateNameHindi[j] =c3.getString("StateNameHindi");
                            db.execSQL("INSERT INTO Statetable VALUES('" + StateCode[j] + "','" + StateName[j] +"','"+StateNameHindi[j]+"')");
                            Log.d("query","INSERT INTO Statetable VALUES('" + StateCode[j] + "','" + StateName[j] +"','"+StateNameHindi[j]+"')");
                            flag = 13;
                        }
                        db.close();

                    }
                    if(flag==13)
                    {
                        pDialog.dismiss();
                    }else
                    {
                        pDialog.dismiss();
                    }
                }catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("error",error.toString());
                pDialog.dismiss();
                Toast.makeText(FirstScreen.this, "Invalid Password", Toast.LENGTH_LONG).show();
            }


        });
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(FirstScreen.this).addToRequestQueue(mJsonObjectRequest);
    }
    }

