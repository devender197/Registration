package rawanasampark.registration;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by sandeepdeora on 1/12/2017.
 */
class SetTime implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private TextView editText;
    private Calendar myCalendar;
    Context ctx;
    String setString ;

    public SetTime(TextView editText, Context ctx,String str){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.myCalendar = Calendar.getInstance();
        this.ctx = ctx;
        setString= str;
        editText.setText(str);
    }

    String getTime(){
        return setString;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        this.editText.setText( hourOfDay + ":" + minute);
        setString = hourOfDay + ":" + minute;
    }


    @Override
    public void onClick(View v) {
        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        new TimePickerDialog(ctx, this, hour, minute, false).show();
    }
}