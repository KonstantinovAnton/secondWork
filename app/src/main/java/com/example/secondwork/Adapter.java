package com.example.secondwork;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {


    Context mContext;
    List<Person> personList;

    public Adapter(Context mContext, List<Person> listProduct) {
        this.mContext = mContext;
        this.personList = listProduct;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int i) {
        return personList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return personList.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = View.inflate(mContext,R.layout.listlayouttemplate,null);

        TextView id = v.findViewById(R.id.ID);
        TextView fname = v.findViewById(R.id.tvFname);
        TextView lname = v.findViewById(R.id.tvLname);
        ImageView img = v.findViewById(R.id.imageView);

        Person p = personList.get(i);

        id.setText(p.getID());
        fname.setText(p.getFname());
        lname.setText(p.getLname());

        DecodeImg decodeImg = new DecodeImg(mContext);
        img.setImageBitmap(decodeImg.getImg(p.getImage()));
        v.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(mContext, updeteClass.class);
                // intent.putExtra(Person.class.getName(),p);
                // mContext.startActivity(intent);
            }
        });

        return v;
    }
}






