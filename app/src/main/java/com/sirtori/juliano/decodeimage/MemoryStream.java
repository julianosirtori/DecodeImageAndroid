package com.sirtori.juliano.decodeimage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;

public class MemoryStream {
    private byte[] Value = new byte[0];
    private Integer Position = 0;

    public MemoryStream() {
    }

    public void setSize(Integer Size) {
        byte[] value = this.Value;
        this.Value = new byte[Size];
        Integer copySize = Size;
        if (value.length < Size) {
            copySize = value.length;
        }

        System.arraycopy(value, 0, this.Value, 0, copySize);
    }

    public void set(byte Value) {
        this.Value[this.Position] = Value;
        Integer var3 = this.Position;
        Integer var4 = this.Position = this.Position + 1;
    }

    public Integer read(byte[] buff, Integer count) {
        Integer i;
        for(i = 0; i < count; i = i + 1) {
            Integer position = this.getPosition();
            if (position >= this.size()) {
                break;
            }

            byte value = this.get(this.getPosition());
            this.setPosition(position + 1);
            buff[i] = value;
        }

        return i;
    }

    public byte get(Integer Index) {
        byte ByteValue = this.Value[Index];
        return ByteValue;
    }

    public void setPosition(Integer Position) {
        this.Position = Position;
    }

    public Integer getPosition() {
        return this.Position;
    }

    public Integer size() {
        Integer size = Array.getLength(this.Value);
        return size;
    }

    public void clear() {
        this.Value = new byte[0];
        this.Position = 0;
    }

    public void saveToFile(String FileName) throws FileNotFoundException, IOException {
        File file = new File(FileName);
        OutputStream out = new FileOutputStream(file);
        out.write(this.Value, 0, this.size());
        out.flush();
        out.close();
    }

    public void saveToStream(OutputStream out) throws IOException {
        out.write(this.Value, 0, this.size());
    }

    public byte[] getBytes() {
        return this.Value;
    }

    public void setBytes(byte[] value) {
        this.Value = value;
    }

    public void loadFromStream(InputStream in) throws IOException {
        this.clear();
        byte[] buffer = new byte[102400];
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        this.Value = out.toByteArray();
        this.Position = this.size();
        out.close();
    }

    public InputStream toInputStream() {
        InputStream in = new ByteArrayInputStream(this.getBytes());
        return in;
    }

    public void loadFromFile(String FileName) throws FileNotFoundException, IOException {
        if (this.size() > 0) {
            this.clear();
        }

        File file = new File(FileName);
        byte[] buffer = new byte[102400];
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        this.Value = out.toByteArray();
        this.Position = this.size();
        out.flush();
        out.close();
        in.close();
    }
}