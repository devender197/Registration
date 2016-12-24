package rawanasampark.registration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sandeepdeora on 9/2/2016.
 */
public class Registration2 extends ActionBarActivity {
    EditText parentalVillage,currentAddress,permenantAddress,permentvillage,currentvillage,Email,contactNo;
    TextView parstate,pardistrict,parcity,curstate,curdistrict,curcity;
    String[] countryname;
    String[] countryID;
    String[] statename;
    String[] stateID;
    String[] cityname;
    String[] cityID;
    String countryURL;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ListView list2;
    private ArrayAdapter<String> adapter2;
    private ArrayList<String> arrayList2;
    private ArrayAdapter<String> adapter4;
    private ArrayList<String> arrayList4;
    private ArrayAdapter<String> adapter3;
    private ArrayList<String> arrayList3;
    int flag1,flag2 ,flag3;
    String countryIDselect;
    String StateIDselect;
    String Cityselect;
    Button next;
    PopupWindow mpopup;
    String column1;
    String statecode[],districtcode[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration2);
        getSupportActionBar().hide();
        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT reg2 FROM Registration ", null);
        if(c.moveToFirst()){
            do{
                //assing values
                column1 = c.getString(0);


            }while(c.moveToNext());
        }
        c.close();
        db.close();
        if(column1.matches("1")){
            Intent intent = new Intent(Registration2.this,Registration3.class);
            startActivity(intent);
            finish();
            Log.d("moving","forward2");
        }
        parentalVillage  = (EditText) findViewById(R.id.RegParentalVillage);
        currentAddress   = (EditText) findViewById(R.id.RegCurrentAddress);
        permenantAddress = (EditText) findViewById(R.id.regpermentaddress);
        permentvillage   = (EditText) findViewById(R.id.RegSpinnerpervillage);
        currentvillage   = (EditText) findViewById(R.id.RegSpinnercurvillage);
        parstate         = (TextView)  findViewById(R.id.RegSpinnerperstate);
        pardistrict      = (TextView)  findViewById(R.id.RegSpinnerperdistrict);
        parcity          = (TextView)  findViewById(R.id.RegSpinnerpercity);
        curstate         = (TextView)  findViewById(R.id.RegSpinnercurstate);
        curdistrict      = (TextView)  findViewById(R.id.RegSpinnercurdistrict);
        curcity          = (TextView)  findViewById(R.id.RegSpinnercurcity);
        next             = (Button) findViewById(R.id.Reg2nextbutton);
        Email            = (EditText) findViewById(R.id.reg4Emailtextbox);
        contactNo        = (EditText) findViewById(R.id.reg2contactNo);

        arrayList = new ArrayList<String>();
        SQLiteDatabase db1 = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        Cursor c1 = db1.rawQuery("SELECT * FROM Statetable ", null);
        statecode = new String[c1.getCount()];
        if(c1.moveToFirst()){
            int i=0;

            do{
                arrayList.add(i,c1.getString(1));
                statecode[i] = c1.getString(0);
                i++;
            }while(c1.moveToNext());
        }
        c1.close();
        db1.close();

