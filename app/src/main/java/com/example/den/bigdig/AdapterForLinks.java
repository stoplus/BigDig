package com.example.den.bigdig;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by den on 21.02.2018.
 */

public class AdapterForLinks extends RecyclerView.Adapter<AdapterForLinks.ViewHolder> {
    private LayoutInflater inflater;    // для загрузки разметки элемента
    private List<LinkObject> listLinks;    // коллекция выводимых данных
    private boolean flagSort = true;

    public AdapterForLinks(Context context, List<LinkObject> listLinks) {
        this.inflater = LayoutInflater.from(context);
        this.listLinks = listLinks;
    }//AdapterForLinks

    //внутрений класс ViewHolder для хранения элементов разметки
    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;

        // в конструкторе получаем ссылки на элементы по id
        private ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.idLink);
        }//ViewHolder
    }//class ViewHolder

    @Override
    public int getItemCount() {
        return listLinks.size();
    }//getItemCount

    @Override
    public long getItemId(int position) {
        return position;
    }//getItemId

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.link_adapter, parent, false);
        return new ViewHolder(view);
    } // onCreateViewHolder


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // связать отображаемые элементы и значения полей
        holder.name.setText(listLinks.get(position).getName());
        switch (listLinks.get(position).getStatus()) {
            case 1:
                holder.name.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                holder.name.setBackgroundColor(Color.RED);
                break;
            case 3:
                holder.name.setBackgroundColor(Color.GRAY);
                break;
        }
    }//onBindViewHolder

    //--------------------------------------------------------------------------------------------------
    public void sortByDate() {
        Collections.sort(listLinks, LinkObject.COMPARE_BY_DATE);
        if (flagSort) {
            Collections.reverse(listLinks);
        }
        flagSort = !flagSort;//реверс
        notifyDataSetChanged();
    }//sortByDate

    public void sortByStatus() {
        Collections.sort(listLinks, LinkObject.COMPARE_BY_STATUS);
        if (flagSort) {
            Collections.reverse(listLinks);
        }
        flagSort = !flagSort;//реверс
        notifyDataSetChanged();
    }//sortByStatus

    public void notifyList(List<LinkObject> newListLinks) {
        listLinks = newListLinks;
        notifyDataSetChanged();
    }//notifyList
}//class AdapterForLinks
