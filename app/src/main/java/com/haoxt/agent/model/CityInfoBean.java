package com.haoxt.agent.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * baowen
 */

public class CityInfoBean implements Parcelable {

    
    private String VALUE; /*110101*/
    
    private String LABEL; /*东城区*/
    
    private ArrayList<CityInfoBean> cityList;
    
    public ArrayList<CityInfoBean> getCityList() {
        return cityList;
    }
    
    public void setCityList(ArrayList<CityInfoBean> cityList) {
        this.cityList = cityList;
    }
    
    public CityInfoBean() {
    }
    
    public static CityInfoBean findCity(List<CityInfoBean> list, String cityName) {
        try {
            for (int i = 0; i < list.size(); i++) {
                CityInfoBean city = list.get(i);
                if (cityName.equals(city.getName()) /*|| cityName.contains(city.getName())
                        || city.getName().contains(cityName)*/) {
                    return city;
                }
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }


    public String getId() {
        return VALUE == null ? "" : VALUE;
    }

    public void setId(String id) {
        this.VALUE = id;
    }

    public String getName() {
        return LABEL == null ? "" : LABEL;
    }

    public void setName(String name) {
        this.LABEL = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.VALUE);
        dest.writeString(this.LABEL);
        dest.writeTypedList(this.cityList);
    }

    protected CityInfoBean(Parcel in) {
        this.VALUE = in.readString();
        this.LABEL = in.readString();
        this.cityList = in.createTypedArrayList(CityInfoBean.CREATOR);
    }

    public static final Creator<CityInfoBean> CREATOR = new Creator<CityInfoBean>() {
        @Override
        public CityInfoBean createFromParcel(Parcel source) {
            return new CityInfoBean(source);
        }

        @Override
        public CityInfoBean[] newArray(int size) {
            return new CityInfoBean[size];
        }
    };
}
