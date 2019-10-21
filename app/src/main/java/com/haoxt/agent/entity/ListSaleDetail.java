package com.haoxt.agent.entity;

import android.os.Parcel;
import android.os.Parcelable;


public class ListSaleDetail implements Parcelable {
    public Integer id;
    public String typename;
    public String saletime;
    public String salemoney;

    public ListSaleDetail() {
    }

    protected ListSaleDetail(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        typename = in.readString();
        saletime = in.readString();
        salemoney = in.readString();
    }

    public static final Creator<ListSaleDetail> CREATOR = new Creator<ListSaleDetail>() {
        @Override
        public ListSaleDetail createFromParcel(Parcel in) {
            return new ListSaleDetail(in);
        }

        @Override
        public ListSaleDetail[] newArray(int size) {
            return new ListSaleDetail[size];
        }
    };

    public String getSalemoney() {
        return salemoney;
    }

    public void setSalemoney(String salemoney) {
        this.salemoney = salemoney;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getSaletime() {
        return saletime;
    }

    public void setSaletime(String saletime) {
        this.saletime = saletime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(typename);
        parcel.writeString(saletime);
        parcel.writeString(salemoney);
    }
}