        adapter = new ArrayAdapter<String>(Registration2.this, R.layout.spinner, arrayList);
        adapter4 = new ArrayAdapter<String>(Registration2.this, R.layout.spinner, arrayList);
        pardistrict.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
                View contentView = mpopup.getContentView();
                contentView.getBackground().setAlpha(150);
                mpopup.setAnimationStyle(android.R.style.Animation_Translucent);
                mpopup.setFocusable(true);
                mpopup.setTouchable(true);
                mpopup.setOutsideTouchable(true);
                mpopup.setTouchInterceptor(new View.OnTouchListener()
                {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                        {
                            mpopup.dismiss();
                            return true;
                        }
                        return false;
                    }

                });
                mpopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
                ListView list = (ListView) layout.findViewById(R.id.listView);
                list.setAdapter(adapter2);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        pardistrict.setText(arrayList2.get(position));
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            pardistrict.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration2.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
        parstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
                View contentView = mpopup.getContentView();
                contentView.getBackground().setAlpha(150);
                mpopup.setAnimationStyle(android.R.style.Animation_Translucent);
                mpopup.setFocusable(true);
                mpopup.setTouchable(true);
                mpopup.setOutsideTouchable(true);
                mpopup.setTouchInterceptor(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                        {
                            mpopup.dismiss();
                            return true;
                        }
                        return false;
                    }

                });
                mpopup.showAtLocation(layout, Gravity.CENTER, 0, 0);

                ListView list = (ListView) layout.findViewById(R.id.listView);
                list.setAdapter(adapter);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        parstate.setText(arrayList.get(position));
                        SQLiteDatabase db1 = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                        Cursor c1 = db1.rawQuery("SELECT * FROM Districttable where StateCode = '"+statecode[position]+"'", null);
                        arrayList2 = new ArrayList<String>();
                        districtcode = new String[c1.getCount()];
                        if(c1.moveToFirst()){
                            int i=0;

                            do{
                                arrayList2.add(i,c1.getString(2));
                                districtcode[i] = c1.getString(3);
                                i++;
                            }while(c1.moveToNext());
                        }
                        c1.close();
                        db1.close();
                        adapter2 = new ArrayAdapter<String>(Registration2.this, R.layout.spinner, arrayList2);
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            parstate.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration2.this, "Either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        curstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
                 //View contentView = mpopup.getContentView();
              //  contentView.getBackground().setAlpha(150);
                mpopup.setAnimationStyle(android.R.style.Animation_Translucent);
                mpopup.setFocusable(true);
                mpopup.setTouchable(true);
                mpopup.setOutsideTouchable(true);
                mpopup.setTouchInterceptor(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                        {
                            mpopup.dismiss();
                            return true;
                        }
                        return false;
                    }

                });
                mpopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
                ListView list = (ListView) layout.findViewById(R.id.listView);
                list.setAdapter(adapter);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        curstate.setText(arrayList.get(position));
                        arrayList3 = new ArrayList<String>();
                        SQLiteDatabase db1 = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                        Cursor c1 = db1.rawQuery("SELECT * FROM Districttable where StateCode = '"+statecode[position]+"'", null);
                        districtcode = new String[c1.getCount()];
                        if(c1.moveToFirst()){
                            int i=0;

                            do{
                                arrayList3.add(i,c1.getString(2));
                                districtcode[i] = c1.getString(3);
                                i++;
                            }while(c1.moveToNext());
                        }
                        c1.close();
                        db1.close();
                        adapter3 = new ArrayAdapter<String>(Registration2.this, R.layout.spinner, arrayList3);
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            curstate.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration2.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        curdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
//                View contentView = mpopup.getContentView();
//                contentView.getBackground().setAlpha(150);
                mpopup.setAnimationStyle(android.R.style.Animation_Translucent);
                mpopup.setFocusable(true);
                mpopup.setTouchable(true);
                mpopup.setOutsideTouchable(true);
                mpopup.setTouchInterceptor(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                        {
                            mpopup.dismiss();
                            return true;
                        }
                        return false;
                    }

                });
                mpopup.showAtLocation(layout, Gravity.CENTER, 0, 0);

                ListView list = (ListView) layout.findViewById(R.id.listView);
                list.setAdapter(adapter3);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        curdistrict.setText(arrayList3.get(position));
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            curdistrict.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration2.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
//        parstate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View layout = inflater.inflate(R.layout.popuplist, null);
//                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
//                mpopup.setBackgroundDrawable(new BitmapDrawable());
//                View contentView = mpopup.getContentView();
//                contentView.getBackground().setAlpha(150);
//                mpopup.setAnimationStyle(android.R.style.Animation_Translucent);
//                mpopup.setFocusable(true);
//                mpopup.setTouchable(true);
//                mpopup.setOutsideTouchable(true);
//                mpopup.setTouchInterceptor(new View.OnTouchListener() {
//
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
//                        {
//                            mpopup.dismiss();
//                            return true;
//                        }
//                        return false;
//                    }
//
//                });
//                mpopup.showAtLocation(layout, Gravity.CENTER, 0, 0);
//
//                ListView list = (ListView) layout.findViewById(R.id.listView);
//                list.setAdapter(adapter4);
//                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
//                Button submit = (Button) layout.findViewById(R.id.button10);
//                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        parstate.setText(arrayList.get(position));
//                        arrayList2 = new ArrayList<String>();
//                        SQLiteDatabase db1 = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
//                        Cursor c1 = db1.rawQuery("SELECT * FROM Districttable where StateCode = '"+statecode[position]+"'", null);
//                        districtcode = new String[c1.getCount()];
//                        if(c1.moveToFirst()){
//                            int i=0;
//
//                            do{
//                                arrayList3.add(i,c1.getString(2));
//                                districtcode[i] = c1.getString(3);
//                                i++;
//                            }while(c1.moveToNext());
//                        }
//                        c1.close();
//                        db1.close();
//
//                        mpopup.dismiss();
//                    }
//                });
//                submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(edit.getText().toString().length()!=0) {
//                            parstate.setText(edit.getText().toString());
//                            mpopup.dismiss();
//                        }else{
//                            Toast.makeText(Registration2.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                if(checkValidation()) {
                    db.execSQL("UPDATE Registration set ParentalVillage ='" + parentalVillage.getText().toString() + "', CurrentAddress ='" + currentAddress.getText().toString() + "',currentState ='" + curstate.getText().toString() + "', currentDistrict ='" + curdistrict.getText().toString() + "', currentCity ='" + curcity.getText().toString() + "', currentVillage ='" + currentvillage.getText().toString() + "',ParmanentAddress  ='" + permenantAddress.getText().toString() + "', perState  ='" + parstate.getText().toString() + "', perDistrict  ='" + pardistrict.getText().toString() + "', perCity  ='" + parcity.getText().toString() + "',perVillage  ='" + permentvillage.getText().toString() + "',reg2  ='" + 1+ "', Email  ='" + Email.getText().toString()  +"', ContactNo  ='" + contactNo .getText().toString()  + "'");
                    Intent intent = new Intent(Registration2.this,Registration3.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
//    private void countrylist() {
//
//        JSONObject jsonObject=new JSONObject();
//        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, countryURL, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("error", "getBlockedUsers Response: " + response);
//                try {
//                    final JSONArray arr1;
//                    arr1 = response.getJSONArray("Result");
//
//                Log.d("after arraay result","leeeeeeeeeeeeeeee");
//                Log.d("Size of array",""+arr1.length());
//                if(arr1.length()!=0) {
//                    JSONObject c1 = arr1.getJSONObject(0);
//                    Log.d("Size of array", "" + arr1.length());
//                    countryname = new String[arr1.length() + 1];
//                    countryID = new String[arr1.length() + 1];
//                    countryname[0] = "0";
//                    countryID[0] = "0";
//                    arrayList = new ArrayList<String>();
//                    Log.d("outside loop result", "leeeeeeeeeeeeeeee");
//                    arrayList.add(0, "Select Country");
//                    for (int j = 0; j < arr1.length(); j++) {
//                        JSONObject c2 = arr1.getJSONObject(j);
//                        countryname[j + 1] = c2.getString("Name");
//                        countryID[j + 1] = c2.getString("Code");
//                        arrayList.add(j + 1, countryname[j + 1]);
//                        flag1 = 13;
//
//                    }
//
//                }
//
//                        if(flag1==13){
//                            adapter = new ArrayAdapter<String>(Registration2.this, R.layout.spinner, arrayList);
//                            parstate.setAdapter(adapter);
//                            parstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                   // new LoadAllProducts3(countryID[position]).execute();
//                                    countryIDselect = countryID[position];
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
//
//                                }
//                            });
//
//                        }else{
//                            Toast.makeText(Registration2.this, "Server down", Toast.LENGTH_LONG).show();
//                        }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                Log.d("error",error.toString());
//                Toast.makeText(Registration2.this, "Error Occured", Toast.LENGTH_LONG).show();
//            }
//
//
//        });
//        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
//    }
//
//
//    private void statelist() {
//
//        JSONObject jsonObject=new JSONObject();
//
//        //##################################
//
//
//        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, countryURL, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("error", "getBlockedUsers Response: " + response);
//                try {
//                    final JSONArray arr1;
//                    arr1 = response.getJSONArray("Result");
//
//                    Log.d("after arraay result","leeeeeeeeeeeeeeee");
//                    Log.d("Size of array",""+arr1.length());
//                    if(arr1.length()!=0) {
//                        JSONObject c1 = arr1.getJSONObject(0);
//                        Log.d("Size of array", "" + arr1.length());
//                        statename = new String[arr1.length() + 1];
//                        stateID = new String[arr1.length() + 1];
//                        statename[0] = "0";
//                        stateID[0] = "0";
//                        arrayList2 = new ArrayList<String>();
//                        Log.d("outside loop result", "leeeeeeeeeeeeeeee");
//                        arrayList2.add(0, "Select Country");
//                        for (int j = 0; j < arr1.length(); j++) {
//                            JSONObject c2 = arr1.getJSONObject(j);
//                            statename[j + 1] = c2.getString("Name");
//                            stateID[j + 1] = c2.getString("Code");
//                            arrayList2.add(j + 1, statename[j + 1]);
//                            flag2 = 13;
//
//                        }
//
//                    }
//
//                    if(flag2==13){
//                        adapter2 = new ArrayAdapter<String>(Registration2.this, R.layout.spinner, arrayList);
//                        pardistrict.setAdapter(adapter2);
//                        pardistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                              //  new LoadAllProducts3( stateID[position]).execute();
//                                StateIDselect =  stateID[position];
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
//
//                    }else{
//                        Toast.makeText(Registration2.this, "Server down", Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                Log.d("error",error.toString());
//                Toast.makeText(Registration2.this, "Error Occured", Toast.LENGTH_LONG).show();
//            }
//
//
//        });
//        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
//    }
//
//    private void citylist() {
//
//        JSONObject jsonObject=new JSONObject();
//
//        //##################################
//
//
//        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, countryURL, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("error", "getBlockedUsers Response: " + response);
//                try {
//                    final JSONArray arr1;
//                    arr1 = response.getJSONArray("Result");
//
//                    Log.d("after arraay result","leeeeeeeeeeeeeeee");
//                    Log.d("Size of array",""+arr1.length());
//                    if(arr1.length()!=0) {
//                        JSONObject c1 = arr1.getJSONObject(0);
//                        Log.d("Size of array", "" + arr1.length());
//                        cityname = new String[arr1.length() + 1];
//                        cityID = new String[arr1.length() + 1];
//                        cityname[0] = "0";
//                        cityID[0] = "0";
//                        arrayList3 = new ArrayList<String>();
//                        Log.d("outside loop result", "leeeeeeeeeeeeeeee");
//                        arrayList3.add(0, "Select Country");
//                        for (int j = 0; j < arr1.length(); j++) {
//                            JSONObject c2 = arr1.getJSONObject(j);
//                            cityname[j + 1] = c2.getString("Name");
//                            cityID[j + 1] = c2.getString("Code");
//                            arrayList3.add(j + 1, cityname[j + 1]);
//                            flag3 = 13;
//
//                        }
//
//                    }
//
//                    if(flag3==13){
//                        adapter3 = new ArrayAdapter<String>(Registration2.this, R.layout.spinner, arrayList);
//                        parcity.setAdapter(adapter3);
//                        parcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                               // new LoadAllProducts3( cityID[position]).execute();
//                                Cityselect =  cityID[position];
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
//
//                    }else{
//                        Toast.makeText(Registration2.this, "Server down", Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                Log.d("error",error.toString());
//                Toast.makeText(Registration2.this, "Error Occured", Toast.LENGTH_LONG).show();
//            }
//
//
//        });
//        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
//    }
    private boolean checkValidation() {
        parentalVillage  = (EditText) findViewById(R.id.RegParentalVillage);
        currentAddress   = (EditText) findViewById(R.id.RegCurrentAddress);
        permenantAddress = (EditText) findViewById(R.id.regpermentaddress);
        permentvillage   = (EditText) findViewById(R.id.RegSpinnerpervillage);
        currentvillage   = (EditText) findViewById(R.id.RegSpinnercurvillage);
        boolean ret = true;
        if (!Validation.hasText(parentalVillage)) ret = false;
        if (!Validation.hasText(currentAddress)) ret = false;
        if (!Validation.hasText(permenantAddress)) ret = false;
        if (!Validation.hasText(permentvillage)) ret = false;
        if (!Validation.hasText(currentvillage)) ret = false;
        if (!Validation.hasText(parstate)) ret = false;
        if (!Validation.hasText(pardistrict)) ret = false;
        if (!Validation.hasText(curstate)) ret = false;
        if (!Validation.hasText(curdistrict)) ret = false;
        if (!Validation.hasText(curcity)) ret = false;
        if (!Validation.hasText(parcity)) ret = false;

        return ret;
    }
}

