package com.example.den.bigdig;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by den on 23.02.2018.
 */

public class LinkObject implements Parcelable {
    private String id;
    private String name;
    private String time;
    private int status;

    public LinkObject(String id, String name, String time, int status) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.status = status;
    }//LinkObject

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeInt(status);
    }//writeToParcel

    protected LinkObject(Parcel in) {
        id = in.readString();
        name = in.readString();
        time = in.readString();
        status = in.readInt();
    }//LinkObject

    public static final Creator<LinkObject> CREATOR = new Creator<LinkObject>() {
        @Override
        public LinkObject createFromParcel(Parcel in) {
            return new LinkObject(in);
        }

        @Override
        public LinkObject[] newArray(int size) {
            return new LinkObject[size];
        }
    };

    public static final Comparator<LinkObject> COMPARE_BY_STATUS = new Comparator<LinkObject>() {
        @Override
        public int compare(LinkObject s1, LinkObject s2) {
            return s1.getStatus() - s2.getStatus();
        }
    };

    public static final Comparator<LinkObject> COMPARE_BY_DATE = new Comparator<LinkObject>() {
        @Override
        public int compare(LinkObject s1, LinkObject s2) {
            return (s1.getTime()).compareTo(s2.getTime());//реверс сортировки
        }//compare
    };
}//class LinkObject
