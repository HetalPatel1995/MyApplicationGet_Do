package com.example.get_doapplication.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.get_doapplication.Activities.HomePage;
import com.example.get_doapplication.Activities.ProfileActivity;
import com.example.get_doapplication.R;

public class SetPinGenerate extends AppCompatActivity {
Button btnSetPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin_generate);

        btnSetPin=findViewById(R.id.SetPinBtn);

        btnSetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SetPinGenerate.this, HomePage.class);
                startActivity(i);
            }
        });
    }
}
