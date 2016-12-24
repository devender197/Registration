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
 * Created by sandeepdeora on 9/3/2016.
 */
public class Registration3 extends ActionBarActivity {
    TextView spinner10,spinner12,spinnergrad,spinnerpostgrad;
    EditText percentage10,percentage12,percentagegrad,percentagepostgrad,streamdoctor;
    TextView stream12,streamgrad,streampostgrad;
    int count;
    Button next;
    PopupWindow mpopup;
    String[] array;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    String column1;
    String array12[] = {"ARTS","Commerce","Science(Math)","Science(Biology)"};
    String arraygrad[] = { "Bachelor Hotel Management and Catering Technology(B.H.M.C.T)", "Bachelor Library Science(B.L.Sc)",
             "Bachelor of Applied Sciences(B.A.S)",
             "Bachelor of Architecture(B.Arch)",
             "Bachelor of Arts  ","Bachelor of Education(B.A. B.Ed)",
             "Bachelor of Arts  ","Bachelor of Law(B.A.LLB)",
             "Bachelor of Arts(B.A)",
             "Bachelor of Audiology and Speech Language Pathology(B.A.S.L.P)",
             "Bachelor of Ayurvedic Medicine and Surgery(B.A.M.S)",
             "Bachelor of Business Administration",  "Bachelor of Law(B.B.A LL.B)",
             "Bachelor of Business Administration(B.B.A)",
             "Bachelor of Business Management(B.B.M)",
             "Bachelor of Business Studies(B.B.S)",
             "Bachelor of Commerce(B.Com)",
             "Bachelor of Communication Journalism(B.C.J)",
             "Bachelor of Computer Applications(B.C.A)",
             "Bachelor of Computer Science(B.C.S)",
             "Bachelor of Dental Surgery(B.D.S)",
             "Bachelor of Design(B.Des)",
             "Bachelor of education in Artificial Intelligence(B.Ed AI)",
             "Bachelor of Education(B.Ed)",
             "Bachelor of Electronic Science(B.E.S)",
             "Bachelor of Elementary Education(B.EL.Ed)",
             "Bachelor of Engineering(B.E)",
             "Bachelor of Fashion Technology(B.F.Tech)",
             "Bachelor of Financial Investment and Analysis(B.F.I.A)",
             "Bachelor of Fine Arts(B.F.A)",
             "Bachelor of Fishery Sciences(B.F.S)",
             "Bachelor of General Law(B.G.L)",
             "Bachelor of Homeopathic Medicine & Surgery(B.H.M.S)",
             "Bachelor of Hospitality and Tourism Management(B.H.T.M)",
             "Bachelor of Hotel Management(B.H.M)",
             "Bachelor of Information Systems Management(B.I.S.M)",
             "Bachelor of Labour Management(B.L.M)",
             "Bachelor of Law(LL.B)",
             "Bachelor of Laws(B.L)",
             "Bachelor of Library and Information Science(B.L.I.S)",
             "Bachelor of Literature(B.Lit)",
             "Bachelor of Medical Laboratory Technology(B.M.L.T)",
             "Bachelor of Medical Record Science(B.M.R.Sc)",
             "Bachelor of Medical Technology(B.M.T)",
             "Bachelor of Medicine ", "Bachelor of Surgery(M.B.B.S)",
             "Bachelor of Mental Retardation(B.M.R)",
             "Bachelor of Naturopathy and Yogic Sciences(B.N.Y.Sc)",
             "Bachelor of Occupational Therapy(B.O.T)",
             "Bachelor of Occupational Therapy(B.O.Th)",
             "Bachelor of Optometry and Vision Science(B.Optom)",
             "Bachelor of Pharmacy(B.Pharma)",
             "Bachelor of Physical Education(B.P.E)",
             "Bachelor Of Physical Education(B.P.Ed)",
             "Bachelor of Physiotherapy(B.P.T)",
             "Bachelor of Public Relations(B.P.R)",
             "Bachelor of Science",  "Bachelor of Education(B.Sc.B.Ed)",
             "Bachelor of Science Education(B.S.E)",
             "Bachelor of Science in Education(B.Sc.Ed)",
             "Bachelor of Science(B.S)",
             "Bachelor of Siddha Medical Sciences(B.S.M.S)",
             "Bachelor Of Social Work(B.S.W)",
             "Bachelor of Socio Legal Sciences",  "Bachelor of Laws(B.S.L.LL.B)",
             "Bachelor of Speech Language & Audiology(B.S.L.A)",
             "Bachelor of Tourism Administration(B.T.A)",
             "Bachelor of Unani Medicine & Surgery(B.U.M.S)",
             "Bachelor of Unani Medicine & Surgery(Kamil e Tob o Jarahat)",
             "Bachelor of Veterinary Science(B.V.Sc)",
             "Bachelors of Technology(B.Tech)",
            "Basic Training Certificate(B.T.C)",
            "Behavioral Healthcare Education(B.H.Ed)",
            "Under Graduate Basic Training(U.G.B.T)",
            "Under Graduate Teacher Training(U.G.T.T)",
            "Under Graduate Training(U.G.T)}"};

