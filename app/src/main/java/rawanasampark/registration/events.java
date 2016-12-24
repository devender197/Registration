package rawanasampark.registration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Scroller;
import android.widget.Spinner;
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sandeepdeora on 11/3/2016.
 */
public class events extends ActionBarActivity{
    EditText name, description ,address,contact,email,latslong;
    Spinner type;
    String success;
    ImageView upload;
    int count;
    ProgressDialog dialog = null;
    TextView parstate,pardistrict;
    Button latlong,submit;
    Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    Uri selectedFileUri;
    String strMyImagePath = null;
    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    String statecode[],districtcode[];
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ListView list2;
    private ArrayAdapter<String> adapter2;
    private ArrayList<String> arrayList2;
    private ProgressDialog pDialog;
    private ArrayAdapter<String> adapter4;
    private ArrayList<String> arrayList4;
    private ArrayAdapter<String> adapter3;
    private ArrayList<String> arrayList3;
    PopupWindow mpopup;
    JSONArray arr;
    private String selectedFilePath;
    String[] id,code;
    String ID,usrID;
    String stringLongitude,stringlattitude;
    double latitude ,longitude ;
    int statecount,districtcount;
//    GPSTracker gpsTracker = new GPSTracker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.pratisthan);
        name= (EditText) findViewById(R.id.P_shopname);
        description= (EditText) findViewById(R.id.P_shopdescription);
        address= (EditText) findViewById(R.id.P_address);
        contact= (EditText) findViewById(R.id.P_contactno);
        email= (EditText) findViewById(R.id.P_email);
        latslong= (EditText) findViewById(R.id.P_latslongs);
        upload = (ImageView) findViewById(R.id.P_image);
        usrID = new Database().usercode(events.this);
        description.setScroller(new Scroller(getApplicationContext()));
        description.setVerticalScrollBarEnabled(true);
        description.setMovementMethod(new ScrollingMovementMethod());
        address.setScroller(new Scroller(getApplicationContext()));
        address.setVerticalScrollBarEnabled(true);
        address.setMovementMethod(new ScrollingMovementMethod());

