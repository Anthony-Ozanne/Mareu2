package com.openclassrooms.mareu.ui.mareu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.openclassrooms.mareu.model.Salle;

import java.util.List;

public class CustomAdapter extends BaseAdapter  {

    private LayoutInflater flater;
    private List<Salle> list;
    private int listItemLayoutResource;
    private int name;
    private int color;


    public CustomAdapter(Activity context, int listItemLayoutResource,
                         int name, int color,
                         List<Salle> list) {
        this.listItemLayoutResource = listItemLayoutResource;

        this.name = name;
        this.color = color;
        this.list = list;
        this.flater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        if(this.list == null)  {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        Salle salle = (Salle) this.getItem(position);
        return salle.getNumero();
        // return position; (Return position if you need).
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Salle salle = (Salle) getItem(position);

        // Example: @listItemLayoutResource: R.layout.spinner_item_layout_resource
        // (File: layout/spinner_item_layout_resource.xml)
        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        // Example: @textViewItemNameId: R.id.textView_item_name
        // (A TextView in file layout/spinner_item_layout_resource.xml)
        TextView name = (TextView) rowView.findViewById(this.name);
        name.setText(salle.getNom());

        TextView color = (TextView) rowView.findViewById(this.color);
        color.setText(String.valueOf(salle.getCouleur()));
        return rowView;
    }
}
