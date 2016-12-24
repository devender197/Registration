package rawanasampark.registration;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
    Button member,shop,organization,biography;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        member = (Button) findViewById(R.id.button2);
        shop = (Button) findViewById(R.id.button3);
        organization = (Button) findViewById(R.id.button4);
        biography = (Button) findViewById(R.id.button9);

        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Registration.class);
                startActivity(in);

            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Pratisthan.class);
                startActivity(in);

            }
        });
        organization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Organization.class);
                startActivity(in);

            }
        });
        biography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Biography.class);
                startActivity(in);

            }
        });


    }
}
