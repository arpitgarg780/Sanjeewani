package com.cbitss.sanjeewani;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class serviceproviderlist extends BaseAdapter {
    Context c;
    String Title[] = {
            "Cleaning",
            "Printer Repair",
            "Automobile repair"
    };
    String Description[]={
            "I will clean Air Conditioners and cars",
            "I refill and epair aur printer at your home",
            "Get your automobiles repaired very quick and cheap"
    };
    serviceproviderlist(Context c)
    {
        this.c=c;
    }
    @Override
    public int getCount() {
        return Title.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.adapter_serviceprovider,null);

        ImageView im;
        TextView title,description;



        im = convertView.findViewById(R.id.image);
        title = convertView.findViewById(R.id.Name_service);
        description = convertView.findViewById(R.id.description);

        im.setImageResource(R.mipmap.ic_launcher);
        title.setText(Title[position]);
        description.setText(Description[position]);

        return convertView;
    }
}
