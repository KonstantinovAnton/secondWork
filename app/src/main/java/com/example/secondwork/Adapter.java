package com.example.secondwork;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

    Context mContext;
    List<Persons> personList;

    public Adapter(Context mContext, List<Persons> personList) {
        this.mContext = mContext;
        this.personList = personList;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext,R.layout.listlayouttemplate,null);
        TextView id = v.findViewById(R.id.tvID);
        TextView fname = v.findViewById(R.id.tvFname);
        TextView lname = v.findViewById(R.id.tvLname);
        ImageView Img = v.findViewById(R.id.imageView);

        Persons p = personList.get(i);

       id.setText(Integer.toString(p.getID()));
        fname.setText((p.getFname()));
        lname.setText(p.getLname());

        DecodeImg m = new DecodeImg(mContext);
        Img.setImageBitmap(m.getImg(p.getImage()));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdatePerson.class);
                intent.putExtra(Persons.class.getSimpleName(), p);
                mContext.startActivity(intent);
            }
        });
        return v;
    }
}






