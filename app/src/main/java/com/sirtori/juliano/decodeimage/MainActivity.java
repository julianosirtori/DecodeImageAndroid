package com.sirtori.juliano.decodeimage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Bitmap bitmap = Convert
                    .base64ToBitmap(getResources().getString(R.string.imagem), true);

            ((ImageView) findViewById(R.id.imagem)).setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
