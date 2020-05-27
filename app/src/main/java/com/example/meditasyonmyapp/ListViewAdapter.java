package com.example.meditasyonmyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private String[] adi;
    private String[] link;
    private LayoutInflater ınflater;

    public ListViewAdapter(Context m_context, String[] m_adi, String[] m_link)
    {
        this.context =m_context;
        this.adi=m_adi;
        this.link=m_link;

    }

    @Override
    public int getCount() {
        return adi.length;
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
    public View getView(int position, View view, ViewGroup parent) {
        //menü satırına ulaşacağız
        TextView adi_textview;
        ınflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=ınflater.inflate(R.layout.menusatir,parent,false);
        adi_textview=itemView.findViewById(R.id.idMenuAdiText);
        adi_textview.setText(adi[position]);
        return itemView;
    }
}
