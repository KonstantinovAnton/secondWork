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
    List<Person> personList;

    public Adapter(Context mContext, List<Person> filmsList) {
        this.mContext = mContext;
        this.personList = filmsList;
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
        TextView fname = v.findViewById(R.id.tvFname);
        TextView lname = v.findViewById(R.id.tvLname);
        TextView id = v.findViewById(R.id.tvID);
        ImageView Img = v.findViewById(R.id.imageView);

        Person p = personList.get(i);

        fname.setText((p.getFname()));

        lname.setText(p.getLname());
        id.setText(Integer.toString(p.getID()));
        DecodeImg m = new DecodeImg(mContext);
        Img.setImageBitmap(m.getImg(p.getImage()));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdatePerson.class);
                intent.putExtra(Person.class.getSimpleName(), p);
                mContext.startActivity(intent);
            }
        });
        return v;
    }
}






