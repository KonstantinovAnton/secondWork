package com.example.secondwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class DecodeImg {
    Context context;

    public DecodeImg(Context context){
        this.context = context;
    }

    public Bitmap getImg(String img)
    {
        if(img!=null&& !img.equals("null")) {
            byte[] bytes = Base64.decode(img, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return BitmapFactory.decodeResource(DecodeImg.this.context.getResources(), R.drawable.e);
    }
}
