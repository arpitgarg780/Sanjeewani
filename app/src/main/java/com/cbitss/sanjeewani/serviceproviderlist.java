package com.cbitss.sanjeewani;

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
    serviceproviderlist(Context c)
    {
        this.c=c;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

        return convertView;
    }
}
