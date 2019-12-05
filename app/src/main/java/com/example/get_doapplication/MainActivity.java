package com.example.get_doapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.get_doapplication.auth.RegisterMobile;

public class MainActivity extends AppCompatActivity {
    ImageView imgLogo;
    Animation animation ,translate;
    Thread splashTread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLogo=findViewById(R.id.ImgLogo);

        animation = AnimationUtils.loadAnimation(this,R.anim.alpha);
        animation.reset();

        translate = AnimationUtils.loadAnimation(this,R.anim.bounce);
        translate.reset();
        imgLogo.clearAnimation();
        imgLogo.startAnimation(translate);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(MainActivity.this,
                            RegisterMobile.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    MainActivity.this.finish();
                }

            }
        };
        splashTread.start();

    }
}