        //spinner
        type = (Spinner) findViewById(R.id.P_shoptype);
        parstate = (TextView) findViewById(R.id.P_state);
        pardistrict = (TextView) findViewById(R.id.P_district);
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
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );




        adapter = new ArrayAdapter<String>(events.this, R.layout.spinner, arrayList);
        adapter4 = new ArrayAdapter<String>(events.this, R.layout.spinner, arrayList);
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
                        districtcount  = position;
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
                            Toast.makeText(events.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
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
                        statecount = position;
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
                        adapter2 = new ArrayAdapter<String>(events.this, R.layout.spinner, arrayList2);
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
                            Toast.makeText(events.this, "Either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //Button
        latlong = (Button) findViewById(R.id.button8);
        submit  = (Button) findViewById(R.id.button6);
        latlong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationmanager gps  = new locationmanager(events.this);
                if(gps.canGetLocation()){ // gps enabled} // return boolean true/false
                    latitude = gps.getLatitude(); // returns latitude
                    longitude = gps.getLongitude(); // returns longitude
                    stringlattitude = latitude+"";
                    stringLongitude = longitude+"";
                    latslong.setText(latitude+","+longitude);
                }else{
                    gps.showSettingsAlert();

                }

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(name.getText().toString().length()!=0 && description.getText().toString().length()!=0 && address.getText().toString().length()!=0 && contact.getText().toString().length()!=0 && email.getText().toString().length()!=0 && parstate.getText().toString().length()!=0 && pardistrict.getText().toString().length()!=0 && type.getSelectedItem().toString().matches("Select")==false )
                {
                    update();
                }else{
                    Toast.makeText(events.this, "Complete All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        userLogin();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        });

    }
    private void userLogin() {

        JSONObject jsonObject=new JSONObject();
        pDialog = new ProgressDialog(events.this);
        pDialog.setMessage("Loading  Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.eventsend), jsonObject, new Response.Listener<JSONObject>() {
            int flag=0;
            @Override
            public void onResponse(JSONObject response) {
                Log.i("error", "getBlockedUsers Response: " + response);
                try{
                    arr = response.getJSONArray("result");
                    if(arr!=null)
                    {
                        JSONObject   c2 = arr.getJSONObject(0);
                        JSONArray arr1 = c2.getJSONArray("value");
                        arrayList3 = new ArrayList<>();
                        id = new String[arr1.length()];
                        code = new String[arr1.length()+1];
                        arrayList3.add(0,"Select");
                        code[0]="0";
                        for(int j=0,i=1;j<arr1.length();j++,i++)
                        {
                            JSONObject   c3 = arr1.getJSONObject(j);
                            id[j] = c3.getString("PraName");
                            code[i] = c3.getString("PrtId");
                            arrayList3.add(i,id[j]);
                            flag = 13;

                        }

                    }
                    if(flag==13)
                    {
                        pDialog.dismiss();
                        adapter3= new ArrayAdapter<String>(events.this, R.layout.spinner, arrayList3);
                        type.setAdapter(adapter3);
                        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                count = position;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }else{
                        pDialog.dismiss();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("error",error.toString());
                pDialog.dismiss();
                Toast.makeText(events.this, "Invalid Password", Toast.LENGTH_LONG).show();
            }


        });
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(events.this).addToRequestQueue(mJsonObjectRequest);
    }
    private void update()
    {

        JSONObject jsonObject=new JSONObject();


        try {

            jsonObject.put("name",          name.getText().toString());
            jsonObject.put("description",   description.getText().toString());
            jsonObject.put("address",       address.getText().toString());
            jsonObject.put("contact",       contact.getText().toString());
            jsonObject.put("email",         email.getText().toString());
            jsonObject.put("lats",          stringlattitude);
            jsonObject.put("long",          stringLongitude);
            jsonObject.put("type",          code[count]);
            jsonObject.put("parstate",      statecode[statecount]);
            jsonObject.put("pardistrict",   districtcode[districtcount]);
            jsonObject.put("UserID", usrID);
            Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);

        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.prat_send), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("error", "getBlockedUsers Response: " + response);
                try {
                    JSONArray jArray = response.getJSONArray("result");
                    JSONObject c = jArray.getJSONObject(0);

                    success = c.getString("success");
                    ID = c.getString("id");
                    Log.d("Received String",success);
                    if(success.matches("1"))
                    {
                        Toast.makeText(events.this, "Details Enter successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(events.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        compress(selectedFileUri);

                    }else{
                        Toast.makeText(events.this, "Details not Enter successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("error",error.toString());
                Toast.makeText(events.this, "Invalid Password", Toast.LENGTH_LONG).show();
            }


        });
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
    }
    void compress(Uri uri1){
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri1);
            // if(bitmap.getByteCount()<=500) {
            new Thread(new Runnable() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String extr = Environment.getExternalStorageDirectory().toString();
                            File mFolder = new File(extr + File.separator+ getResources().getString(R.string.app_name));
                            if (!mFolder.exists()) {
                                mFolder.mkdir();
                            }

                            String s = ID+".png";


                            File f = new File(mFolder.getAbsolutePath(), s);
                            try {
                                f.createNewFile();
                            } catch (IOException e) {
                                Log.d("file","problem");
                            }
                            strMyImagePath = f.getAbsolutePath();
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(f);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, fos);
                                fos.flush();
                                fos.close();

                                Log.d("string path",strMyImagePath);

                                selectedFilePath=strMyImagePath;
                                uploadFile(selectedFilePath);

                            } catch (FileNotFoundException e) {

                                e.printStackTrace();
                            } catch (Exception e) {

                                e.printStackTrace();
                            }

                            // bitmap.recycle();


                            if (strMyImagePath == null) {
                                Log.d("string path",strMyImagePath);
                            }

                        }

                    });


                }
            }).start();
            // }else{
            //   Toast.makeText(getActivity(), "Image Size should be less than 500 bytes"+bitmap.getByteCount(), Toast.LENGTH_SHORT).show();
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist 33:"+sourceFile.getAbsolutePath());

            runOnUiThread(new Runnable() {
                public void run() {
//                    messageText.setText("Source File not exist :"
//                            +uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(getResources().getString(R.string.eventimage));

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename='"+ fileName + "'" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode+ serverResponseMessage + ": " + serverResponseMessage);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(events.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //

                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        // messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(events.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {


                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(events.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "+ e.getMessage(), e);
            }

            return serverResponseCode;

        } // End else block
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            final Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                selectedFileUri = data.getData();
                selectedFilePath = filepath.getPath(getApplicationContext(),selectedFileUri);
                upload.setImageBitmap(bitmap);
                Log.d("Selected File Path:" , selectedFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

    }

}
