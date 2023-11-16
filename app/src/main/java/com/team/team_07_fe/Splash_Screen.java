package com.team.team_07_fe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        ImageView imageView = findViewById(R.id.splash_screen);

        Glide.with(this)
                .load(R.drawable.gifsplash)
                .into(new ImageViewTarget<GifDrawable>(imageView) {
                    @Override
                    protected void setResource(GifDrawable resource) {
                        imageView.setImageDrawable(resource);
                        resource.start();
                    }
                }.getView());

        // Handler code to navigate to the main screen after a certain time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}