    String arraypostgrad[] = {"Doctor of Medicine Unani(Mahir e Tibb)",
            "Doctor of Medicine(M.D)",
            "Executive Fellow Program In Management(E.F.P.M)",
            "Executive Management Programme(E.M.P)",
            "Fellow Programme in MAnagement(F.P.M)",
            "MA / MSc MAthematics",
            "MA Adult & Continuing Education",
            "MA Ancient History & Archeology",
            "MA Anthropology",
            "MA Applied Economics",
            "MA Dance",
            "MA Economics",
            "MA English",
            "MA Hindi",
            "MA History",
            "MA Human Rights & Duties",
            "MA Music",
            "MA Philosophy",
            "MA Political Science",
            "MA Psychology",
            "MA Public Administration",
            "MA Sanskrit",
            "MA Social Work",
            "MA Sociology",
           " Management Development Programme(M.D.P)",
            "Master of Architecture(M.Arch)",
            "Master of Arts in Management(M.A.M)",
            "Master of Arts in Personal Management(M.A.P.M)",
            "Master of Arts in Theatre & Television(M.A.T.T)",
            "Master of Arts(M.A)",
            "Master of Audiology & Speech Language Pathology(M.A.S.L.P)",
            "Master of Business Administration(M.B.A)",
            "Master of Business Economics(M.B.E)",
            "Master of Business Laws(M.B.L)",
            "Master of Business Management(M.B.M)",
            "Master of Business Studies(M.B.S)",
            "Master of Chirurgical(M.Ch)",
            "Master of Commerce(M.Com)",
            "Master of Communication & Journalism(M.C.J)",
            "Master of Comparative Laws(M.C.L)",
            "Master of Computer Applications(M.C.A)",
            "Master of Computer Management(M.C.M)",
            "Master of Corporate Secretaryship(M.C.S)",
            "Master of Dental Surgery(M.D.S)",
            "Master of education in Artificial Intelligence(M.Ed AI)",
            "Master of Education(M.Ed)",
            "Master of Engineering(M.E)",
            "Master of Film Management(M.F.M)",
            "Master of Finance & Control(M.F.C)",
            "Master of Financial Services(M.F.S)",
            "Master of Fine Arts(M.F.A)",
            "Master of Fishery Sciences(M.F.Sc)",
            "Master of Foreign Trade(M.F.T)",
            "Master Of Health Science(M.H.Sc)",
            "Master of Hospital Administration(M.H.A)",
            "Master of Hospitality & Hotel Management(M.H.H.M)",
            "Master of Hospitality Management(M.H.M)",
            "Master of Human Resource Management(M.H.R.M)",
            "Master of Industrial Relation and Personal Management(MIR and PM)",
            "Master of Information Management(M.I.M)",
            "Master of International Business(M.I.B)",
            "Master of Journalism(M.J)",
            "Master of Labour Management(M.L.M)",
            "Master of Law(LL.M)",
            "Master of Laws(M.L)",
            "Master of Library and Information Science(M.L.I.Sc)",
            "Master of Library Science(M.L.Sc)",
            "Master of Management Program(M.M.P)",
            "Master of Management Studies(M.M.S)",
            "Master of Marketing Management(M.M.M)",
            "Master of Occupational Theraphy(M.O.T)",
            "Master of Performance Management(M.P.M)",
            "Master of Performing Arts(M.P.A)",
            "Master of Personal Management and Industrial Relation(MPM and IR)",
            "Master of Personnel Management(MPM)",
            "Master of Pharmacy(M.Pharma)",
            "Master of Philosophy(M.Phil)",
            "Master Of Physical Education(M.P.Ed)",
            "Master of Physiotheraphy(M.P.T)",
            "Master of Psychiatric Epidemiology(M.P.E)",
            "Master of Public Health(M.P.H)",
            "Master of Science(BOTANY)",
            "Master of Science(M.S)",
            "Master of Science(M.Sc)",
            "Master of Social Dynamics(M.S.D)",
            "Master of Social Work(M.S.W)",
            "Master of Technology(M.Tech)",
            "Master of Tourism Administrations(M.T.A)",
            "Master of Tourism Management(M.T.M)",
            "Master of Veterinary Science(M.V.Sc)",
            "Masters of Hospitality and Tourism Management(M.H.T.M)",
            "Masters of Public Systems Management(M.P.S.M)",
            "Masters Programme in International Business(M.P.I.B)",
            "Masters Programme In Sports Physiotheraphy(M.S.P.T)",
            "MSc Agricultural Biotechnology",
            "MSc Analysis of Foods, Drugs and Water",
            "MSc Analytical Chemistry",
            "MSc Anthropology",
            "MSc Applied Chemistry",
            "MSc Applied Mathematics",
            "MSc Bio-Inorganic Chemistry",
            "MSc Biochemistry",
            "MSc Biotechnology",
            "MSc Botany",
            "MSc Coastal Aquaculture & Marine Biotechnology",
            "MSc Computer Science",
            "MSc Computer Science and Statistics",
            "MSc Electronics & Instrumentation",
            "MSc Environmental Chemistry",
            "MSc Environmental Sciences",
            "MSc Fishery Science",
            "MSc Food, Nutrition Drugs and Water",
            "MSc Foods, Nutrition & Dietetics",
            "MSc Geography - B.A(Stream)",
            "MSc Geography - B.Sc.(Stream)",
            "MSc Geology",
            "MSc Geophysics",
            "MSc Horticulture & Landscape Management",
            "MSc Human Genetics",
            "MSc Hydrology",
            "MSc Inorganic Chemistry",
            "MSc Marine Biology and Fisheries",
            "MSc Marine Biotechnology",
            "MSc Marine Chemistry",
            "MSc Marine Geology",
            "MSc Marine Geophysics",
            "MSc Meteorology",
            "MSc Microbiology",
            "MSc Nuclear Chemistry",
            "MSc Nuclear Physics",
            "MSc Organic Chemistry",
            "MSc Physical Chemistry",
            "MSc Physical Oceanography",
            "MSc Physics",
            "MSc Space Physics",
            "MSc Statistics",
            "MSc VLSI Design, DSP & ESD",
            "MSc Zoology"};
    private ArrayAdapter<String> adaptergrad;
    private ArrayList<String> arrayListgrad;
    private ArrayAdapter<String> adapter12;
    private ArrayList<String> arrayList12;
    private ArrayAdapter<String> adapterpost;
    private ArrayList<String> arrayListpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration3);
        getSupportActionBar().hide();
        SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Registration(Name VARCHAR, fatherName VARCHAR,MotherName VARCHAR,GrandFatherName VARCHAR,GrandMotherName VARCHAR,MatrinialGrandFatherName VARCHAR,Gender VARCHAR,Dateofbirth VARCHAR,ParentalVillage VARCHAR, CurrentAddress VARCHAR,currentState VARCHAR, currentDistrict VARCHAR, currentCity VARCHAR, currentVillage VARCHAR,ParmanentAddress VARCHAR, perState VARCHAR, perDistrict VARCHAR, perCity VARCHAR,perVillage VARCHAR,secyearpassing VARCHAR, secpercentage VARCHAR,senioryear VARCHAR,seniorpercentage VARCHAR, seniorStream VARCHAR, Graduationyear VARCHAR,GraduationPercentage VARCHAR,GraduationStream VARCHAR,PostGraduationyear VARCHAR,postGraduationpercentage VARCHAR, postGraduationStream VARCHAR, DoctorStream VARCHAR,reg1 VARCHAR, reg2 VARCHAR, reg3 VARCHAR);");
        Cursor c = db.rawQuery("SELECT reg3 FROM Registration ", null);
        if(c.moveToFirst()){
            do{
                //assing values
                column1 = c.getString(0);


            }while(c.moveToNext());
        }
        c.close();
        db.close();
        if(column1.matches("1")){
            Intent intent = new Intent(Registration3.this,Registration4.class);
            startActivity(intent);
            finish();
            Log.d("moving","forward2");
        }
        //Senior Secondary
        arrayList12 = new ArrayList<String>();
        for(int i=0;i<array12.length;i++){
            arrayList12.add(i,array12[i]);
        }
        adapter12 = new ArrayAdapter<String>(Registration3.this, R.layout.spinner, arrayList12);



        //graduation array list
        arrayListgrad = new ArrayList<String>();
        for(int i=0;i<arraygrad.length;i++){
            arrayListgrad.add(i,arraygrad[i]);
        }
        adaptergrad = new ArrayAdapter<String>(Registration3.this, R.layout.spinner, arrayListgrad);

        //postgraduation array list

        arrayListpost = new ArrayList<String>();
        for(int i=0;i<arraypostgrad.length;i++){
            arrayListpost.add(i,arraypostgrad[i]);
        }
        adapterpost = new ArrayAdapter<String>(Registration3.this, R.layout.spinner, arrayListpost);
        percentage10        = (EditText) findViewById(R.id.regsecondarypercentage);
        percentage12        = (EditText) findViewById(R.id.regseniorsecondarypercnetage);
        percentagegrad      = (EditText) findViewById(R.id.reggraduationpercentage);
        percentagepostgrad  = (EditText) findViewById(R.id.regpostgradpercentage);
        stream12            = (TextView) findViewById(R.id.regseniorsecondarystream);
        streamgrad          = (TextView) findViewById(R.id.reggraduationstream);
        streampostgrad      = (TextView) findViewById(R.id.regpostgraduation);
        streamdoctor        = (EditText) findViewById(R.id.regDoctoraitor);
        spinner10           = (TextView) findViewById(R.id.spinner10);
        spinner12           = (TextView) findViewById(R.id.spinner12);
        spinnergrad         = (TextView) findViewById(R.id.spinnergrad);
        spinnerpostgrad     = (TextView) findViewById(R.id.spinnerpostgrad);
        next = (Button) findViewById(R.id.reg3button);
        arrayList = new ArrayList<String>();
        for(int i=2016,j=0;i>1921;i--,j++){

            arrayList.add(j, Integer.toString(i));
        }
        adapter = new ArrayAdapter<String>(Registration3.this, R.layout.spinner, arrayList);
        stream12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
          //      View contentView = mpopup.getContentView();
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
                list.setAdapter(adapter12);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        stream12.setText(array12[position]);
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            stream12.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration3.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        streamgrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
                View contentView = mpopup.getContentView();
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
                list.setAdapter(adaptergrad);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        streamgrad.setText(arraygrad[position]);
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            streamgrad.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration3.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        streampostgrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
            //    View contentView = mpopup.getContentView();
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
                list.setAdapter(adapterpost);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        streampostgrad.setText(arraypostgrad[position]);
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            streampostgrad.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration3.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        spinner10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
              //  View contentView = mpopup.getContentView();
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
                list.setAdapter(adapter);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinner10.setText(arrayList.get(position));
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            spinner10.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration3.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        spinner12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
             //   View contentView = mpopup.getContentView();
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
                list.setAdapter(adapter);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinner12.setText(arrayList.get(position));
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            spinner12.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration3.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        spinnergrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
                View contentView = mpopup.getContentView();
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
                list.setAdapter(adapter);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinnergrad.setText(arrayList.get(position));
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            spinnergrad.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration3.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        spinnerpostgrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.popuplist, null);
                mpopup = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                mpopup.setBackgroundDrawable(new BitmapDrawable());
                View contentView = mpopup.getContentView();
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
                list.setAdapter(adapter);
                final EditText edit = (EditText) layout.findViewById(R.id.editText12);
                Button submit = (Button) layout.findViewById(R.id.button10);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        spinnerpostgrad.setText(arraypostgrad[position]);
                        mpopup.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().length()!=0) {
                            spinnerpostgrad.setText(edit.getText().toString());
                            mpopup.dismiss();
                        }else{
                            Toast.makeText(Registration3.this, "either select from list or write name in text box", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validation()) {
                    SQLiteDatabase db = openOrCreateDatabase("MyDb", MODE_PRIVATE, null);
                    db.execSQL("UPDATE Registration set secyearpassing ='" + spinner10.getText().toString() + "', secpercentage ='" + percentage10.getText().toString() + "',senioryear ='" + spinner12.getText().toString() + "', seniorpercentage ='" + percentage12.getText().toString() + "', seniorStream ='" + stream12.getText().toString() + "', Graduationyear ='" + spinnergrad.getText().toString() + "',GraduationPercentage  ='" + percentagegrad.getText().toString() + "', GraduationStream  ='" + streamgrad.getText().toString() + "', PostGraduationyear  ='" + spinnerpostgrad.getText().toString() + "', postGraduationpercentage  ='" + percentagepostgrad.getText().toString() + "',postGraduationStream  ='" + streampostgrad.getText().toString() + "',DoctorStream  ='" + streamdoctor.getText().toString() + "',reg3  ='" + 1 + "'");
                    Intent intent = new Intent(Registration3.this, Registration4.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    Boolean validation(){
        Boolean bool;
        if(percentage12.getText().toString().length()>0 && stream12.getText().toString().length()==0){
            stream12.setError("enter Stream");
            return false;
        }
        if(percentage12.getText().toString().length()==0 && stream12.getText().toString().length()>0){
            percentage12.setError("enter percentage");
            return false;
        }
        if(percentagegrad.getText().toString().length()>0 && streamgrad.getText().toString().length()==0){
            streamgrad.setError("enter Stream");
            return false;
        }
        if(percentagegrad.getText().toString().length()==0 && streamgrad.getText().toString().length()>0){
            percentagegrad.setError("enter percentage");
            return false;
        }
        if(percentagepostgrad.getText().toString().length()>0 && streampostgrad.getText().toString().length()==0){
            streampostgrad.setError("enter Stream");
            return false;
        }
        if(percentagepostgrad.getText().toString().length()==0 && streampostgrad.getText().toString().length()>0){
            percentagepostgrad.setError("enter percentage");
            return false;
        }
        if(percentage10.getText().toString().length()!=0 && (Integer.parseInt(percentage10.getText().toString())<0 || Integer.parseInt(percentage10.getText().toString())>100)){
            percentage10.setError("enter value between 0 to 100");
            return false;
        }
        if(percentagepostgrad.getText().toString().length()!=0 && (Integer.parseInt(percentagepostgrad.getText().toString())<0 || Integer.parseInt(percentagepostgrad.getText().toString())>100)){
            percentagepostgrad.setError("enter value between 0 to 100");
            return false;
        }
        if(percentagegrad.getText().toString().length()!=0 && (Integer.parseInt(percentagegrad.getText().toString())<0 || Integer.parseInt(percentagegrad.getText().toString())>100)){
            percentagegrad.setError("enter value between 0 to 100");
            return false;
        }
        if(percentage12.getText().toString().length()!=0 && (Integer.parseInt(percentage12.getText().toString())<0 || Integer.parseInt(percentage12.getText().toString())>100)){
            percentage12.setError("enter value between 0 to 100");
            return false;
        }
        return true;

    }

}
