package com.example.den.bigdig;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentListLinks extends Fragment {
    public static final String AUTHORITY = "com.example.den.bigdig.Links";
    public static final String PATH = "/links_data";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH);
    private List<LinkObject> links;
    public static AdapterForLinks adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_for_links, container, false);//создаем вид
        RecyclerView recyclerView = view.findViewById(R.id.idRecyclerViewLinks);
        Context context = getContext();

        Cursor mCursor = getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null, null);
        links = new ArrayList<>();
        if (mCursor.moveToFirst()) {
            do {
                String id = mCursor.getString(0);
                String link = mCursor.getString(1);
                int status = mCursor.getInt(2);
                String time = mCursor.getString(3);
                links.add(new LinkObject(id, link, time, status));
            } while (mCursor.moveToNext());
            adapter = new AdapterForLinks(context, links);//адаптер для ресайклера
            recyclerView.setAdapter(adapter);//подсоединяем адаптер к ресайклеру
        } else {
            Toast.makeText(getContext(), "Nothing is inside the cursor ", Toast.LENGTH_LONG).show();
        }
        mCursor.close();

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
                        intent.setClassName("com.example1.den.bigdig2", "com.example1.den.bigdig2.MainActivity");
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
//    private void editDataPerson(String editPersonId) {
//        Intent intent = new Intent(context, AddEditUser.class);
//        intent.putExtra("editPersonId", editPersonId);  // в активность передаем _id
//        startActivityForResult(intent, REQEST_EDIT);//запуск активности с ожидание результата
//    }//editDataPerson
//
//    //обработка КОНТЕКТСНОГО меню
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_CANCELED) return;
//        switch (requestCode) {
//            case REQEST_EDIT:
//                flagUpdateList = true;//разрешаем обновить список
//                Utils.setToast(context, "Данные изменены");
//        }//switch
//        super.onActivityResult(requestCode, resultCode, data);
//    }//onActivityResult

    //================================================================================================
    //сохраняем данные при повороте экрана
    public void onSaveInstanceState(Bundle outState) {
//        Log.d(LOG_FLAG, "onSaveInstanceState");
//        outState.putBoolean("flagUpdate", flagUpdate);//при пересоздании страници, для продолжения работы кружка-анимации при обновлении списка
//        outState.putBoolean("flagDel", flagDel);//при пересоздании страници, для продолжения работы кружка-анимации при удалении
//        outState.putInt("hashCodeViewPager", hashCodeViewPager);//при пересоздании страници, сохраняем хешкод viewPager
        super.onSaveInstanceState(outState);
    }//onSaveInstanceState

}//class FragmentListLinks