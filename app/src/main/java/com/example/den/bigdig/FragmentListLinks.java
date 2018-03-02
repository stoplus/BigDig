package com.example.den.bigdig;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentListLinks extends Fragment {
    public static final String AUTHORITY = "com.example.den.bigdig.Links";
    public static final String PATH = "/links_data";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    private List<LinkObject> links;
    public static AdapterForLinks adapter;
    private Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_for_links, container, false);//создаем вид
        RecyclerView recyclerView = view.findViewById(R.id.idRecyclerViewLinks);
        Context context = getContext();

        links = createListLinks(context);

        adapter = new AdapterForLinks(context, links);//адаптер для ресайклера
        recyclerView.setAdapter(adapter);//подсоединяем адаптер к ресайклеру

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    // код для клика по элементу
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent();
                        intent.putExtra("processing", true);
                        intent.putExtra("status", links.get(position).getStatus());
                        intent.putExtra("id", links.get(position).getId());
                        intent.putExtra("linkText", links.get(position).getName());//передаем ссылку
                        intent.putExtra("AUTHORITY", ContractLinks.AUTHORITY);
                        intent.putExtra("PATH", ContractLinks.PATH_LINKS_DATA);
                        intent.setClassName("com.example1.den.bigdig2", "com.example1.den.bigdig2.App2");
                        startActivity(intent);
                        getActivity().finish();
                    }//onItemClick

                    //длинное нажатие по элементу
                    @Override
                    public void onLongItemClick(View view, final int position) {
                    }//onLongItemClick
                })//RecyclerItemClickListener
        );
        return view;
    }//onCreateView

    //===========================================================================================//
    public List<LinkObject> createListLinks(Context context) {
        List<LinkObject> links = new ArrayList<>();
        Cursor mCursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null, null);
        if (mCursor.moveToFirst()) {
            do {
                String id = mCursor.getString(0);
                String link = mCursor.getString(1);
                int status = mCursor.getInt(2);
                String time = mCursor.getString(3);
                links.add(new LinkObject(id, link, time, status));
            } while (mCursor.moveToNext());
        } else {
            res = context.getResources();//доступ к ресерсам
            Toast.makeText(context, res.getString(R.string.listLinksEmpty), Toast.LENGTH_LONG).show();
        }
        mCursor.close();
        return links;
    }//createListLinks
}//class FragmentListLinks