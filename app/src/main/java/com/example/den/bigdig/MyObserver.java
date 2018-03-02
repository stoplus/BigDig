package com.example.den.bigdig;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import java.util.List;

/**
 * Created by den on 01.03.2018.
 */

class MyObserver extends ContentObserver {
    private Context context;

    public MyObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
    }//MyObserver

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        FragmentListLinks fragmentListLinks = new FragmentListLinks();
        List<LinkObject> links = fragmentListLinks.createListLinks(context);//создаем новый список
        FragmentListLinks.adapter.notifyList(links);//обновляем адаптер с новым списком
    }//onChange
}//MyObserver
