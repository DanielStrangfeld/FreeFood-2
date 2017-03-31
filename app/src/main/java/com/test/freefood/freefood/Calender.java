package com.test.freefood.freefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Calender extends AppCompatActivity
{

    public Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        init(); //calls the init function
    }

    public void init()
    {
        button2= (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivities2 = new Intent(Calender.this, MapsActivity.class);

                startActivity(switchActivities2);
            }
        });
    }
}
