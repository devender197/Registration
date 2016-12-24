package rawanasampark.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
 * Created by sandeepdeora on 9/4/2016.
 */
public class Registration4 extends ActionBarActivity {
    EditText regextradetail,password,confirmpassword,job_title,job_salary;
    Button save;
    String ID;
    private ProgressDialog pDialog;
    String column1,success;
    TextView job_stream;
    private String selectedFilePath;
    private ArrayAdapter<String> adapter2;
    private ArrayList<String> arrayList;
    String uploadinagestring=null;
    PopupWindow mpopup;
    Bitmap bitmap;
    Uri selectedFileUri;
    String strMyImagePath = null;
    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String contact;
    String usrID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration4);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getSupportActionBar().hide();
        regextradetail = (EditText) findViewById(R.id.regextradetail);
        password = (EditText) findViewById(R.id.regpassword);
        confirmpassword = (EditText) findViewById(R.id.regconfirmpassword);
        regextradetail.setMovementMethod(new ScrollingMovementMethod());
        job_salary= (EditText) findViewById(R.id.reg4jobsalary);
        job_title = (EditText) findViewById(R.id.Reg4jobtitle);
        job_stream = (TextView) findViewById(R.id.reg4jobstream);
        usrID = new Database().usercode(Registration4.this);
        arrayList = new ArrayList<String>();
        String[] array= {"Private Job","Business","Govt Job","Self Employement","Artist"};
        for(int i=0;i<array.length;i++){
            arrayList.add(i,array[i]);
        }
        adapter2 = new ArrayAdapter<String>(Registration4.this, R.layout.spinner, arrayList);
        job_stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
