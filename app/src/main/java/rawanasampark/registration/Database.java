package rawanasampark.registration;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by sandeepdeora on 9/16/2016.
 */
public class Database {
    String usercode(ActionBarActivity activity){
        SQLiteDatabase db = activity.openOrCreateDatabase("MyDb", activity.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS logintable(usercode VARCHAR, username VARCHAR,userpass VARCHAR);");
        Cursor c = db.rawQuery("SELECT usercode FROM logintable ", null);
        c.moveToFirst();
        String username = null;
        Log.d("row count",c.getCount()+"");
        if(c.moveToFirst()){
            do{
                username = c.getString(0);


            }while(c.moveToNext());
        }

        c.close();
        db.close();
        return username;
    }

}
