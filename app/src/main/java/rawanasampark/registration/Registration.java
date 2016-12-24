package rawanasampark.registration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends ActionBarActivity {
    EditText name;
    EditText fathername;
    EditText mothername;
    EditText grandfathername;
    EditText spousename;
    EditText FatherGautraname;
    EditText MotherGautraname;
    CircleImageView upload;
    static EditText dateofbirth;
    RadioButton Gendermale , Genderfemale;
    ImageView calender;
    static String datevalue;
    Button b1;
    int row;
    Bitmap bitmap;
    Uri selectedFileUri;
    String strMyImagePath = null;
    TextView messageText;
    Button uploadButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    private int PICK_IMAGE_REQUEST = 1;
    String Gender;
   // String upLoadServerUri = getResources().getString(R.string.upload);
    String uploadFilePath ;
    private String selectedFilePath;
    String column1;
    final String uploadFileName = "profilepic.jpg";
    String regID ;
    String usrID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        getSupportActionBar().hide();

        //show_popup();
        name = (EditText) findViewById(R.id.parental_village);
        fathername = (EditText) findViewById(R.id.Current_address);
        mothername = (EditText) findViewById(R.id.permenant_address);
        grandfathername = (EditText) findViewById(R.id.email);
        spousename = (EditText) findViewById(R.id.reg1spousename);
        FatherGautraname = (EditText) findViewById(R.id.RegFGautraname);
        MotherGautraname = (EditText) findViewById(R.id.RegGautraName);
        Genderfemale = (RadioButton) findViewById(R.id.RegFemale);
        Gendermale = (RadioButton) findViewById(R.id.RegMale);
        dateofbirth = (EditText) findViewById(R.id.RegDate);
        calender = (ImageView) findViewById(R.id.imageView2);
        b1 = (Button) findViewById(R.id.RegPersonalData);
        upload = (CircleImageView) findViewById(R.id.ad_Logo);
        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Registration(Name VARCHAR, fatherName VARCHAR,MotherName VARCHAR,GrandFatherName VARCHAR,spousename VARCHAR,fathergautraname VARCHAR,mothergautraname VARCHAR,Gender VARCHAR,Dateofbirth VARCHAR,ParentalVillage VARCHAR, CurrentAddress VARCHAR,currentState VARCHAR, currentDistrict VARCHAR, currentCity VARCHAR, currentVillage VARCHAR,ParmanentAddress VARCHAR, perState VARCHAR, perDistrict VARCHAR, perCity VARCHAR,perVillage VARCHAR,Email VARCHAR ,ContactNo VARCHAR, secyearpassing VARCHAR, secpercentage VARCHAR,senioryear VARCHAR,seniorpercentage VARCHAR, seniorStream VARCHAR, Graduationyear VARCHAR,GraduationPercentage VARCHAR,GraduationStream VARCHAR,PostGraduationyear VARCHAR,postGraduationpercentage VARCHAR, postGraduationStream VARCHAR, DoctorStream VARCHAR,reg1 VARCHAR, reg2 VARCHAR, reg3 VARCHAR, reg4 VARCHAR, sportdetail VARCHAR, jobtitle VARCHAR, jobstream VARCHAR, jobsalary VARCHAR, image VARCHAR , showno VARCHAR,RID varchar);");
        Cursor c = db.rawQuery("SELECT reg1 FROM Registration ", null);
        row = c.getCount();
        if(c.moveToFirst()){
            do{
                //assing values
                column1 = c.getString(0);


            }while(c.moveToNext());
        }

        if(c.getCount()!=0) {
            if (column1.matches("1")) {
                Intent intent = new Intent(Registration.this, Registration2.class);
                startActivity(intent);
                finish();
                Log.d("moving", "forward");
            }
        }
        c.close();
        db.close();
        Gendermale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genderfemale.setChecked(false);
                Gender = "M";
            }
        });
        Genderfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gendermale.setChecked(false);
                Gender = "F";
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);

                if(checkValidation() && (selectedFilePath!=null)) {
                    db.execSQL("INSERT INTO Registration VALUES('" + name.getText().toString() + "','" + fathername.getText().toString() + "','" + mothername.getText().toString() + "','" + grandfathername.getText().toString() + "','" + spousename.getText().toString() + "','" + FatherGautraname.getText().toString() + "','"+ MotherGautraname.getText().toString() + "','" + Gender +"','" +dateofbirth.getText().toString() + "','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null  +"','" + null+"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + null +"','" + 1+"','" + null+"','" + null+"','" +null+"','" + null+"','" + null+"','" +null+"','"+null+"','" + selectedFilePath+"','1','"+row+"');");
                    Intent intent = new Intent(Registration.this,Registration2.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }

        });
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month+1, day);
        }



        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            datevalue = dayOfMonth +"/"+(monthOfYear+1)+"/"+year;
            settingdate(datevalue);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    static void settingdate(String date){
        dateofbirth.setText(date);


    }
    private boolean checkValidation() {

        boolean ret = true;
        if (!Validation.hasText(name)) ret = false;
        if (!Validation.hasText(fathername)) ret = false;
        if (!Validation.hasText(mothername)) ret = false;
        if (!Validation.hasText(grandfathername)) ret = false;

        if (!Validation.hasText(FatherGautraname)) ret = false;
        if (!Validation.hasText(dateofbirth)) ret = false;
        return ret;
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

    void show_popup(){

    }



}
