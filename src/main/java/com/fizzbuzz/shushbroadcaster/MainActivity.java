package com.fizzbuzz.shushbroadcaster;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.androidintents.PRE_RINGER_MODE_CHANGE");
                intent.putExtra("com.androidintents.EXTRA_SENDER", "com.fizzbuzz.shushbroadcaster");
                getApplicationContext().sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "broadcast sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