//                View contentView = mpopup.getContentView();
////                contentView.getBackground().setAlpha(150);
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
                list.setAdapter(adapter2);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        job_stream.setText(arrayList.get(position));
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            job_stream.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration4.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        save = (Button) findViewById(R.id.reg4savebutton);
        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS logintable(usercode VARCHAR, username VARCHAR,userpass VARCHAR,gcm VARCHAR);");
        Cursor c = db.rawQuery("SELECT username FROM logintable ", null);
        if(c.moveToFirst()){
            do{
                //assing values
                column1 = c.getString(0);


            }while(c.moveToNext());
        }
        Log.d("mobile",column1);
        c.close();
        db.close();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().matches(confirmpassword.getText().toString())&&password.getText().length()!=0&&confirmpassword.getText().length()!=0){
                    SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                    db.execSQL("UPDATE Registration SET sportdetail ='" + regextradetail.getText().toString() +"',  jobtitle ='" + job_title.getText().toString() +"',  jobstream ='" + job_stream.getText().toString() +"', jobsalary ='" + job_salary.getText().toString() +"'");
                    userLogin();
                }else{
                    Toast.makeText(Registration4.this, "Password enter is not same/entry is not complete", Toast.LENGTH_SHORT).show();
                    confirmpassword.setError("enter same password/complete this entry");
                }
            }
        });

    }
    private void userLogin() {

        JSONObject jsonObject=new JSONObject();
        pDialog = new ProgressDialog(Registration4.this);
        pDialog.setMessage("Uploading Data. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        Cursor c1 = db.rawQuery("SELECT * FROM Registration ", null);
        if(c1.moveToFirst()){
            do{
                //assing values

                try {
                    jsonObject.put("userID",    "");
                    jsonObject.put("Name",       c1.getString(0));
                    jsonObject.put("fatherName", c1.getString(1));
                    jsonObject.put("MotherName", c1.getString(2));
                    jsonObject.put("GrandFatherName", c1.getString(3));
                    jsonObject.put("spousename", c1.getString(4));
                    jsonObject.put("fatherGautra", c1.getString(5));
                    jsonObject.put("mothergautra", c1.getString(6));
                    jsonObject.put("Gender", c1.getString(7));
                    jsonObject.put("Dateofbirth", c1.getString(8));
                    jsonObject.put("ParentalVillage", c1.getString(9));
                    jsonObject.put("CurrentAddress", c1.getString(10));
                    jsonObject.put("currentState", c1.getString(11));
                    jsonObject.put("currentDistrict", c1.getString(12));
                    jsonObject.put("currentCity", c1.getString(13));
                    jsonObject.put("currentVillage", c1.getString(14));
                    jsonObject.put("ParmanentAddress", c1.getString(15));
                    jsonObject.put("perState", c1.getString(16));
                    jsonObject.put("perDistrict", c1.getString(17));
                    jsonObject.put("perCity", c1.getString(18));
                    jsonObject.put("perVillage", c1.getString(19));
                    jsonObject.put("Email", c1.getString(20));
                    jsonObject.put("ContactNo", c1.getString(21));
                    jsonObject.put("secyearpassing", c1.getString(22));
                    jsonObject.put("secpercentage", c1.getString(23));
                    jsonObject.put("senioryear", c1.getString(24));
                    jsonObject.put("seniorpercentage", c1.getString(25));
                    jsonObject.put("seniorStream", c1.getString(26));
                    jsonObject.put("Graduationyear", c1.getString(27));
                    jsonObject.put("GraduationPercentage", c1.getString(28));
                    jsonObject.put("GraduationStream", c1.getString(29));
                    jsonObject.put("PostGraduationyear", c1.getString(30));
                    jsonObject.put("postGraduationpercentage", c1.getString(31));
                    jsonObject.put("postGraduationStream", c1.getString(32));
                    jsonObject.put("postGraduationpercentage", c1.getString(33));
                    jsonObject.put("DoctorStream", c1.getString(34));
                    jsonObject.put("sportdetail", c1.getString(35));
                    jsonObject.put("JobTitle", c1.getString(39));
                    jsonObject.put("JobStream", c1.getString(40));
                    jsonObject.put("JobSalary", c1.getString(41));
                    jsonObject.put("UserID", usrID);
                    jsonObject.put("userImage", "RawnaSampark@"+ID+".png");
                    jsonObject.put("password", password.getText().toString());
                    uploadinagestring = c1.getString(42);
                    contact = c1.getString(21);

                    Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }



            }while(c1.moveToNext());
        }
        c1.close();
        db.close();


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.Regfromteam), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("error", "getBlockedUsers Response: " + response);
                try {
                    JSONArray jArray = response.getJSONArray("result");
                    JSONObject c= jArray.getJSONObject(0);
                   
                    success = c.getString("success");
                    ID = c.getString("id");
                    Log.d("Received String",success);
                    if(success.matches("true")){
                        userLogin1();
                        update();
                        clear();
                        Log.d("path of image",uploadinagestring);
                        Toast.makeText(Registration4.this, "Details Entered Successfully", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }else{
                        Toast.makeText(Registration4.this, "Details not Entered Successfully", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                } catch (JSONException e) {
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
                Toast.makeText(Registration4.this, "Invalid Password", Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }


        });
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
    }
    private void userLogin1() {

        JSONObject jsonObject=new JSONObject();
        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);

                try {
                    jsonObject.put("id",   ID);
                    jsonObject.put("MobileNo", contact);
                    jsonObject.put("password", password.getText().toString());
                    Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.messageapiregistration), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("error", "getBlockedUsers Response: " + response);
                try {
                    JSONArray jArray = response.getJSONArray("result");
                    JSONObject c= jArray.getJSONObject(0);

                    success = c.getString("success");
                    Log.d("Received String",success);
                    if(success.matches("1"))
                    {


                        Toast.makeText(Registration4.this, "message send", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Registration4.this, "message not send", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Registration4.this, "Invalid Password", Toast.LENGTH_LONG).show();
            }


        });
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
    }

    private void update() {

        JSONObject jsonObject=new JSONObject();


        try {
            jsonObject.put("id",   ID);
            Log.i("error 11111", "getBlockedUsers Request: " + jsonObject);

        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.updateuserimage), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("error", "getBlockedUsers Response: " + response);
                try {
                    JSONArray jArray = response.getJSONArray("result");
                    JSONObject c= jArray.getJSONObject(0);

                    success = c.getString("success");
                    Log.d("Received String",success);
                    if(success.matches("1"))
                    {

                        compress(Uri.fromFile(new File(uploadinagestring)));

                    }else{

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
                Toast.makeText(Registration4.this, "Invalid Password", Toast.LENGTH_LONG).show();
            }


        });
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(mJsonObjectRequest);
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
                URL url = new URL(getResources().getString(R.string.uploadImage));

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

                            Toast.makeText(Registration4.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Registration4.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {


                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(Registration4.this, "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "+ e.getMessage(), e);
            }

            return serverResponseCode;

        } // End else block
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

                            String s = "RawnaSampark@"+ID+".png";


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
    void clear(){

        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        db.execSQL("UPDATE Registration set reg1  ='" + 0 + "', reg2  ='" + 0 +"',reg3  ='" + 0 +"'");
        db.close();
        Intent intent = new Intent(Registration4.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}
