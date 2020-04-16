package com.example.covid_19tracker.ui.india;

import android.os.Parcel;
import android.os.Parcelable;

public class IndiaCountry implements Parcelable {
    String mState, mRecovered, mDeaths, mActive, mConfirmed;

    public IndiaCountry(String mState, String mRecovered, String mDeaths, String mActive, String mConfirmed) {
        this.mState = mState;
        this.mRecovered = mRecovered;
        this.mDeaths = mDeaths;
        this.mActive = mActive;
        this.mConfirmed = mConfirmed;
    }

    public String getmState() {
        return mState;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public String getmActive() {
        return mActive;
    }

    public String getmConfirmed() {
        return mConfirmed;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mState);
        dest.writeString(this.mRecovered);
        dest.writeString(this.mDeaths);
        dest.writeString(this.mActive);
        dest.writeString(this.mConfirmed);
    }

    protected IndiaCountry(Parcel in) {
        this.mState = in.readString();
        this.mRecovered = in.readString();
        this.mDeaths = in.readString();
        this.mActive = in.readString();
        this.mConfirmed = in.readString();
    }

    public static final Creator<IndiaCountry> CREATOR = new Creator<IndiaCountry>() {
        @Override
        public IndiaCountry createFromParcel(Parcel source) {
            return new IndiaCountry(source);
        }

        @Override
        public IndiaCountry[] newArray(int size) {
            return new IndiaCountry[size];
        }
    };
}
