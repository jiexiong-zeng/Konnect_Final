package com.disaintworking.konnect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

final class MyAdapter extends BaseAdapter {
    public static List<People> mItems ;
    private final LayoutInflater mInflater;



    public MyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        if (mItems == null){
            mItems = new ArrayList<People>();
        }

        mItems = AddPeople.db.getALlPeople();
    }



    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;


        if (v == null) {
            v = mInflater.inflate(R.layout.activity_image_adapter, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        People person = mItems.get(position);

        try {
            File Temp = new File(person.getURI(), person.name +".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(Temp));
            picture.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        name.setText(person.name);

        return v;
    }

}