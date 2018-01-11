package com.sirtori.juliano.decodeimage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagem = (ImageView) findViewById(R.id.imagem);

        try {

            //descompactada PNG
            byte[] stringBase64 = Base64.decode(getResources().getString(R.string.imagem), Base64.DEFAULT);
            FileOutputStream decompress = decompress(stringBase64);


//            //transformado PNG descompactado em BASE64
//            String stringNpgDescompactado = Base64.encodeToString(decompress.toByteArray(), Base64.DEFAULT);
//
//            //transforma PNG em BITMAP
//            byte[] bitmapImagem = Base64.decode(stringNpgDescompactado, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapImagem, 0, bitmapImagem.length);
//
//            //insere no imageview
//            imagem.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FileOutputStream decompress(byte[] compressed) throws IOException {

        String filename = "arquivoCompactado";
        FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
        outputStream.write(compressed);
        outputStream.close();

        FileInputStream inputStream = openFileInput(filename);
        GZIPInputStream gis = new GZIPInputStream(inputStream);
        InputStreamReader reader = new InputStreamReader(gis);

        filename = "imagem";
        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

        BufferedReader in = new BufferedReader(reader);

        String readed;
        while ((readed = in.readLine()) != null) {
            outputStream.write(readed.getBytes());
        }

        outputStream.close();
        gis.close();
        return outputStream;
    }
}
