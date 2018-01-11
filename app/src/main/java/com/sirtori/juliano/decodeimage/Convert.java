package com.sirtori.juliano.decodeimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class Convert {

    public static Bitmap base64ToBitmap(String base64, boolean isCompress) throws IOException {
        Bitmap bitmap = null;
        byte[] decodedString = Base64.decode(base64, android.util.Base64.DEFAULT);
        if (isCompress) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decompress(decodedString));
            byteArrayInputStream.close();
            return BitmapFactory.decodeStream(byteArrayInputStream);
        }

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

    }

    private static byte[] decompress(byte[] compressed) throws IOException {
        final int BUFFER_SIZE = 32;

        ByteArrayInputStream is = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);

        MemoryStream memoryStream = new MemoryStream();
        memoryStream.loadFromStream(gis);
        memoryStream.setPosition(0);

        gis.close();
        is.close();
        return memoryStream.getBytes();
    }
}